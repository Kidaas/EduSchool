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
    public static final String IDMATIERE = "idMatiere";
    public static final String SCORE = "score";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ IDUSER + " INTEGER, " + IDMATIERE + " INTEGER, "+ SCORE + " INTEGER );";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Tableau de données (idUser, idMatiere, score)
    private static final String[] DATA = new String[] {
            "'2', '1', '1'",
            "'2', '2', '2'",
            "'2', '3', '3'",
            "'2', '4', '4'",
            "'2', '5', '5'"
    };

    public ScoreDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     *
     * @return liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
     */
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + "("
                + IDUSER + ", "
                + IDMATIERE + ", "
                + SCORE + ") VALUES ";

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
     * @param s le score à ajouter en base de données
     */
    public void insert(Score s) {

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
        if(getScore(s.getId()) ==  null){
            insert(s);
        }else {
            ContentValues value = new ContentValues();
            value.put(IDUSER, s.getidUser());
            value.put(IDMATIERE, s.getIdMatiere());
            value.put(SCORE, s.getScore());
            mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(s.getId())});
        }
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

/*    *//**
     * @param idUser l'identifiant du score à récupérer
     *//*
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
    }*/

/*    *//**
     * @param idMatiere l'identifiant du score à récupérer
     *//*
    public Score getScoreByMatiere(long idMatiere) {
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
    }*/

    /**
     * @param idMatiere l'identifiant du score à récupérer
     */
    public Score getScoreByMatiereAndUser(long idMatiere, long idUser) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where idMatiere = ? AND idUser = ?", new String[] {String.valueOf(idMatiere), String.valueOf(idUser)});

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
    public ArrayList<Score> getAllScoreByUser(long idUser) {
        ArrayList<Score> listScores = new ArrayList<Score>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where idUser = ?", new String[] {String.valueOf(idUser)});

        // Si il ne retourne rien => retourne null
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
