package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {

    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
