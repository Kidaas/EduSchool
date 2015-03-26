package fr.kevinsarrazin.eduschool;

import android.app.Application;

/**
 * Created by Administrateur on 26/03/2015.
 */
public class GlobalClass extends Application{
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
