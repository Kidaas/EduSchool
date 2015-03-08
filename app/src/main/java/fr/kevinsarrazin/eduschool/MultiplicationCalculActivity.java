package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MultiplicationCalculActivity extends Activity {

    public static int multiplicateur = 0;
    public final static int MULTIPLICATION_FELICITATION_REQUEST = 0;
    public final static int MULTIPLICATION_ERREUR_REQUEST = 0;
    public static final String MULTIPLICATION_NBERREUR = "erreurs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication_calcul);

        multiplicateur = getIntent().getIntExtra(MultiplicationActivity.MULTIPLICATEUR_MULTIPLICATEUR, 2);

        LinearLayout firstlayout = (LinearLayout) findViewById(R.id.lLayoutNumber);
        firstlayout.setTag("firstlayout");

        for (int i = 1; i<=10; i++){
            LinearLayout lLayout = new LinearLayout(this);
            TextView txtView = new TextView(this);
            EditText editTextReponse = new EditText(this);

            txtView.setText(i + " x " + multiplicateur + " = ");
            editTextReponse.setHint(" ? "); // = Placeholder
            editTextReponse.setInputType(InputType.TYPE_CLASS_NUMBER);

            lLayout.addView(txtView);
            lLayout.addView(editTextReponse);
            firstlayout.addView(lLayout);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplication_calcul, menu);
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

    public void validerResultat(View vue) {

        LinearLayout lLayoutNumber = (LinearLayout) findViewById(R.id.lLayoutNumber);
        Button btn = (Button) findViewById(R.id.btnValiderResult);
        int erreur = 0;

        for (int i = 0; i < lLayoutNumber.getChildCount(); i++){
            // Récupère le layout i du layout principal
            LinearLayout l = (LinearLayout)lLayoutNumber.getChildAt(i);
            // Récupère le EditText du layout l, à la positon 1
            EditText e = (EditText)l.getChildAt(1);

            if (TextUtils.isEmpty(e.getText())){
                erreur++;
            }else {
                int multi = (i + 1) * multiplicateur;
                int result = Integer.valueOf(e.getText().toString());

                if (multi == result){
                    Log.d("result", "ok");
                }else {
                    Log.d("result","ko");
                    erreur++;
                }
            }
        }

        if (erreur == 0){
            // Création d'un intention
            Intent intent = new Intent(this, FelicitationMultiplicationActivity.class);
            // lancement de la demande de changement d'activité
            // EXERCICE_4_HELLO_REQUEST est le numéro de la requete
            startActivityForResult(intent, MULTIPLICATION_FELICITATION_REQUEST);
        }else {
            // Création d'un intention
            Intent intent = new Intent(this, ErreurMultiplicationActivity.class);
            // Ajout de la chaine de nom à l'intent
            intent.putExtra(MULTIPLICATION_NBERREUR, erreur);
            // lancement de la demande de changement d'activité
            // EXERCICE_4_HELLO_REQUEST est le numéro de la requete
            startActivityForResult(intent, MULTIPLICATION_ERREUR_REQUEST);
        }
    }

}
