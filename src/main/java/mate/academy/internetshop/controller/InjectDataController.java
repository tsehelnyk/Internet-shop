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

public class InjectDataController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(InjectDataController.class);

    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            User user = new User();
            user.setName("John");
            user.addRole(new Role(Role.RoleName.USER));
            user.setLogin("john");
            user.setPassword("1");
            user = userService.create(user);
            Bucket newBucket = new Bucket();
            newBucket.setUser(user.getId());
            bucketService.create(newBucket);

            User admin = new User();
            admin.setName("Joey");
            admin.addRole(new Role(Role.RoleName.ADMIN));
            admin.setLogin("admin");
            admin.setPassword("1");
            userService.create(admin);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("dpe_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/Error.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
