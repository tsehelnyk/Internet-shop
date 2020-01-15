package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {

    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("psw").equals(req.getParameter("psw-repeat"))) {
            User newUser = new User();
            newUser.setName(req.getParameter("name"));
            newUser.setLogin(req.getParameter("login"));
            newUser.setPassword(req.getParameter("psw"));
            userService.create(newUser);
            Bucket newBucket = new Bucket();
            newBucket.setUser(newUser.getId());
            bucketService.create(newBucket);
            Cookie cookie = new Cookie("MATE", newUser.getToken());
            resp.addCookie(cookie);
            HttpSession session = req.getSession(true);
            session.setAttribute("user_id", newUser.getId());
            resp.sendRedirect(req.getContextPath() + "/servlet/index");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }
}
