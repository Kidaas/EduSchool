package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.ResultatActivity;
import fr.kevinsarrazin.eduschool.data.Cultureg;
import fr.kevinsarrazin.eduschool.data.CulturegDAO;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.data.Score;
import fr.kevinsarrazin.eduschool.data.ScoreDAO;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file CulturegActivity.java
 *
 * Class gérant l'activité culture G
 * Génère les questions, les valides et enregistre le score
 */
public class CulturegActivity extends Activity {

    public static final String CULTUREG_SCORE = "CulturegScore";
    public static final String CULTUREG_MEILLEUR_SCORE = "CulturegMeilleurScore";
    static final String STATE_SCORE = "score";
    static final String STATE_LEVEL = "level";
    static final String STATE_NBTOUR = "nbTour";

    private int level;
    public static String tag = "Geographie";
    private int tour = 0, bonnesReponses = 0, meilleurScore, nbTourDeJeu = 10;
    private String result;
    private  GlobalClass globalVariable;

    private TextView txtViewQuestion, txtViewReponse, nbPartie;
    private EditText EditTxtResult;
    private ImageView imgResult;
    private Button btnNext, btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureg);

        globalVariable = (GlobalClass) getApplicationContext();

        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewReponse = (TextView) findViewById(R.id.txtViewReponse);
        nbPartie = (TextView) findViewById(R.id.nbPartie);
        EditTxtResult = (EditText) findViewById(R.id.editTxtResult);
        imgResult = (ImageView) findViewById(R.id.imgResult);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnValider = (Button) findViewById(R.id.btnValider);

        if (savedInstanceState != null) {
            // Récupère les données
            bonnesReponses = savedInstanceState.getInt(STATE_SCORE);
            level = savedInstanceState.getInt(STATE_LEVEL);
            tour = savedInstanceState.getInt(STATE_NBTOUR);
        }else {
            // Récupère le niveau choisis
            level = getIntent().getIntExtra(NiveauActivity.NIVEAU_NUMBER, 1);
        }

        nbPartie.setText(tour + "/10");

        // Et affecte le tag correspondant
        switch(level) {
            case 1:
                tag = "Geographie";
                break;
            case 2:
                tag = "Français";
                break;
        }

        Question();
    }

    /**
     * Renvoie la question sur la catégorie données
     */
    public void Question(){
        CulturegDAO cDAO = new CulturegDAO(this);
        Cultureg question = cDAO.getQuestionRandom(tag);
        result = question.getReponse();

        // Affecte la valeur à son champs
        txtViewQuestion.setText(question.getQuestion());
    }

    /**
     * Valide la question
     * Si c'est la Xeme question, termine l'activité
     * @param vue
     */
    public void onClickValider(View vue) {
        tour++; // Incremente le nb de tour
        // Si le champ est vide ou différent du résultat attentendu => erreur +1
        if(TextUtils.isEmpty(EditTxtResult.getText()) || !EditTxtResult.getText().toString().toLowerCase().equals(result.toLowerCase())){
            txtViewReponse.setText(result);
            Drawable myDrawable = getResources().getDrawable(R.drawable.ko);
            imgResult.setImageDrawable(myDrawable);
            // Sinon, bonne réponse +1
        }else {
            Drawable myDrawable = getResources().getDrawable(R.drawable.ok);
            imgResult.setImageDrawable(myDrawable);
            bonnesReponses++;
        }

        // Si c'est la Xeme réponse, l'activité est fini
        if (tour >= nbTourDeJeu){
            //Enregistre le score si l'utilisateur est enregistré
            if (globalVariable.getId() != 0){
                score();
            }

            // Création d'un intention
            Intent intent = new Intent(this, ResultatActivity.class);
            // Ajout de la chaine de nom à l'intent
            intent.putExtra(CULTUREG_MEILLEUR_SCORE, meilleurScore);
            intent.putExtra(CULTUREG_SCORE, bonnesReponses);
            intent.putExtra("caller", "Cultureg");
            // lancement de la demande de changement d'activité
            startActivity(intent);
        }

        // Masque le bouton valider, affiche le bouton suivant
        btnValider.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.VISIBLE);

        nbPartie.setText(tour + "/10");

    }

    /**
     * Affiche la prochaine question
     * @param vue
     */
    public void onClickSuivant(View vue) {
        Question();
        EditTxtResult.setText("");
        txtViewReponse.setText("");
        btnValider.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        imgResult.setImageDrawable(null);
    }

    /**
     * Enregistre le score
     */
    public void score(){

        MatiereDAO matiereDAO = new MatiereDAO(this);
        long idMatiere = matiereDAO.getMatiereByLibelle(tag).getId();
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save les données de jeu
        savedInstanceState.putInt(STATE_SCORE, bonnesReponses);
        savedInstanceState.putInt(STATE_LEVEL, level);
        savedInstanceState.putInt(STATE_NBTOUR, tour);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cultureg, menu);
        return true;
    }

}
