package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.kevinsarrazin.eduschool.DAOBase;

/**
 * Created by Administrateur on 23/03/2015.
 */
public class QCMDAO extends DAOBase {

    public static final String TABLE_NAME = "anglais";
    public static final String KEY = "id";
    public static final String TAG = "tag";
    public static final String QUESTION = "question";
    public static final String REPONSE = "reponse";
    public static final String MAUVAISE_REPONSE = "mauvaise_reponse";
    public static final String MAUVAISE_REPONSE1 = "mauvaise_reponse1";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TAG + " TEXT, " + QUESTION + " TEXT, "+ REPONSE + " TEXT, "+ MAUVAISE_REPONSE + " TEXT, "+ MAUVAISE_REPONSE1 + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Tableau de données (tag, question, reponse)
    private static final String[] DATA = new String[] {
            "'Anglais', 'Partir, laisser quitter', 'Leave, left, left', 'Left, leave, leave', 'Left, leave, left'",
            "'Anglais', 'Voir', 'see, saw, seen', 'seen, saw, see', 'saw, seen, see'"
    };


    public QCMDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param a la question à ajouter à la base
     */
    public void ajouter(QCM a) {
        ContentValues value = new ContentValues();
        // Set les données
        value.put(QCMDAO.TAG, a.getTag());
        value.put(QCMDAO.QUESTION, a.getQuestion());
        value.put(QCMDAO.REPONSE, a.getReponse());
        value.put(QCMDAO.MAUVAISE_REPONSE, a.getMauvaiseReponse());
        value.put(QCMDAO.MAUVAISE_REPONSE1, a.getMauvaiseReponse1());
        // Execute l'action
        mDb.insert(QCMDAO.TABLE_NAME, null, value);
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
                + TAG + ", "
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
    public void modifier(QCM a) {
        ContentValues value = new ContentValues();
        value.put(TAG, a.getTag());
        value.put(QUESTION, a.getQuestion());
        value.put(REPONSE, a.getReponse());
        value.put(MAUVAISE_REPONSE, a.getMauvaiseReponse());
        value.put(MAUVAISE_REPONSE1, a.getMauvaiseReponse1());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(a.getId())});
    }

    /**
     * @param id l'identifiant de la question à récupérer
     */
    public QCM getQuestion(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            QCM Question = new QCM();
            Question.setId(c.getLong(0));
            Question.setTag(c.getString(1));
            Question.setQuestion(c.getString(2));
            Question.setReponse(c.getString(3));
            Question.setMauvaiseReponse(c.getString(4));
            Question.setMauvaiseReponse1(c.getString(5));
            return Question;
        }
    }

    /**
     *
     */
    public ArrayList<QCM> getAllQuestion() {
        ArrayList<QCM> listAnglais = new ArrayList<QCM>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            while(c.moveToNext()){
                QCM Question = new QCM();
                Question.setId(c.getLong(0));
                Question.setTag(c.getString(1));
                Question.setQuestion(c.getString(2));
                Question.setReponse(c.getString(3));
                Question.setMauvaiseReponse(c.getString(4));
                Question.setMauvaiseReponse1(c.getString(5));

                listAnglais.add(Question);
            }

            return listAnglais;
        }
    }

}
