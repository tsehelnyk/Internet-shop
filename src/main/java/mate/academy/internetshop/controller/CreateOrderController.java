package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class CreateOrderController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateOrderController.class);

    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;
    @Inject
    private static UserService userService;
    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("user_id");
        try {
            Bucket bucket = bucketService.get(userId);
            User user = userService.get(userId).orElseThrow(NoSuchElementException::new);
            if (bucket.getItems().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/servlet/items");
                return;
            }
            orderService.completeOrder(bucket.getItems(), user);
            bucketService.clear(bucket);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("dpe_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/Error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/orders");
    }
}
