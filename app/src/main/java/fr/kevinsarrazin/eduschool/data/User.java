package fr.kevinsarrazin.eduschool.data;

/**
 * Created by Administrateur on 10/03/2015.
 */
public class User {
    private long id;
    private String login;
    private String password;
    private String pathImage;

    public User() {
        super();
    }

    public User(String login, String password, String pathImage) {
        this.login = login;
        this.password = password;
        this.pathImage = pathImage;
    }

    public User(long id, String login, String password, String pathImage) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.pathImage = pathImage;
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

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
