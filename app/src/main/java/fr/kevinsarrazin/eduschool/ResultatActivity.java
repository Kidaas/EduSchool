package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import fr.kevinsarrazin.eduschool.MainActivity;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.activity.CulturegActivity;
import fr.kevinsarrazin.eduschool.activity.MathActivity;
import fr.kevinsarrazin.eduschool.activity.MultiplicationActivity;
import fr.kevinsarrazin.eduschool.data.Cultureg;

public class ResultatActivity extends Activity {

    public final static int MULTIPLICATION_RETOUR_REQUEST = 0;

    private LinearLayout layoutScore;
    private TextView txtMeilleurScore, txtScore;
    private RatingBar scoreBar;

    private int score = 0, meilleurScore = 0;
    private GlobalClass globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        txtScore = (TextView) findViewById(R.id.txtScore);
        txtMeilleurScore = (TextView) findViewById(R.id.txtMeilleurScore);
        scoreBar = (RatingBar) findViewById(R.id.ScoreBar);
        layoutScore = (LinearLayout) findViewById(R.id.layoutScore);

        // Récupère le caller
        String caller = getIntent().getStringExtra("caller");
        globalVariable = (GlobalClass) getApplicationContext();


        if (caller.equals("Addition") || caller.equals("Soustraction") || caller.equals("Division")){
            score = getIntent().getIntExtra(MathActivity.MATH_SCORE, 0);
            meilleurScore = getIntent().getIntExtra(MathActivity.MATH_MEILLEUR_SCORE, 0);
        }else if (caller.equals("Multiplication")) {
            score = getIntent().getIntExtra(MathActivity.MATH_SCORE, 0);
            meilleurScore = getIntent().getIntExtra(MathActivity.MATH_MEILLEUR_SCORE, 0);

            // Ajout d'un bouton de choix de table
            Button btnChoixTable = new Button(this);
            btnChoixTable.setText("Choisir une autre table");
            btnChoixTable.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    onClickChoisirTable(v);
                }
            });
        }else if(caller.equals("Cultureg")){
            score = getIntent().getIntExtra(CulturegActivity.CULTUREG_SCORE, 0);
            meilleurScore = getIntent().getIntExtra(CulturegActivity.CULTUREG_MEILLEUR_SCORE, 0);
        }

        if (globalVariable.getId() == 0){
            TextView txtMessage = new TextView(this);
            txtMessage.setText("Enregistrez vous pour sauvegarder vos scores");
            layoutScore.addView(txtMessage);
        }

        txtScore.setText("Score : "+ score);
        txtMeilleurScore.setText("Meilleur score : "+ meilleurScore);
        // Nombre d'étoile :
        int nbEtoiles = (int) Math.ceil(score/2);
        scoreBar.setNumStars(nbEtoiles);

    }

    /**
     * Changer d'exercice
     * @param vue
     */
    public void onClickChoisirExercice (View vue) {
        // Création d'un intention
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Changer de table de multiplication
     * @param vue
     */
    public void onClickChoisirTable (View vue) {
        // Création d'un intention
        Intent intent = new Intent(this, MultiplicationActivity.class);
        // lancement de la demande de changement d'activité
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, MULTIPLICATION_RETOUR_REQUEST);
    }

    /**
     * Le bouton retour renvoi à l'accueil
     */
    @Override
    public void onBackPressed() {
        // Création d'une intention
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resultat, menu);
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
