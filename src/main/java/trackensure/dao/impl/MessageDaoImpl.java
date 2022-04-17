package trackensure.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import trackensure.dao.MessageDao;
import trackensure.lib.Dao;
import trackensure.model.Message;
import trackensure.model.User;
import trackensure.util.ConnectionUtil;

@Dao
public class MessageDaoImpl implements MessageDao {

    @Override
    public List<Message> getFiftyMessages() {
        String query = "SELECT m.id, u.id, u.login, m.message, m.time_stamp FROM messages m "
                + "LEFT JOIN users u ON m.user_id = u.id "
                + "WHERE m.is_deleted = false ORDER BY (m.id) DESC LIMIT 50";
        List<Message> messageList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement getFiftyMessagesStatement = connection.createStatement()) {
            ResultSet resultSet = getFiftyMessagesStatement.executeQuery(query);
            while (resultSet.next()) {
                messageList.add(getMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get 50 messages from DB", e);
        }
        return messageList;
    }

    @Override
    public Message create(Message message) {
        String query = "INSERT INTO messages (user_id, message, time_stamp) "
                + "VALUES(?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createMessageStatement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createMessageStatement.setObject(1, message.getUser().getId());
            createMessageStatement.setString(2, message.getMessage());
            createMessageStatement.setString(3, message.getTimeStamp());
            createMessageStatement.executeUpdate();
            ResultSet resultSet = createMessageStatement.getGeneratedKeys();
            if (resultSet.next()) {
                message.setId(resultSet.getObject(1, Long.class));
            }
            return message;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a message "
                    + message + " in DB", e);
        }
    }

    @Override
    public Optional<Message> get(Long id) {
        String query = "SELECT m.id, u.id, u.login, m.message, m.time_stamp FROM messages m "
                + "JOIN users u ON m.user_id = u.id WHERE m.id = ? AND m.is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement getMessageStatement = connection.prepareStatement(query)) {
            getMessageStatement.setObject(1, id);
            ResultSet resultSet = getMessageStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get a message by message's id "
                    + id + " from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public Boolean update(Message message) {
        String query = "UPDATE messages SET user_id = ?, message = ?, time_stamp = ? "
                + "WHERE id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement updateMessageStatement = connection.prepareStatement(query)) {
            updateMessageStatement.setObject(1, message.getUser().getId());
            updateMessageStatement.setString(2, message.getMessage());
            updateMessageStatement.setString(3, message.getTimeStamp());
            updateMessageStatement.setObject(4, message.getId());
            return updateMessageStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update a message "
                    + message + " in DB", e);
        }
    }

    @Override
    public boolean delete(Message message) {
        String query = "UPDATE messages SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteMessageStatement = connection.prepareStatement(query)) {
            deleteMessageStatement.setObject(1, message.getId());
            return deleteMessageStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete a message "
                    + message + " in DB", e);
        }
    }

    @Override
    public List<Message> getAll() {
        List<Message> messageList = new ArrayList<>();
        String query = "SELECT m.id, u.id, u.login, m.message, m.time_stamp FROM messages m "
                + "LEFT JOIN users u ON m.user_id = u.id WHERE m.is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 Statement gatAllStatement = connection.createStatement()) {
            ResultSet resultSet = gatAllStatement.executeQuery(query);
            while (resultSet.next()) {
                messageList.add(getMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all message from DB", e);
        }
        return messageList;
    }

    private Message getMessageFromResultSet(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getObject("m.id", Long.class));
        User user = new User();
        user.setId(resultSet.getObject("u.id", Long.class));
        user.setLogin(resultSet.getString("u.login"));
        message.setUser(user);
        message.setMessage(resultSet.getString("m.message"));
        message.setTimeStamp(resultSet.getString("m.time_stamp"));
        return message;
    }
}
