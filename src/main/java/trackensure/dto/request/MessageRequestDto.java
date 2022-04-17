package trackensure.dto.request;

import lombok.Data;

@Data
public class MessageRequestDto {
    private String login;
    private String message;
    private String timeStamp;
}
