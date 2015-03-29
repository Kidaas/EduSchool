package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.data.ResultatActivity;


public class MathActivity extends Activity {

    public static final String MATH_SCORE = "MathScore";

    private int level, multiplicateur, i = 1;
    private int val1, val2, result;
    private int fin = 1, bonnesReponses = 0, erreurs = 0;
    private TextView txtVal1;
    private TextView txtVal2;
    private EditText EditTxtResult;
    private Button btnNext, btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        level = getIntent().getIntExtra(NiveauActivity.NIVEAU_NUMBER, 1);

        TextView txtOperateur = (TextView) findViewById(R.id.txtViewOperateur);
        txtVal1 = (TextView) findViewById(R.id.txtViewVal1);
        txtVal2 = (TextView) findViewById(R.id.txtViewVal2);
        EditTxtResult = (EditText) findViewById(R.id.editTxtResult);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnValider = (Button) findViewById(R.id.btnValider);

        EditTxtResult.setHint(" ? "); // = Placeholder


        switch(level) {
            case 1:
                txtOperateur.setText(" + ");
                break;
            case 2:
                txtOperateur.setText(" - ");
                break;
            case 3:
                multiplicateur = getIntent().getIntExtra(MultiplicationActivity.MULTIPLICATEUR_MULTIPLICATEUR, 1);
                txtOperateur.setText(" x ");
                break;
            case 4:
                txtOperateur.setText(" / ");
                break;
        }
        btnNext.setVisibility(View.INVISIBLE);
        Calcul();

    }

    /**
     * Vérifie le calcul
     * Si c'est la 10eme reponses => Affiche la bin
     * @param vue
     */
    public void onClickValider(View vue) {

        // Si le champ est vide ou différent du résultat attentendu => erreur +1
        if(TextUtils.isEmpty(EditTxtResult.getText()) || Integer.valueOf(EditTxtResult.getText().toString()) != result){
            erreurs++;
            String notification =  "Erreur";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        // Sinon, bonne réponse +1
        }else {
            bonnesReponses++;
            String notification =  "Bonne réponse";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        }

        // Si c'est la 10eme réponse, l'activité est fini
        if (fin >= 10){

            // Création d'un intention
            Intent intent = new Intent(this, ResultatActivity.class);
            // Ajout de la chaine de nom à l'intent
            intent.putExtra(MATH_SCORE, bonnesReponses);

            switch(level) {
                case 1:
                    intent.putExtra("caller", "Addition");
                    break;
                case 2:
                    intent.putExtra("caller", "Soustraction");
                    break;
                case 3:
                    intent.putExtra("caller", "Multiplication");
                    break;
                case 4:
                    intent.putExtra("caller", "Division");
                    break;
            }
            // lancement de la demande de changement d'activité
            startActivity(intent);

        }

        // Masque le bouton valider, affiche le bouton suivant
        btnValider.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.VISIBLE);

        fin++;

    }

    /**
     * Affiche le prochain calcul
     * @param vue
     */
    public void onClickSuivant(View vue) {
        Calcul();
        EditTxtResult.setText("");
        btnValider.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
    }


    /**
     * Créer un calcul en fonciton du niveau choisi
     */
    public void Calcul() {
        switch(level) {
            case 1:
                val1 = randNb(0,20);
                val2 = randNb(0,20);
                result = val1 + val2;
                break;
            case 2:
                val1 = randNb(0,20);
                val2 = randNb(0, val1);
                result = val1 - val2;
                break;
            case 3:
                val1 = i++;
                val2 = multiplicateur;
                result = val1 * val2;
                break;
            case 4:
                val1 = randNb(1,20);
                List diviseurs = new ArrayList<Integer>();
                for(int i=1;i<=val1;i++) {
                    if(val1%i==0){
                        diviseurs.add(i);
                    }
                }
                int temp = randNb(0,diviseurs.size()-1);
                val2 = (Integer)diviseurs.get(temp);
                result = val1 / val2;
                break;
        }

        // Affecte les valeurs aux champs
        txtVal1.setText("" + val1);
        txtVal2.setText("" + val2);
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
        getMenuInflater().inflate(R.menu.menu_math, menu);
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
}
