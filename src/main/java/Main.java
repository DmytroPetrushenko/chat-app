import trackensure.dao.MessageDao;
import trackensure.dao.UserDao;
import trackensure.dao.impl.MessageDaoImpl;
import trackensure.dao.impl.UserDaoImpl;
import trackensure.model.Message;
import trackensure.model.User;

import java.time.LocalDateTime;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        MessageDao messageDao = new MessageDaoImpl();
        UserDao userDao = new UserDaoImpl();
        /*
        for (int i = 0; i < 100; i++) {
            Message message = new Message();
            message.setUserId(1L);
            message.setMessage(String.valueOf(random.nextInt()));
            message.setTimeStamp(LocalDateTime.now().toString());
            messageDao.create(message);
        }
         */
        //messageDao.getFiftyMessages().forEach(System.out::println);
        /*
        User bob = new User();
        bob.setName("Bob");
        bob = userDao.create(bob);


        User alice = new User();
        alice.setId(7L);
        alice.setName("ALICE!");

         */
        userDao.getAll().forEach(System.out::println);
    }
}
