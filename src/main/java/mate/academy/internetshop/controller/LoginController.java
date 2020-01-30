package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.exception.HashGeneratingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("psw");
        try {
            User user = userService.login(login, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("user_id", user.getId());
            resp.sendRedirect(req.getContextPath() + "/servlet/index");
        } catch (AuthenticationException e) {
            LOGGER.info("Wrong login or password");
            req.setAttribute("errorMessage", "Incorrect login or password.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } catch (DataProcessingException | HashGeneratingException e) {
            LOGGER.error(e);
            req.setAttribute("dpe_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/Error.jsp").forward(req, resp);
        }
    }
}
