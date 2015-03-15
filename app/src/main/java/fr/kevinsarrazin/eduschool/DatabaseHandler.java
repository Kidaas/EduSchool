package fr.kevinsarrazin.eduschool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Création des tables
        db.execSQL(CULTUREG_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CULTUREG_TABLE_DROP);
        db.execSQL(USER_TABLE_DROP);
        onCreate(db);
    }
}
