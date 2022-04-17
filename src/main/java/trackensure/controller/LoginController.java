package trackensure.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trackensure.lib.Injector;
import trackensure.model.User;
import trackensure.service.UserService;

public class LoginController extends HttpServlet {
    private final Injector injector = Injector.getInstance("trackensure");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        User user = userService.logging(login);
        HttpSession session = req.getSession();
        session.setAttribute("login", login);
        resp.sendRedirect("/chat");
    }
}
