package fr.kevinsarrazin.eduschool.data;

/**
 * Created by Administrateur on 09/03/2015.
 */
public class Matiere {

    private long id;
    private String libelle;

    public Matiere() {
        super();
    }

    public Matiere(String libelle) {
        this.libelle = libelle;
    }

    public Matiere(long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}