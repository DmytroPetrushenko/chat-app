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
import trackensure.dao.MessageDao;
import trackensure.lib.Dao;
import trackensure.model.Message;
import trackensure.model.User;
import trackensure.util.ConnectionUtil;

@Dao
public class MessageDaoImpl implements MessageDao {
    private static final Logger logger = LogManager.getLogger(MessageDaoImpl.class);

    @Override
    public List<Message> getNMessages(Long quantity) {
        logger.info("getNMessages method was called. quantity: {}", quantity);

        String query = "SELECT m.id, u.id, u.login, m.message, m.time_stamp FROM messages m "
                + "LEFT JOIN users u ON m.user_id = u.id "
                + "WHERE m.is_deleted = false ORDER BY (m.id) DESC LIMIT ?";
        List<Message> messageList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getFiftyMessagesStatement = connection.prepareStatement(query)) {
            getFiftyMessagesStatement.setObject(1, quantity);
            ResultSet resultSet = getFiftyMessagesStatement.executeQuery();
            while (resultSet.next()) {
                messageList.add(getMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't get N messages from DB", e);
        }
        return messageList;
    }

    @Override
    public Message create(Message message) {
        logger.info("create method was called. message: {}", message);

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
        } catch (SQLException e) {
            logger.error("Can't create a message " + message + " in DB", e);
        }
        return message;
    }

    @Override
    public Optional<Message> get(Long id) {
        logger.info("get method was called. id: {}", id);

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
            logger.error("Can't get a message by message's id " + id + " from DB", e);
        }
        return Optional.empty();
    }

    @Override
    public Boolean update(Message message) {
        logger.info("update method was called. message: {}", message);

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
            logger.error("Can't update a message " + message + " in DB", e);
        }
        return false;
    }

    @Override
    public boolean delete(Message message) {
        logger.info("delete method was called. message: {}", message);

        String query = "UPDATE messages SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteMessageStatement = connection.prepareStatement(query)) {
            deleteMessageStatement.setObject(1, message.getId());
            return deleteMessageStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Can't delete a message " + message + " in DB", e);
        }
        return false;
    }

    @Override
    public List<Message> getAll() {
        logger.info("getAll method was called.");

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
            logger.error("Can't get all message from DB", e);
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
