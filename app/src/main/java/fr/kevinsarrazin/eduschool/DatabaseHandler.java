package fr.kevinsarrazin.eduschool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.kevinsarrazin.eduschool.data.CulturegDAO;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.data.UserDAO;
import fr.kevinsarrazin.eduschool.data.QcmDAO;

/**
 * Created by Administrateur on 09/03/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    /*********************************************
     *      Table Culture Genérale
     ********************************************/
    public static final String CULTUREG_KEY = "id";
    public static final String CULTUREG_TAG = "tag";
    public static final String CULTUREG_QUESTION = "question";
    public static final String CULTUREG_REPONSE = "reponse";

    public static final String CULTUREG_TABLE_NAME = "cultureg";
    public static final String CULTUREG_TABLE_CREATE =
                        "CREATE TABLE " + CULTUREG_TABLE_NAME + " (" +
                        CULTUREG_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CULTUREG_TAG + " TEXT, " +
                        CULTUREG_QUESTION + " TEXT, " +
                        CULTUREG_REPONSE + " TEXT);";

    public static final String CULTUREG_TABLE_DROP = "DROP TABLE IF EXISTS " + CULTUREG_TABLE_NAME + ";";

    /*********************************************
     *      Table User
     ********************************************/
    public static final String USER_KEY = "id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_LOGIN + " TEXT, " +
                    USER_PASSWORD + " TEXT);";

    public static final String USER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    /*********************************************
     *      Table QCM
     ********************************************/
    public static final String QCM_KEY = "id";
    public static final String QCM_QUESTION = "question";
    public static final String QCM_REPONSE = "reponse";
    public static final String QCM_MAUVAISE_REPONSE = "mauvaise_reponse";
    public static final String QCM_MAUVAISE_REPONSE1 = "mauvaise_reponse1";

    public static final String QCM_TABLE_NAME = "qcm";
    public static final String QCM_TABLE_CREATE =
            "CREATE TABLE " + QCM_TABLE_NAME + " (" +
                    QCM_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QCM_QUESTION + " TEXT, " +
                    QCM_REPONSE + " TEXT, " +
                    QCM_MAUVAISE_REPONSE + " TEXT, " +
                    QCM_MAUVAISE_REPONSE1 + " TEXT);";

    public static final String QCM_TABLE_DROP = "DROP TABLE IF EXISTS " + QCM_TABLE_NAME + ";";

    /*********************************************
     *      Table Score
     ********************************************/
    public static final String SCORE_KEY = "id";
    public static final String SCORE_IDUSER = "idUser";
    public static final String SCORER_IDMATIERE = "idMatiere";
    public static final String SCORE_SCORE = "score";

    public static final String SCORE_TABLE_NAME = "score";
    public static final String SCORE_TABLE_CREATE =
            "CREATE TABLE " + SCORE_TABLE_NAME + " (" +
                    SCORE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SCORE_IDUSER + " INTEGER, " +
                    SCORER_IDMATIERE + " INTEGER, " +
                    SCORE_SCORE + " INTEGER);";

    public static final String SCORE_TABLE_DROP = "DROP TABLE IF EXISTS " + SCORE_TABLE_NAME + ";";

    /*********************************************
     *      Table Matiere
     ********************************************/
    public static final String MATIERE_KEY = "id";
    public static final String MATIERE_LIBELLE = "libelle";

    public static final String MATIERE_TABLE_NAME = "matiere";
    public static final String MATIERE_TABLE_CREATE =
            "CREATE TABLE " + MATIERE_TABLE_NAME + " (" +
                    MATIERE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MATIERE_LIBELLE + " TEXT);";

    public static final String MATIERE_TABLE_DROP = "DROP TABLE IF EXISTS " + MATIERE_TABLE_NAME + ";";

    /*********************************************
     *      DatabaseHandler
     ********************************************/

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Création des tables de la bdd et insertion des données
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Création des tables
        db.execSQL(CULTUREG_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(QCM_TABLE_CREATE);
        db.execSQL(SCORE_TABLE_CREATE);
        db.execSQL(MATIERE_TABLE_CREATE);

        // Insérer les données de la table CultureG
        for (String insert : CulturegDAO.getInsertSQL()) {
            db.execSQL(insert);
        }

        // Insérer les données de la table User
        for (String insert : UserDAO.getInsertSQL()) {
            db.execSQL(insert);
        }

        // Insérer les données de la table QCM
        for (String insert : QcmDAO.getInsertSQL()) {
            db.execSQL(insert);
        }
        // Insérer les données de la table Matiere
        for (String insert : MatiereDAO.getInsertSQL()) {
            db.execSQL(insert);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CULTUREG_TABLE_DROP);
        db.execSQL(USER_TABLE_DROP);
        db.execSQL(QCM_TABLE_DROP);
        db.execSQL(SCORE_TABLE_DROP);
        db.execSQL(MATIERE_TABLE_DROP);
        onCreate(db);
    }
}
