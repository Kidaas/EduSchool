package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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

    public CulturegDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param c la question à ajouter à la base
     */
    public void ajouter(Cultureg c) {
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
    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param c la question à modifié
     */
    public void modifier(Cultureg c) {
        ContentValues value = new ContentValues();
        value.put(TAG, c.getTag());
        value.put(QUESTION, c.getQuestion());
        value.put(REPONSE, c.getReponse());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(c.getId())});
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
    public ArrayList<Cultureg> getAllCultureg(String tag) {
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
        Cursor c = mDb.rawQuery("select " + TAG +", " + QUESTION + ", " + REPONSE + " from " + TABLE_NAME+ " where tag = ?", new String[] {tag});

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
}
