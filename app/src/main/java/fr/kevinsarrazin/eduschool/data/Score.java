package fr.kevinsarrazin.eduschool.data;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file Score.java
 *
 * Class Score
 *
 */
public class Score {

    private long id;
    private long idUser;
    private long idMatiere;
    private int score;

    public Score() {
        super();
    }

    public Score(long idUser, long idMatiere, int score) {
        this.idUser = idUser;
        this.idMatiere = idMatiere;
        this.score = score;
    }

    public Score(long id, long idUser, long idMatiere, int score) {
        this.id = id;
        this.idUser = idUser;
        this.idMatiere = idMatiere;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getidUser() {
        return idUser;
    }

    public void setidUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(long idMatiere) {
        this.idMatiere = idMatiere;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}