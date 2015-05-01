package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.ResultatActivity;
import fr.kevinsarrazin.eduschool.data.Score;
import fr.kevinsarrazin.eduschool.data.ScoreDAO;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file MathActivity.java
 *
 * Class gérant l'activité Math
 * Génère les calcul, les valides et enregistre le score
 */
public class MathActivity extends Activity {

    public static final String MATH_SCORE = "MathScore";
    public static final String MATH_MEILLEUR_SCORE = "MathMeilleurScore";
    static final String STATE_SCORE = "score";
    static final String STATE_LEVEL = "level";
    static final String STATE_NBTOUR = "nbTour";
    static final String STATE_MULTIPLICATION = "i";
    static final String STATE_VAL1 = "val1";
    static final String STATE_VAL2= "val2";
    static final String STATE_RESULT= "result";

    private int level, multiplicateur, i = 1;
    private int val1, val2, result;
    private int tour = 0, bonnesReponses = 0, meilleurScore, nbTourDeJeu = 10;
    private GlobalClass globalVariable;

    private String operateur;
    private TextView txtVal1, txtVal2, txtViewReponse;
    private EditText EditTxtResult;
    private Button btnNext, btnValider;
    private ImageView imgResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        globalVariable = (GlobalClass) getApplicationContext();

        TextView txtOperateur = (TextView) findViewById(R.id.txtViewOperateur);
        txtVal1 = (TextView) findViewById(R.id.txtViewVal1);
        txtVal2 = (TextView) findViewById(R.id.txtViewVal2);
        txtViewReponse = (TextView) findViewById(R.id.txtViewReponse);
        EditTxtResult = (EditText) findViewById(R.id.editTxtResult);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnValider = (Button) findViewById(R.id.btnValider);
        imgResult = (ImageView) findViewById(R.id.imgResult);

        if (savedInstanceState != null) {
            // Récupère les données
            bonnesReponses = savedInstanceState.getInt(STATE_SCORE);
            level = savedInstanceState.getInt(STATE_LEVEL);
            tour = savedInstanceState.getInt(STATE_NBTOUR);
            if (level != 3){
                val1 = savedInstanceState.getInt(STATE_VAL1);
                val2 = savedInstanceState.getInt(STATE_VAL2);
                result = savedInstanceState.getInt(STATE_RESULT);
            }else {
                i = savedInstanceState.getInt(STATE_MULTIPLICATION);
                i--; // Ré-équilibre le i++ de la fonction Calcul()
            }
        }else {
            level = getIntent().getIntExtra(NiveauActivity.NIVEAU_NUMBER, 1);
        }

        EditTxtResult.setHint(" ? "); // = Placeholder

        switch(level) {
            case 1:
                operateur = "Addition";
                txtOperateur.setText(" + ");
                break;
            case 2:
                operateur = "Soustraction";
                txtOperateur.setText(" - ");
                break;
            case 3:
                multiplicateur = getIntent().getIntExtra(MultiplicationActivity.MULTIPLICATEUR_MULTIPLICATEUR, 1);
                operateur = "Multiplication";
                txtOperateur.setText(" x ");
                break;
            case 4:
                operateur = "Division";
                txtOperateur.setText(" / ");
                break;
        }
        btnNext.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null || level == 3) {
            Calcul();
        }else{
            // Affecte les valeurs aux champs
            txtVal1.setText("" + val1);
            txtVal2.setText("" + val2);
        }
    }

    /**
     * Vérifie le calcul
     * Si c'est la Xeme reponses => Affiche la bin
     * @param vue
     */
    public void onClickValider(View vue) {

        tour++;// Incrémente le nb de tour

        // Si le champ est vide ou différent du résultat attentendu => erreur +1
        if(TextUtils.isEmpty(EditTxtResult.getText()) || Integer.valueOf(EditTxtResult.getText().toString()) != result){
            txtViewReponse.setText(""+result);
            Drawable myDrawable = getResources().getDrawable(R.drawable.ko);
            imgResult.setImageDrawable(myDrawable);
            // Sinon, bonne réponse +1
        }else {
            Drawable myDrawable = getResources().getDrawable(R.drawable.ok);
            imgResult.setImageDrawable(myDrawable);
            bonnesReponses++;
        }

        // Si c'est la 10eme réponse, l'activité est fini
        if (tour >= nbTourDeJeu){
            if(globalVariable.getId() != 0){ // Si user est co
                //Enregistre le score
                score();
            }
            // Création d'un intention
            Intent intent = new Intent(this, ResultatActivity.class);
            // Ajout de la chaine de nom à l'intent
            intent.putExtra(MATH_MEILLEUR_SCORE, meilleurScore);
            intent.putExtra(MATH_SCORE, bonnesReponses);
            intent.putExtra("caller", operateur);
            // lancement de la demande de changement d'activité
            startActivity(intent);
        }

        // Masque le bouton valider, affiche le bouton suivant
        btnValider.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.VISIBLE);

    }

    /**
     * Affiche le prochain calcul
     * @param vue
     */
    public void onClickSuivant(View vue) {
        Calcul();
        EditTxtResult.setText("");
        txtViewReponse.setText("");
        imgResult.setImageDrawable(null);
        btnValider.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
    }

    /**
     * Enregistre le score
     * libelleMatiere, score
     */
    public void score(){
        MatiereDAO matiereDAO = new MatiereDAO(this);
        long idMatiere = matiereDAO.getMatiereByLibelle(operateur).getId();
        long idUser = globalVariable.getId();
        ScoreDAO scoreDAO = new ScoreDAO(this);
        Score scoreBD = scoreDAO.getScoreByMatiereAndUser(idMatiere, idUser);
        meilleurScore = bonnesReponses;

        if (scoreBD != null) { // Si un score existe
            if(scoreBD.getScore() > bonnesReponses){ // Si le score en BD est supérieur a l'actuel
                meilleurScore = scoreBD.getScore();
            }else { // Sinon Maj de la BD
                scoreBD.setScore(bonnesReponses);
                scoreDAO.update(scoreBD);
            }
        }else{
            Score score = new Score();
            score.setidUser(idUser);
            score.setIdMatiere(idMatiere);
            score.setScore(bonnesReponses);
            scoreDAO.insert(score);
        }
    }

    /**
     * Créer un calcul en fonction du niveau choisi
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
                val1 = randNb(1,100);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save les données de jeu
        savedInstanceState.putInt(STATE_SCORE, bonnesReponses);
        savedInstanceState.putInt(STATE_LEVEL, level);
        savedInstanceState.putInt(STATE_NBTOUR, tour);
        if (operateur.equals("Multiplication")){
            savedInstanceState.putInt(STATE_MULTIPLICATION, i);
        }else {
            savedInstanceState.putInt(STATE_VAL1, val1);
            savedInstanceState.putInt(STATE_VAL2, val2);
            savedInstanceState.putInt(STATE_RESULT, result);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_math, menu);
        return true;
    }
}
