package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fr.kevinsarrazin.eduschool.R;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file QcmActivity.java
 *
 * Class gérant l'activité QCM
 * Génère les question, les valides et enregistre le score
 */
public class QcmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qcm, menu);
        return true;
    }
}
