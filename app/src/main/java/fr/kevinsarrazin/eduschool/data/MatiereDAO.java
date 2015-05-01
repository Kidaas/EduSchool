package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

import fr.kevinsarrazin.eduschool.DAOBase;

/**
 * Created by Administrateur on 23/03/2015.
 */
public class MatiereDAO extends DAOBase {

    public static final String TABLE_NAME = "matiere";
    public static final String KEY = "id";
    public static final String LIBELLE = "libelle";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ LIBELLE + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Tableau de données (tag, question, reponse)
    private static final String[] DATA = new String[] {
            "'Anglais'",
            "'Français'",
            "'Addition'",
            "'Soustraction'",
            "'Multiplication'",
            "'Division'",
            "'Geographie'",
            "'Departement'"
    };


    public MatiereDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param m la matiere à ajouter à la base
     */
    public void insert(Matiere m) {
        ContentValues value = new ContentValues();
        // Set les données
        value.put(MatiereDAO.LIBELLE, m.getLibelle());
        // Execute l'action
        mDb.insert(MatiereDAO.TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant de la question à supprimer
     */
    public void delete(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     *
     * @return liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
     */
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + "("
                + LIBELLE + ") VALUES ";

        String[] liste = new String[DATA.length];
        int i = 0;
        for (String questionReponse : DATA) {
            // Instruction SQL INSERT
            liste[i] = insertSQL + "(" + questionReponse + ")";
            i++;
        }

        return liste;
    }


    /**
     * @param m la matière
     */
    public void update(Matiere m) {
        if(getMatiere(m.getId()) == null){
            insert(m);
        }else {
            ContentValues value = new ContentValues();
            value.put(LIBELLE, m.getLibelle());
            mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(m.getId())});
        }

    }

    /**
     * @param id l'identifiant de la matière à récupérer
     */
    public Matiere getMatiere(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Matiere matiere = new Matiere();
            matiere.setId(c.getLong(0));
            matiere.setLibelle(c.getString(1));
            return matiere;
        }
    }

    /**
     * @param libelle de la matière à récupérer
     */
    public Matiere getMatiereByLibelle(String libelle) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where libelle = ?", new String[] {libelle});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Matiere matiere = new Matiere();
            matiere.setId(c.getLong(0));
            matiere.setLibelle(c.getString(1));
            return matiere;
        }
    }

    /**
     *
     */
    public ArrayList<Matiere> getAllQuestion() {
        ArrayList<Matiere> listMatiere = new ArrayList<Matiere>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            while(c.moveToNext()){
                Matiere matiere = new Matiere();
                matiere.setId(c.getLong(0));
                matiere.setLibelle(c.getString(1));

                listMatiere.add(matiere);
            }

            return listMatiere;
        }
    }

}
