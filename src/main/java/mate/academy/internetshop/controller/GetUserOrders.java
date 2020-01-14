package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class GetUserOrders extends HttpServlet {

    private static final Long USER_ID = 0L;

    @Inject
    private static UserService userService;
    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = userService.get(USER_ID);
        List<Order> orders = orderService.getUserOrders(user)
                .orElseThrow(NoSuchElementException::new);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
    }
}
