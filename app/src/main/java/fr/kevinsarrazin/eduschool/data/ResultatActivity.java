package fr.kevinsarrazin.eduschool.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import fr.kevinsarrazin.eduschool.MainActivity;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.activity.MathActivity;
import fr.kevinsarrazin.eduschool.activity.MultiplicationActivity;

public class ResultatActivity extends Activity {

    public final static int MULTIPLICATION_RETOUR_REQUEST = 0;

    private LinearLayout layoutScore;

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        // Récupère le caller
        String caller = getIntent().getStringExtra("caller");

        if (caller.equals("Addition")){
            score = getIntent().getIntExtra(MathActivity.MATH_SCORE, 0);
        }else if (caller.equals("Soustraction")) {
            score = getIntent().getIntExtra(MathActivity.MATH_SCORE, 0);
        }else if (caller.equals("Multiplication")) {
            score = getIntent().getIntExtra(MathActivity.MATH_SCORE, 0);

            // Ajout d'un bouton de choix de table
            layoutScore = (LinearLayout) findViewById(R.id.layoutScore);
            Button btnChoixTable = new Button(this);
            btnChoixTable.setText("Choisir une autre table");
            btnChoixTable.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    onClickChoisirTable(v);
                }
            });

        }else if (caller.equals("Division")) {
            score = getIntent().getIntExtra(MathActivity.MATH_SCORE, 0);
        }

        TextView txtScore = (TextView) findViewById(R.id.txtScore);
        RatingBar scoreBar = (RatingBar) findViewById(R.id.ScoreBar);

        txtScore.setText("Score : "+ score);
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
