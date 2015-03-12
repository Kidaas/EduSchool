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

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOGIN + " TEXT, "+ PASSWORD + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public UserDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param u la question à ajouter à la base
     */
    public void ajouter(User u) {
        ContentValues value = new ContentValues();
        // Set les données
        value.put(UserDAO.LOGIN, u.getLogin());
        value.put(UserDAO.PASSWORD, u.getPassword());
        // Execute l'action
        mDb.insert(UserDAO.TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant de l'utilisateur
     */
    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param u l'utilisateur modifié
     */
    public void modifier(User  u) {
        ContentValues value = new ContentValues();
        value.put(LOGIN, u.getLogin());
        value.put(PASSWORD, u.getPassword());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(u.getId())});
    }

    /**
     * @param id l'identifiant de l'utilisateur
     */
    public User getUserById(long id) {
        Cursor c = mDb.rawQuery("select " + LOGIN +", " + PASSWORD + " from " + TABLE_NAME + " where id = ?", new String[] {String.valueOf(id)});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            User user = new User();
            user.setLogin(c.getString(0));
            user.setPassword(c.getString(1));
            return user;
        }
    }

    /**
     * @param login, l'identifiant de l'utilisateur
     * @return User user
     */
    public User getUserByLogin(String login) {
        Cursor c = mDb.rawQuery("select " + LOGIN +", " + PASSWORD + " from " + TABLE_NAME + " where login = ?", new String[] {login});

        // Si il ne retourne rien, => retourne null
        if (c.getCount() == 0) {
            return null;
        }else {
            // On va sur le 1er élements
            c.moveToFirst();
            User user = new User();
            user.setLogin(c.getString(0));
            user.setPassword(c.getString(1));
            return user;
        }
    }

}

