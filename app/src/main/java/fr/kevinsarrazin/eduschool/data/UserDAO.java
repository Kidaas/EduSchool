package fr.kevinsarrazin.eduschool.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.kevinsarrazin.eduschool.DAOBase;

/**
 * Created by Administrateur on 10/03/2015.
 */
public class UserDAO extends DAOBase {
    public static final String TABLE_NAME = "user";
    public static final String KEY = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String PATHIMAGE = "pathImage";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOGIN + " TEXT, "+ PASSWORD + " TEXT, "+ NOM + " TEXT, "+ PRENOM + " TEXT, "+ PATHIMAGE + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Tableau de données (login, password)
    private static final String[] DATA = new String[] {
            "'kidaas', 'kidaas', 'Sarrazin', 'kevin', null"
    };

    public UserDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     *
     * @return liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
     */
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_NAME + "("
                + LOGIN + ", "
                + PASSWORD + ", "
                + NOM + ", "
                + PRENOM + ", "
                + PATHIMAGE + ") VALUES ";

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
     * @param u la question à ajouter à la base
     */
    public void insert(User u) {
        ContentValues value = new ContentValues();
        // Set les données
        value.put(UserDAO.LOGIN, u.getLogin());
        value.put(UserDAO.PASSWORD, u.getPassword());
        value.put(UserDAO.NOM, u.getNom());
        value.put(UserDAO.PRENOM, u.getPrenom());
        value.put(UserDAO.PATHIMAGE, u.getPathImage());
        // Execute l'action
        mDb.insert(UserDAO.TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant de l'utilisateur
     */
    public void delete(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param u l'utilisateur modifié
     */
    public void update(User  u) {
        if(getUserById(u.getId()) == null){
            insert(u);
        }else {
            ContentValues value = new ContentValues();
            value.put(LOGIN, u.getLogin());
            value.put(PASSWORD, u.getPassword());
            value.put(PATHIMAGE, u.getPathImage());
            value.put(UserDAO.PRENOM, u.getPrenom());
            value.put(UserDAO.PATHIMAGE, u.getPathImage());
            mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(u.getId())});
        }
    }

    /**
     * @param id l'identifiant de l'utilisateur
     */
    public User getUserById(long id) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            User user = new User();
            user.setId(c.getLong(0));
            user.setLogin(c.getString(1));
            user.setPassword(c.getString(2));
            user.setNom(c.getString(3));
            user.setPrenom(c.getString(4));
            user.setPathImage(c.getString(5));
            return user;
        }
    }

    /**
     * @param login, l'identifiant de l'utilisateur
     * @return User user
     */
    public User getUserByLogin(String login) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where login = ?", new String[] {login});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            User user = new User();
            user.setId(c.getLong(0));
            user.setLogin(c.getString(1));
            user.setPassword(c.getString(2));
            user.setNom(c.getString(3));
            user.setPrenom(c.getString(4));
            user.setPathImage(c.getString(5));
            return user;
        }
    }

}

