package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import fr.kevinsarrazin.eduschool.R;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file MathActivity.java
 *
 *
 * Créer un numberPicker pour choisir la table et l'envoi à l'activité MathActivity
 */
public class MultiplicationActivity extends Activity {

    public static final String MULTIPLICATEUR_MULTIPLICATEUR = "multiplicateur";
    public static final String NIVEAU_NUMBER = "level";

    private NumberPicker nbPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);

        nbPicker = (NumberPicker) findViewById(R.id.numberChoixTable);
        nbPicker.setMaxValue(10);
        nbPicker.setMinValue(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplication, menu);
        return true;
    }

    public void onClickChoixTable(View vue){
        int multiplicateur = nbPicker.getValue();

        // Création d'un intention
        Intent intent = new Intent(this, MathActivity.class);
        // Ajout du niveau d'exercice
        intent.putExtra(NIVEAU_NUMBER, 3);
        intent.putExtra(MULTIPLICATEUR_MULTIPLICATEUR, multiplicateur);
        // lancement de la demande de changement d'activité
        startActivity(intent);
    }

}
