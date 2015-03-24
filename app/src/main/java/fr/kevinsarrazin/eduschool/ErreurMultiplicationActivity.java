package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.activity.AdditionActivity;
import fr.kevinsarrazin.eduschool.activity.CulturegGeoActivity;
import fr.kevinsarrazin.eduschool.activity.MultiplicationActivity;
import fr.kevinsarrazin.eduschool.activity.MultiplicationCalculActivity;


public class ErreurMultiplicationActivity extends Activity {

    public final static int MULTIPLICATION_RETOUR_REQUEST = 0;
    public final static int MULTIPLICATION_ERREUR_REQUEST = 1;
    public final static int ADDITION_ERREUR_REQUEST = 2;
     int erreur =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erreur_multiplication);

        String caller = getIntent().getStringExtra("caller");

        if (caller.equals("MultiplicationActivity")){
            erreur = getIntent().getIntExtra(MultiplicationCalculActivity.MULTIPLICATION_NBERREUR, 0);
        }else if (caller.equals("AdditionActivity")){
            erreur = getIntent().getIntExtra(AdditionActivity.ADDITION_NBERREUR, 0);
        }else if (caller.equals("CulturegGeoActivity")) {
            erreur = getIntent().getIntExtra(CulturegGeoActivity.GEO_NBERREUR, 0);
        }

        TextView txtViewErreur = (TextView) findViewById(R.id.txtViewErreur);
        txtViewErreur.setText("Nombre d'erreur : " + erreur);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_erreur_multiplication, menu);
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

    public void onClickCorrigerErreur (View vue) {
        setResult(RESULT_OK);
        super.finish();
    }

    public void onClickChoisirTable (View vue) {
        // Création d'un intention
        Intent intent = new Intent(this, MultiplicationActivity.class);
        // lancement de la demande de changement d'activité
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, MULTIPLICATION_RETOUR_REQUEST);
    }

}
