package trackensure.service.impl;

import java.util.List;
import trackensure.dao.MessageDao;
import trackensure.lib.Inject;
import trackensure.lib.Service;
import trackensure.model.Message;
import trackensure.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    @Inject
    private MessageDao messageDao;

    @Override
    public List<Message> getFiftyMessages() {
        return messageDao.getFiftyMessages();
    }

    @Override
    public Message create(Message message) {
        return messageDao.create(message);
    }
}
