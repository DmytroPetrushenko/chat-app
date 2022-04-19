package trackensure.service;

import java.util.List;
import trackensure.model.Message;

public interface MessageService {
    List<Message> getNMessages(Long quantity);

    Message create(Message message);
}
