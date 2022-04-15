package trackensure.model;

public class Message {
    private Long id;
    private Long userId;
    private String message;
    private String timeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                + ", userId = " + userId
                + ", message = " + message
                + ", timeStamp = " + timeStamp
                + "}";
    }
}
