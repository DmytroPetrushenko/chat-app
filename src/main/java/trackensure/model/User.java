package trackensure.model;

public class User {
    private Long id;
    private String login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + login + '\''
                + '}';
    }
}
