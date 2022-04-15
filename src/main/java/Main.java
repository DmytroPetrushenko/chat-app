import trackensure.dao.MessageDao;
import trackensure.dao.impl.MessageDaoImpl;
import trackensure.model.Message;

import java.time.LocalDateTime;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        MessageDao messageDao = new MessageDaoImpl();
        /*
        for (int i = 0; i < 100; i++) {
            Message message = new Message();
            message.setUserId(1L);
            message.setMessage(String.valueOf(random.nextInt()));
            message.setTimeStamp(LocalDateTime.now().toString());
            messageDao.create(message);
        }
         */
        messageDao.getFiftyMessages().forEach(System.out::println);


    }
}
