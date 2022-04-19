package trackensure.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trackensure.dao.UserDao;
import trackensure.lib.Dao;
import trackensure.model.User;
import trackensure.util.ConnectionUtil;

@Dao
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public User create(User user) {
        logger.info("create method was called. User: {}", user);

        String query = "INSERT INTO users (login) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement createUserStatement = connection
                         .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createUserStatement.setString(1, user.getLogin());
            createUserStatement.executeUpdate();
            ResultSet resultSet = createUserStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            logger.error("Can't create a user " + user + " in DB", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        logger.info("get method was called. id: {}", id);

        String query = "SELECT * FROM users WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getUserStatement = connection.prepareStatement(query)) {
            getUserStatement.setObject(1, id);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't get a user by id" + id + " from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public Boolean update(User user) {
        logger.info("update method was called. User: {}", user);
        String query = "UPDATE users SET login = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateUserStatement = connection.prepareStatement(query)) {
            updateUserStatement.setString(1, user.getLogin());
            updateUserStatement.setObject(2, user.getId());
            return updateUserStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Can't update the user " + user + " in DB", e);
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        logger.info("delete method was called. User: {}", user);

        String query = "UPDATE users SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteUserStatement = connection.prepareStatement(query)) {
            deleteUserStatement.setObject(1, user.getId());
            return deleteUserStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Can't delete the user " + user + " in DB", e);
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        logger.info("getAll method was called.");

        String query = "SELECT * FROM users WHERE is_deleted = false";
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(query);
            while (resultSet.next()) {
                userList.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't get all users " + " from DB", e);
        }
        return userList;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getObject("id", Long.class));
        user.setLogin(resultSet.getString("login"));
        return user;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        logger.info("getByLogin method was called. login: {}", login);

        String query = "SELECT * FROM users WHERE login = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getByLoginStatement = connection.prepareStatement(query)) {
            getByLoginStatement.setString(1, login);
            ResultSet resultSet = getByLoginStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't get a user by login " + login + " in DB", e);
        }
        return Optional.empty();
    }
}
