package mate.academy.internetshop.web.filters;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {

    private static final String COOKIE_NAME = "MATE";
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/users", ADMIN);
        protectedUrls.put("/servlet/addItem", ADMIN);
        protectedUrls.put("/servlet/deleteItem", ADMIN);
        protectedUrls.put("/servlet/bucket", USER);
        protectedUrls.put("/servlet/add2bucket", USER);
        protectedUrls.put("/servlet/delFromBucket", USER);
        protectedUrls.put("/servlet/newOrder", USER);
        protectedUrls.put("/servlet/orders", USER);
        protectedUrls.put("/servlet/delOrder", USER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            unAuthenticatedAccess(req, resp);
            return;
        }

        Role.RoleName roleName = protectedUrls.get(req.getServletPath());
        if (roleName == null) {
            authorizatedAccess(req, resp, filterChain);
            return;
        }

        String token = null;
        for (Cookie cookie: req.getCookies()) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            unAuthenticatedAccess(req, resp);
            return;
        } else {
            Optional<User> user = userService.getByToken(token);
            if (user.isPresent()) {
                if (verifyRole(user.get(), roleName)) {
                    authorizatedAccess(req, resp, filterChain);
                    return;
                } else {
                    deniedAccess(req, resp, filterChain);
                    return;
                }
            } else {
                unAuthenticatedAccess(req, resp);
                return;
            }
        }
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
    }

    private void deniedAccess(HttpServletRequest req, HttpServletResponse resp,
                              FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("User wasn't authenticated.");
        req.getRequestDispatcher("/WEB-INF/views/denied.jsp").forward(req, resp);
    }

    private void authorizatedAccess(HttpServletRequest req, HttpServletResponse resp,
                                    FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(req, resp);
    }

    private void unAuthenticatedAccess(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        LOGGER.info("User wasn't authenticated.");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {

    }
}
