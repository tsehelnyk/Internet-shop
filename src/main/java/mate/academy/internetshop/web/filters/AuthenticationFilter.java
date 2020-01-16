package mate.academy.internetshop.web.filters;

import java.io.IOException;
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
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter {

    private static final String COOKIE_NAME = "MATE";
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (req.getCookies() == null) {
            unAuthenticatedAccess(req, resp);
            return;
        }
        for (Cookie cookie: req.getCookies()) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                Optional<User> user = userService.getByToken(cookie.getValue());
                if (user.isPresent()) {
                    LOGGER.info("User " + user.get().getId() + " was authenticated.");
                    filterChain.doFilter(req, resp);
                    return;
                }
            }
        }
        unAuthenticatedAccess(req, resp);
    }

    private void unAuthenticatedAccess(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        LOGGER.info("User wasn't authenticated.");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    public void destroy() { }
}
