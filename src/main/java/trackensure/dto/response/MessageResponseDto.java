package trackensure.dto.response;

import lombok.Data;

@Data
public class MessageResponseDto {
    private Long id;
    private String login;
    private String message;
    private String timeStamp;
}
