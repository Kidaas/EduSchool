package fr.kevinsarrazin.eduschool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.kevinsarrazin.eduschool.data.CulturegDAO;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.data.QcmDAO;
import fr.kevinsarrazin.eduschool.data.UserDAO;
import fr.kevinsarrazin.eduschool.data.ScoreDAO;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file DatabaseHandler.java
 *
 * Class gérant la création de la bdd
 * Créer la bdd et insère les données
 * update la bdd
 */
public class DatabaseHandler extends SQLiteOpenHelper {

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
        db.execSQL(CulturegDAO.TABLE_CREATE);
        db.execSQL(QcmDAO.TABLE_CREATE);
        db.execSQL(UserDAO.TABLE_CREATE);
        db.execSQL(ScoreDAO.TABLE_CREATE);
        db.execSQL(MatiereDAO.TABLE_CREATE);


        // Insérer les données de la table CultureG
        for (String insert : QcmDAO.getInsertSQL()) {
            db.execSQL(insert);
        }

        // Insérer les données de la table CultureG
        for (String insert : CulturegDAO.getInsertSQL()) {
            db.execSQL(insert);
        }

        // Insérer les données de la table User
        for (String insert : UserDAO.getInsertSQL()) {
            db.execSQL(insert);
        }

        // Insérer les données de la table Matiere
        for (String insert : MatiereDAO.getInsertSQL()) {
            db.execSQL(insert);
        }
        // Insérer les données de la table Score
        for (String insert : ScoreDAO.getInsertSQL()) {
            db.execSQL(insert);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CulturegDAO.TABLE_DROP);
        db.execSQL(QcmDAO.TABLE_DROP);
        db.execSQL(UserDAO.TABLE_DROP);
        db.execSQL(ScoreDAO.TABLE_DROP);
        db.execSQL(MatiereDAO.TABLE_DROP);
        onCreate(db);
    }
}
