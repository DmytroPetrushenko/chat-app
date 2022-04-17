package trackensure.service;

import trackensure.model.User;

public interface UserService {
    User logging(String login);

    User getByLogin(String login);

    User get(Long userId);
}
