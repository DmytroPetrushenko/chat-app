package trackensure.service.mapper.impl;

import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trackensure.dto.request.MessageRequestDto;
import trackensure.dto.response.MessageResponseDto;
import trackensure.lib.Inject;
import trackensure.lib.Service;
import trackensure.model.Message;
import trackensure.model.User;
import trackensure.service.UserService;
import trackensure.service.mapper.MessageMapper;

@Service
public class MessageMapperImpl implements MessageMapper {
    private static final Logger logger = LogManager.getLogger(MessageMapperImpl.class);

    @Inject
    private UserService userService;

    @Override
    public Message fromDto(MessageRequestDto requestDto) {
        logger.info("fromDto method was called. requestDto: {}", requestDto);

        Message message = new Message();
        User byLogin = userService.getByLogin(requestDto.getLogin());
        message.setUser(byLogin);
        message.setMessage(requestDto.getMessage());
        message.setTimeStamp(LocalDateTime.now().toString());
        return message;
    }

    @Override
    public MessageResponseDto toDto(Message message) {
        logger.info("toDto method was called. message: {}", message);

        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setId(message.getId());
        responseDto.setLogin(message.getUser().getLogin());
        responseDto.setMessage(message.getMessage());
        responseDto.setTimeStamp(message.getTimeStamp());
        return responseDto;
    }
}
