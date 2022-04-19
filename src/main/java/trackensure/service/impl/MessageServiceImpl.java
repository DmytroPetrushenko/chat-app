package trackensure.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trackensure.dao.MessageDao;
import trackensure.lib.Inject;
import trackensure.lib.Service;
import trackensure.model.Message;
import trackensure.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);
    @Inject
    private MessageDao messageDao;

    @Override
    public List<Message> getNMessages(Long quantity) {
        logger.info("getNMessages method was called. quantity: {}", quantity);

        return messageDao.getNMessages(quantity);
    }

    @Override
    public Message create(Message message) {
        logger.info("create method was called. quantity: {}", message);

        return messageDao.create(message);
    }
}
