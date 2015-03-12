package fr.kevinsarrazin.eduschool.data;

/**
 * Created by Administrateur on 09/03/2015.
 */
public class Cultureg {

    private long id;
    private String tag;
    private String question;
    private String reponse;

    public Cultureg() {
        super();
    }

    public Cultureg(String tag, String question, String reponse) {
        super();
        this.tag = tag;
        this.question = question;
        this.reponse = reponse;
    }

    public Cultureg(long id, String tag, String question, String reponse) {
        super();
        this.id = id;
        this.tag = tag;
        this.question = question;
        this.reponse = reponse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}
