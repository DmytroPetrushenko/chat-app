package trackensure.service.impl;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trackensure.dao.UserDao;
import trackensure.lib.Inject;
import trackensure.lib.Service;
import trackensure.model.User;
import trackensure.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Inject
    private UserDao userDao;

    @Override
    public User logging(String login) {
        logger.info("logging method was called. login: {}", login);

        Optional<User> optionalUser = userDao.getByLogin(login);
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setLogin(login);
            return userDao.create(user);
        }
        return optionalUser.get();
    }

    @Override
    public User getByLogin(String login) {
        logger.info("getByLogin method was called. login: {}", login);

        Optional<User> optionalUser = userDao.getByLogin(login);
        if (optionalUser.isEmpty()) {
            logger.warn("No User by login" + login + "in DB!");
        }
        return optionalUser.get();
    }

    @Override
    public User get(Long userId) {
        logger.info("get method was called. userId: {}", userId);

        Optional<User> optionalUser = userDao.get(userId);
        if (optionalUser.isEmpty()) {
            logger.warn("No User by id" + userId + "in DB!");
        }
        return optionalUser.get();
    }
}
