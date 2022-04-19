package trackensure.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trackensure.dto.request.MessageRequestDto;
import trackensure.dto.response.MessageResponseDto;
import trackensure.lib.Injector;
import trackensure.service.MessageService;
import trackensure.service.UserService;
import trackensure.service.mapper.MessageMapper;

public class ChatController extends HttpServlet {
    private static final String USER_IDENTIFIER = "login";
    private static final String LIST_N_MESSAGES = "fiftyMessages";
    private static final String USER_MESSAGE = "input";
    private static final String JSP_ADDRESS = "WEB-INF/views/chat.jsp";
    private static final Long N_MESSAGES = 50L;
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
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute(USER_IDENTIFIER);
        List<MessageResponseDto> dtoList = messageService.getNMessages(N_MESSAGES).stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
        req.setAttribute(LIST_N_MESSAGES, dtoList);
        req.setAttribute(USER_IDENTIFIER, login);
        req.getRequestDispatcher(JSP_ADDRESS).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        MessageRequestDto requestDto = new MessageRequestDto();
        String login = (String) session.getAttribute(USER_IDENTIFIER);
        requestDto.setLogin(login);
        requestDto.setMessage(req.getParameter(USER_MESSAGE));
        messageService.create(messageMapper.fromDto(requestDto));

        List<MessageResponseDto> fiftyMessages = messageService.getNMessages(N_MESSAGES).stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
        req.setAttribute(LIST_N_MESSAGES, fiftyMessages);
        req.setAttribute(USER_IDENTIFIER, login);
        req.getRequestDispatcher(JSP_ADDRESS).forward(req, resp);
    }
}
