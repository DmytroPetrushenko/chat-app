package trackensure.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import trackensure.dto.response.MessageResponseDto;
import trackensure.lib.Injector;
import trackensure.model.Message;
import trackensure.model.User;
import trackensure.service.MessageService;
import trackensure.service.UserService;
import trackensure.service.mapper.MessageMapper;

public class ChatController extends HttpServlet {
    private final Injector injector = Injector.getInstance("trackensure");
    private final MessageService messageService = (MessageService) injector
            .getInstance(MessageService.class);
    private final UserService userService = (UserService) injector
            .getInstance(UserService.class);
    private final MessageMapper messageMapper = (MessageMapper) injector
            .getInstance(MessageMapper.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login");
        List<MessageResponseDto> dtoList = messageService.getFiftyMessages().stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
        req.setAttribute("fiftyMessages", objectMapper.writeValueAsString(dtoList));
        req.setAttribute("login", login);
        req.getRequestDispatcher("WEB-INF/views/chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String input = req.getParameter("input");
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.get(userId);
        Message message = new Message();
        message.setMessage(input);
        message.setUser(user);
        message.setTimeStamp(LocalDateTime.now().toString());
        messageService.create(message);
        List<Message> fiftyMessages = messageService.getFiftyMessages();
        req.setAttribute("fiftyMessages", fiftyMessages);
        req.setAttribute("user", user);
        req.getRequestDispatcher("WEB-INF/views/chat.jsp").forward(req, resp);
    }
}
