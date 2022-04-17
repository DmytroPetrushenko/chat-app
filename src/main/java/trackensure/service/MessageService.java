package trackensure.service;

import java.util.List;
import trackensure.model.Message;

public interface MessageService {
    List<Message> getFiftyMessages();

    Message create(Message message);
}
