package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.kevinsarrazin.eduschool.DAOBase;

/**
 * Created by Administrateur on 23/03/2015.
 */
public class ScoreDAO extends DAOBase {

    public static final String TABLE_NAME = "score";
    public static final String KEY = "id";
    public static final String IDUSER = "idUser";
    public static final String IDMATIERE = "idMatière";
    public static final String SCORE = "score";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ IDUSER + " INTEGER, " + IDMATIERE + " INTEGER, "+ SCORE + " INTEGER );";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public ScoreDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param s le score à ajouter en base de données
     */
    public void ajouter(Score s) {
        ContentValues value = new ContentValues();
        // Set les données
        value.put(IDUSER, s.getidUser());
        value.put(IDMATIERE, s.getIdMatiere());
        value.put(SCORE, s.getScore());
        // Execute l'action
        mDb.insert(ScoreDAO.TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant du score à supprimer
     */
    public void delete(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }


    /**
     * @param s la question à modifié
     */
    public void update(Score s) {
        ContentValues value = new ContentValues();
        value.put(IDUSER, s.getidUser());
        value.put(IDMATIERE, s.getIdMatiere());
        value.put(SCORE, s.getScore());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(s.getId())});
    }

    /**
     * @param id l'identifiant du score à récupérer
     */
    public Score getScore(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Score score = new Score();
            score.setId(c.getLong(0));
            score.setidUser(c.getLong(1));
            score.setIdMatiere(c.getLong(2));
            score.setScore(c.getInt(3));
            return score;
        }
    }

    /**
     * @param idUser l'identifiant du score à récupérer
     */
    public Score getScoreByUser(long idUser) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where idUser = ?", new String[] {String.valueOf(idUser)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Score score = new Score();
            score.setId(c.getLong(0));
            score.setidUser(c.getLong(1));
            score.setIdMatiere(c.getLong(2));
            score.setScore(c.getInt(3));
            return score;
        }
    }

    /**
     * @param idMatiere l'identifiant du score à récupérer
     */
    public Score getScoreByMatière(long idMatiere) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where idMatiere = ?", new String[] {String.valueOf(idMatiere)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            Score score = new Score();
            score.setId(c.getLong(0));
            score.setidUser(c.getLong(1));
            score.setIdMatiere(c.getLong(2));
            score.setScore(c.getInt(3));
            return score;
        }
    }

    /**
     *
     */
    public ArrayList<Score> getAllScore() {
        ArrayList<Score> listScores = new ArrayList<Score>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            while(c.moveToNext()){
                Score score = new Score();
                score.setId(c.getLong(0));
                score.setidUser(c.getLong(1));
                score.setIdMatiere(c.getLong(2));
                score.setScore(c.getInt(3));

                listScores.add(score);
            }

            return listScores;
        }
    }

}
