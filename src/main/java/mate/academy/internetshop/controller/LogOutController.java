package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutController extends HttpServlet {

    private static final String COOKIE_NAME = "MATE";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        session.setAttribute("user_id", null);

//        resp.addCookie(new Cookie(COOKIE_NAME, ""));
//        for (Cookie cookie : req.getCookies()) {
//            if (cookie.getName().equals(COOKIE_NAME)) {
//                cookie.setValue("");
//                cookie.setValue("");
//                cookie.setPath("/");
//                cookie.setMaxAge(12);
//                resp.addCookie(cookie);
//                break;
//            }
//        }
//        resp.addCookie(new Cookie(COOKIE_NAME, ""));
            resp.sendRedirect(req.getContextPath() + "/login");
    }
}
