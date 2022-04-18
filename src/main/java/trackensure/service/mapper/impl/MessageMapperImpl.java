package trackensure.service.mapper.impl;

import java.time.LocalDateTime;
import trackensure.dto.request.MessageRequestDto;
import trackensure.dto.response.MessageResponseDto;
import trackensure.lib.Inject;
import trackensure.lib.Service;
import trackensure.model.Message;
import trackensure.service.UserService;
import trackensure.service.mapper.MessageMapper;

@Service
public class MessageMapperImpl implements MessageMapper {
    @Inject
    private UserService userService;

    @Override
    public Message fromDto(MessageRequestDto requestDto) {
        Message message = new Message();
        message.setUser(userService.getByLogin(requestDto.getLogin()));
        message.setMessage(requestDto.getMessage());
        message.setTimeStamp(LocalDateTime.now().toString());
        return message;
    }

    @Override
    public MessageResponseDto toDto(Message message) {
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setId(message.getId());
        responseDto.setLogin(message.getUser().getLogin());
        responseDto.setMessage(message.getMessage());
        responseDto.setTimeStamp(message.getTimeStamp());
        return responseDto;
    }
}
