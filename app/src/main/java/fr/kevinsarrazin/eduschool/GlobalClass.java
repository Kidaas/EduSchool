package fr.kevinsarrazin.eduschool;

import android.app.Application;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file GlobalClass.java
 *
 * Sert de stockage en session pour les données réutilisable
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
