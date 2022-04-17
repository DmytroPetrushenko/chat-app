package trackensure.model;

public class Message {
    private Long id;
    private User user;
    private String message;
    private String timeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Message{"
                + "id = " + id
                + ", user = " + user
                + ", message = " + message
                + ", timeStamp = " + timeStamp
                + "}";
    }
}
