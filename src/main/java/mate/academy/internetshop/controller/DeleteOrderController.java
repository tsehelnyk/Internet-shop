package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.OrderService;
import org.apache.log4j.Logger;

public class DeleteOrderController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteOrderController.class);

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("order_id");
        try {
            orderService.delete(Long.valueOf(orderId));
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("dpe_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/Error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/orders");
    }

}
