package fr.kevinsarrazin.eduschool.data;

/**
 * Created by Administrateur on 10/03/2015.
 */
public class User {
    private long id;
    private String login;
    private String password;

    public User() {
        super();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
