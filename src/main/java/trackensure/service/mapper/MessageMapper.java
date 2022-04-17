package trackensure.service.mapper;

import trackensure.dto.request.MessageRequestDto;
import trackensure.dto.response.MessageResponseDto;
import trackensure.model.Message;

public interface MessageMapper {
    Message fromDto(MessageRequestDto requestDto);

    MessageResponseDto toDto(Message message);
}
