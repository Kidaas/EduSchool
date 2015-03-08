package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;


public class MultiplicationActivity extends Activity {

    public static final String MULTIPLICATEUR_MULTIPLICATEUR = "multiplicateur";
    public final static int MULTIPLICATION_HELLO_REQUEST = 0;
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
        Intent intent = new Intent(this, MultiplicationCalculActivity.class);
        // Ajout du multiplicateur
        intent.putExtra(MULTIPLICATEUR_MULTIPLICATEUR, multiplicateur);
        // lancement de la demande de changement d'activité
        // MULTIPLICATION_HELLO_REQUEST est le numéro de la requete
        startActivityForResult(intent, MULTIPLICATION_HELLO_REQUEST);
    }

}
