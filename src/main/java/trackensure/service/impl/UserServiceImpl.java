package trackensure.service.impl;

import java.util.Optional;
import trackensure.dao.UserDao;
import trackensure.lib.Inject;
import trackensure.lib.Service;
import trackensure.model.User;
import trackensure.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;


    @Override
    public User logging(String login) {
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
        return userDao.getByLogin(login).orElseThrow(() -> {
            throw new RuntimeException("No User by login" + login + "in DB!");
        });
    }

    @Override
    public User get(Long userId) {
        return userDao.get(userId).orElseThrow(() -> {
            throw new RuntimeException("No User by id" + userId + "in DB!");
        });
    }
}
