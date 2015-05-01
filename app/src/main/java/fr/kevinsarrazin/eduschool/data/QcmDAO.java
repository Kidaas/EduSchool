package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

import fr.kevinsarrazin.eduschool.DAOBase;

/**
 * Created by Administrateur on 23/03/2015.
 */
public class QcmDAO extends DAOBase {

    public static final String TABLE_NAME = "qcm";
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
            "'Departement', 'Ain',              '01', '05', '12'",
            "'Departement', 'Hautes-Alpes',     '05', '04', '13'",
            "'Departement', 'Drome',            '26', '38', '44'",
            "'Departement', 'Cantal',           '15', '30', '45'",
            "'Departement', 'Bouches-du-Rhône', '13', '15', '18'",
            "'Departement', 'Aude',             '11', '15', '32'",
            "'Departement', 'Finistère',        '29', '25', '12'",
            "'Departement', 'Hérault',          '34', '85', '83'",
            "'Departement', 'Jura',             '39', '40', '41'",
            "'Departement', 'Lot-et-Garonne',   '47', '35', '12'",
            "'Departement', 'Manche',           '50', '23', '44'",
            "'Departement', 'Nord',             '59', '12', '65'",
            "'Departement', 'Hautes-Pyrénées',  '65', '59', '12'",
            "'Departement', 'Haut-Rhin',        '68', '55', '22'",
            "'Departement', 'Savoie',           '73', '24', '05'",
            "'Departement', 'Yvelines',         '78', '75', '22'",
            "'Departement', 'Var',              '83', '04', '12'",
            "'Departement', 'Vosges',           '88', '83', '55'",
            "'Departement', 'La Réunion',       '974', '973', '976'",
            "'Departement', 'Meuse',            '55', '21', '45'",
            "'Departement', 'Cote d Or',        '21', '85', '12'",
            "'Departement', 'Dordogne',         '24', '54', '26'"

    };


    public QcmDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param a la question à ajouter à la base
     */
    public void insert(Qcm a) {
        ContentValues value = new ContentValues();
        // Set les données
        value.put(QcmDAO.TAG, a.getTag());
        value.put(QcmDAO.QUESTION, a.getQuestion());
        value.put(QcmDAO.REPONSE, a.getReponse());
        value.put(QcmDAO.MAUVAISE_REPONSE, a.getMauvaiseReponse());
        value.put(QcmDAO.MAUVAISE_REPONSE1, a.getMauvaiseReponse1());
        // Execute l'action
        mDb.insert(QcmDAO.TABLE_NAME, null, value);
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
    public void update(Qcm a) {
        if(getQuestion(a.getId()) == null){
            insert(a);
        }else{
            ContentValues value = new ContentValues();
            value.put(TAG, a.getTag());
            value.put(QUESTION, a.getQuestion());
            value.put(REPONSE, a.getReponse());
            value.put(MAUVAISE_REPONSE, a.getMauvaiseReponse());
            value.put(MAUVAISE_REPONSE1, a.getMauvaiseReponse1());
            mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(a.getId())});
        }
    }

    /**
     * @param id l'identifiant de la question à récupérer
     */
    public Qcm getQuestion(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Qcm Question = new Qcm();
            Question.setId(c.getLong(0));
            Question.setTag(c.getString(1));
            Question.setQuestion(c.getString(2));
            Question.setReponse(c.getString(3));
            Question.setMauvaiseReponse(c.getString(4));
            Question.setMauvaiseReponse1(c.getString(5));
            return Question;
        }
    }

    public Qcm getQcmRandom(String tag) {

        //Récupère dans un Cursor les valeurs correspondants à une question au hasard
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE tag = ? ORDER BY RANDOM() LIMIT 1", new String[]{tag});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élement
            c.moveToFirst();
            Qcm Question = new Qcm();
            Question.setId(c.getLong(0));
            Question.setTag(c.getString(1));
            Question.setQuestion(c.getString(2));
            Question.setReponse(c.getString(3));
            Question.setMauvaiseReponse(c.getString(4));
            Question.setMauvaiseReponse1(c.getString(5));
            return Question;
        }
    }

}