package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class RegistrationController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);

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
            Role role = new Role(2L);
            role.setRoleName(Role.RoleName.USER);
            newUser.addRole(role);
            try {
                userService.create(newUser);
                Bucket newBucket = new Bucket();
                newBucket.setUser(newUser.getId());
                bucketService.create(newBucket);
            } catch (DataProcessingException e) {
                LOGGER.error(e);
                req.setAttribute("dpe_msg", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
            }

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }
}
