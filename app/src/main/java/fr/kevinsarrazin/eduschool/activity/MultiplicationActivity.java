package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import fr.kevinsarrazin.eduschool.R;


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
