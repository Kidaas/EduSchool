package fr.kevinsarrazin.eduschool.data;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file User.java
 *
 * Class User
 *
 */
public class User {
    private long id;
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String pathImage;

    public User() {
        super();
    }

    public User(String login, String password, String nom, String prenom,  String pathImage) {
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.pathImage = pathImage;
    }

    public User(long id, String login, String password, String nom, String prenom, String pathImage) {
        this.id = id;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
