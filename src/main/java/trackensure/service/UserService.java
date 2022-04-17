package trackensure.service;

import trackensure.model.User;

public interface UserService {
    User login(String login);

    User get(Long userId);
}
