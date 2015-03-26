package fr.kevinsarrazin.eduschool.data;

/**
 * Created by Administrateur on 23/03/2015.
 */
public class QCM {

    private long id;
    private String tag;
    private String question;
    private String reponse;
    private String mauvaiseReponse;
    private String mauvaiseReponse1;

    public QCM() {
        super();
    }

    public QCM(long id, String tag, String question, String reponse, String mauvaiseReponse, String mauvaiseReponse1) {
        this.id = id;
        this.tag = tag;
        this.question = question;
        this.reponse = reponse;
        this.mauvaiseReponse = mauvaiseReponse;
        this.mauvaiseReponse1 = mauvaiseReponse1;
    }

    public QCM(String tag, String question, String reponse, String mauvaiseReponse, String mauvaiseReponse1) {
        this.tag = tag;
        this.question = question;
        this.reponse = reponse;
        this.mauvaiseReponse = mauvaiseReponse;
        this.mauvaiseReponse1 = mauvaiseReponse1;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
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

    public String getMauvaiseReponse() {
        return mauvaiseReponse;
    }

    public void setMauvaiseReponse(String mauvaiseReponse) {
        this.mauvaiseReponse = mauvaiseReponse;
    }

    public String getMauvaiseReponse1() {
        return mauvaiseReponse1;
    }

    public void setMauvaiseReponse1(String mauvaiseReponse1) {
        this.mauvaiseReponse1 = mauvaiseReponse1;
    }
}