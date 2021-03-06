package trackensure.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trackensure.lib.Injector;
import trackensure.service.UserService;

public class LoginController extends HttpServlet {
    private static final String USER_IDENTIFIER = "login";
    private static final String REDIRECT_URL = "/chat";
    private static final String JSP_ADDRESS = "WEB-INF/views/login.jsp";
    private final Injector injector = Injector.getInstance("trackensure");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(JSP_ADDRESS).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String login = req.getParameter(USER_IDENTIFIER);
        userService.logging(login);
        HttpSession session = req.getSession();
        session.setAttribute(USER_IDENTIFIER, login);
        resp.sendRedirect(REDIRECT_URL);
    }
}
