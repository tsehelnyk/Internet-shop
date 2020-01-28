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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {

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

        Role.RoleName roleName = protectedUrls.get(req.getServletPath());
        if (roleName == null) {
            authorizatedAccess(req, resp, filterChain);
            return;
        }

        try {
            Long userId = (Long) req.getSession(true).getAttribute("user_id");
            Optional<User> user = userService.get(userId);
            if (verifyRole(user.get(), roleName)) {
                authorizatedAccess(req, resp, filterChain);
                return;
            } else {
                deniedAccess(req, resp, filterChain);
                return;
            }
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("dpe_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }

    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
    }

    private void deniedAccess(HttpServletRequest req, HttpServletResponse resp,
                              FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("User wasn't authorized.");
        req.getRequestDispatcher("/WEB-INF/views/denied.jsp").forward(req, resp);
    }

    private void authorizatedAccess(HttpServletRequest req, HttpServletResponse resp,
                                    FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("Authorization successful.");
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
