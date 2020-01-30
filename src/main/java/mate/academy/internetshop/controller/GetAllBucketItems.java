package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class GetAllBucketItems extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetAllBucketItems.class);

    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("user_id");
        try {
            List<Item> items = bucketService.getAllItems(bucketService.get(userId))
                    .orElseThrow(NoSuchElementException::new);
            req.setAttribute("items", items);
        } catch (DataProcessingException | NoSuchElementException e) {
            LOGGER.error(e);
            req.setAttribute("dpe_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/Error.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("/WEB-INF/views/bucket.jsp").forward(req, resp);
    }

}
