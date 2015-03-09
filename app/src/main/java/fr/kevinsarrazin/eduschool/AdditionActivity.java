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

import java.util.ArrayList;


public class AdditionActivity extends Activity {

    public final static int ADDITION_FELICITATION_REQUEST = 0;
    public final static int ADDITION_ERREUR_REQUEST = 0;
    public static final String ACTIVITE_NBERREUR = "erreurs";

    int val1 = 0;
    int val2 = 0;

    ArrayList tabResultat = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        LinearLayout firstlayout = (LinearLayout) findViewById(R.id.lLayoutAddition);

        for (int i = 1; i<=10; i++){
            LinearLayout lLayout = new LinearLayout(this);
            TextView txtView = new TextView(this);
            EditText editTextReponse = new EditText(this);

            val1 = randNb(1, 25);
            val2 = randNb(1, 25);

            tabResultat.add(i-1, val1 + val2);

            txtView.setText(val1 + " + "+ val2 + " = ");
            editTextReponse.setHint(" ? "); // = Placeholder
            editTextReponse.setInputType(InputType.TYPE_CLASS_NUMBER);

            lLayout.addView(txtView);
            lLayout.addView(editTextReponse);
            firstlayout.addView(lLayout);
        }
    }

    /**
     * Renvoie un nombre aléatoire compris entre min et max
     * @param min
     * @param max
     * @return int res
     */
    public int randNb(int min, int max) {
        int res = min + (int)(Math.random() * ((max - min) + 1));
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addition, menu);
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

        LinearLayout lLayoutNumber = (LinearLayout) findViewById(R.id.lLayoutAddition);
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
                int addition = Integer.valueOf(tabResultat.get(i).toString());
                int result = Integer.valueOf(e.getText().toString());

                if (addition == result){
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
            startActivityForResult(intent, ADDITION_FELICITATION_REQUEST);
        }else {
            // Création d'un intention
            Intent intent = new Intent(this, ErreurMultiplicationActivity.class);
            // Ajout de la chaine de nom à l'intent
            intent.putExtra(ACTIVITE_NBERREUR, erreur);
            // lancement de la demande de changement d'activité
            // EXERCICE_4_HELLO_REQUEST est le numéro de la requete
            startActivityForResult(intent, ADDITION_ERREUR_REQUEST);
        }
    }
}
