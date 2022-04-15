package trackensure.dao.impl;

import trackensure.dao.UserDao;
import trackensure.model.User;
import trackensure.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createUserStatement = connection
                     .prepareStatement(query, Statement.RETURN_GENERATED_KEYS) ) {
            createUserStatement.setString(1, user.getName());
            createUserStatement.executeUpdate();
            ResultSet resultSet = createUserStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a user "
                    + user + " in DB", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(query)) {
            getUserStatement.setObject(1, id);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get a user by id"
                    + id + " from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public Boolean update(User user) {
        String query = "UPDATE users SET name = ? WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateUserStatement = connection.prepareStatement(query)) {
            updateUserStatement.setString(1, user.getName());
            updateUserStatement.setObject(2, user.getId());
            return updateUserStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update the user "
                    + user + " in DB", e);
        }
    }

    @Override
    public boolean delete(User user) {
        String query = "UPDATE users SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteUserStatement = connection.prepareStatement(query)) {
            deleteUserStatement.setObject(1, user.getId());
            return deleteUserStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete the user "
                    + user + " in DB", e);
        }
    }

    @Override
    public List<User> getAll() {
        String query ="SELECT * FROM users WHERE is_deleted = false";
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllStatement = connection.createStatement()) {
            ResultSet resultSet = getAllStatement.executeQuery(query);
            while (resultSet.next()) {
                userList.add(getUserFromResultSet(resultSet));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all users "
                    + " from DB", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getObject("id", Long.class));
        user.setName(resultSet.getString("name"));
        return user;
    }
}
