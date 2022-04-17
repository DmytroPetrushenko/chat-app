package trackensure.dao;

import java.util.Optional;
import trackensure.model.User;

public interface UserDao extends GenericDao<User> {
    Optional<User> getByLogin(String login);
}
