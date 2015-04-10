package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import fr.kevinsarrazin.eduschool.DAOBase;

public class CulturegDAO extends DAOBase {
    public static final String TABLE_NAME = "cultureg";
    public static final String KEY = "id";
    public static final String TAG = "tag";
    public static final String QUESTION = "question";
    public static final String REPONSE = "reponse";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAG + " TEXT, "+ QUESTION + " TEXT, "+ REPONSE + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Tableau de données (tag, question, reponse)
    private static final String[] DATA = new String[] {
            "'Geographie', 'Capitale de la France ? ', 'Paris'",
            "'Geographie', 'Capitale de l\''Espagne ? ', 'Madrid'",
            "'Geographie', 'Capitale de la Belgique ? ', 'Bruxelles'",
            "'Geographie', 'Capitale des Etats Unis ? ', 'Washington'",
            "'Geographie', 'Capitale de la Chine ? ', 'Pekin'",
            "'Geographie', 'Capitale du Japon ? ', 'Tokyo'",
            "'Geographie', 'Capitale de l\''Italie ? ', 'Rome'",
            "'Français', 'test', 'test'"
    };

    public CulturegDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     *
     * @return liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
     */
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + "("
                + TAG + ", "
                + QUESTION + ", "
                + REPONSE + ") VALUES ";

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
     * @param c la question à ajouter à la base
     */
    public void insert(Cultureg c) {

        ContentValues value = new ContentValues();
        // Set les données
        value.put(CulturegDAO.TAG, c.getTag());
        value.put(CulturegDAO.QUESTION, c.getQuestion());
        value.put(CulturegDAO.REPONSE, c.getReponse());
        // Execute l'action
        mDb.insert(CulturegDAO.TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant de la question à supprimer
     */
    public void delete(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param c la question à modifié
     */
    public void update(Cultureg c) {
        if(getQuestion(c.getId()) == null){
            insert(c);
        }else {
            ContentValues value = new ContentValues();
            value.put(TAG, c.getTag());
            value.put(QUESTION, c.getQuestion());
            value.put(REPONSE, c.getReponse());
            mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(c.getId())});
        }
    }

    /**
     * @param id l'identifiant de la question à récupérer
     */
    public Cultureg getQuestion(long id) {
        Cursor c = mDb.rawQuery("select " + TAG +", " + QUESTION + ", " + REPONSE + " from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Cultureg Question = new Cultureg();
              //Question.setId(c.getLong(0));
              Question.setTag(c.getString(0));
              Question.setQuestion(c.getString(1));
              Question.setReponse(c.getString(2));
            return Question;
        }
    }

    /**
     *
     */
    public ArrayList<Cultureg> getAllCultureg() {
        ArrayList<Cultureg> listCultureg = new ArrayList<Cultureg>();
        Cursor c = mDb.rawQuery("select " + TAG +", " + QUESTION + ", " + REPONSE + " from " + TABLE_NAME, null);

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            while(c.moveToNext()){
                Cultureg cultureg = new Cultureg();
                //Question.setId(c.getLong(0));
                cultureg.setTag(c.getString(0));
                cultureg.setQuestion(c.getString(1));
                cultureg.setReponse(c.getString(2));

                listCultureg.add(cultureg);
            }

            return listCultureg;
        }
    }

    public ArrayList<Cultureg> getAllCulturegByTag(String tag) {
        ArrayList<Cultureg> listCultureg = new ArrayList<Cultureg>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where tag = ?", new String[]{tag});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            while(c.moveToNext()){
                Cultureg cultureg = new Cultureg();
                cultureg.setId(c.getLong(0));
                cultureg.setTag(c.getString(1));
                cultureg.setQuestion(c.getString(2));
                cultureg.setReponse(c.getString(3));

                listCultureg.add(cultureg);
            }

            return listCultureg;
        }
    }

    public Cultureg getQuestionRandom(String tag) {

        //Récupère dans un Cursor les valeurs correspondants à une question au hasard
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE tag = ? ORDER BY RANDOM() LIMIT 1", new String[]{tag});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élement
            c.moveToFirst();
            Cultureg Question = new Cultureg();
            Question.setId(c.getLong(0));
            Question.setTag(c.getString(1));
            Question.setQuestion(c.getString(2));
            Question.setReponse(c.getString(3));
            return Question;
        }
    }
}
