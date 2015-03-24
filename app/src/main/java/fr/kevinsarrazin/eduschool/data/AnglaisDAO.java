package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.kevinsarrazin.eduschool.DAOBase;

/**
 * Created by Administrateur on 23/03/2015.
 */
public class AnglaisDAO extends DAOBase {

    public static final String TABLE_NAME = "anglais";
    public static final String KEY = "id";
    public static final String QUESTION = "question";
    public static final String REPONSE = "reponse";
    public static final String MAUVAISE_REPONSE = "mauvaise_reponse";
    public static final String MAUVAISE_REPONSE1 = "mauvaise_reponse1";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QUESTION + " TEXT, "+ REPONSE + " TEXT, "+ MAUVAISE_REPONSE + " TEXT, "+ MAUVAISE_REPONSE1 + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Tableau de données (tag, question, reponse)
    private static final String[] DATA = new String[] {
            "'Partir, laisser quitter', 'Leave, left, left', 'Left, leave, leave', 'Left, leave, left'",
            "'Voir', 'see, saw, seen', 'seen, saw, see', 'saw, seen, see'"
    };


    public AnglaisDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param a la question à ajouter à la base
     */
    public void ajouter(Anglais a) {
        ContentValues value = new ContentValues();
        // Set les données
        value.put(AnglaisDAO.QUESTION, a.getQuestion());
        value.put(AnglaisDAO.REPONSE, a.getReponse());
        value.put(AnglaisDAO.MAUVAISE_REPONSE, a.getMauvaiseReponse());
        value.put(AnglaisDAO.MAUVAISE_REPONSE1, a.getMauvaiseReponse1());
        // Execute l'action
        mDb.insert(AnglaisDAO.TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant de la question à supprimer
     */
    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     *
     * @return liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
     */
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + "("
                + QUESTION + ", "
                + REPONSE + ", "
                + MAUVAISE_REPONSE + ", "
                + MAUVAISE_REPONSE1 + ") VALUES ";

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
     * @param a la question à modifié
     */
    public void modifier(Anglais a) {
        ContentValues value = new ContentValues();
        value.put(QUESTION, a.getQuestion());
        value.put(REPONSE, a.getReponse());
        value.put(MAUVAISE_REPONSE, a.getMauvaiseReponse());
        value.put(MAUVAISE_REPONSE1, a.getMauvaiseReponse1());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(a.getId())});
    }

    /**
     * @param id l'identifiant de la question à récupérer
     */
    public Anglais getQuestion(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Anglais Question = new Anglais();
            Question.setId(c.getLong(0));
            Question.setQuestion(c.getString(1));
            Question.setReponse(c.getString(2));
            Question.setMauvaiseReponse(c.getString(3));
            Question.setMauvaiseReponse1(c.getString(4));
            return Question;
        }
    }

    /**
     *
     */
    public ArrayList<Anglais> getAllQuestion() {
        ArrayList<Anglais> listAnglais = new ArrayList<Anglais>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            while(c.moveToNext()){
                Anglais Question = new Anglais();
                Question.setId(c.getLong(0));
                Question.setQuestion(c.getString(1));
                Question.setReponse(c.getString(2));
                Question.setMauvaiseReponse(c.getString(3));
                Question.setMauvaiseReponse1(c.getString(4));

                listAnglais.add(Question);
            }

            return listAnglais;
        }
    }

}
