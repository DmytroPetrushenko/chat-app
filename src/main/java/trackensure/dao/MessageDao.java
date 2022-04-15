package trackensure.dao;

import trackensure.model.Message;

import java.util.List;

public interface MessageDao extends GenericDao<Message> {
    List<Message> getFiftyMessages();
}
