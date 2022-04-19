package trackensure.dao;

import java.util.List;
import trackensure.model.Message;

public interface MessageDao extends GenericDao<Message> {
    List<Message> getNMessages(Long quantity);
}
