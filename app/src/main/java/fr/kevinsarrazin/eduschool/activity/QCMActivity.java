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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.ResultatActivity;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.data.Qcm;
import fr.kevinsarrazin.eduschool.data.QcmDAO;
import fr.kevinsarrazin.eduschool.data.Score;
import fr.kevinsarrazin.eduschool.data.ScoreDAO;


public class QCMActivity extends Activity {

    public static final String QCM_SCORE = "CulturegScore";
    public static final String QCM_MEILLEUR_SCORE = "CulturegMeilleurScore";
    static final String STATE_SCORE = "score";
    static final String STATE_LEVEL = "level";
    static final String STATE_NBTOUR = "nbTour";

    private int level;
    public static String tag = "Departement";
    private int tour = 0, bonnesReponses = 0, meilleurScore, nbTourDeJeu = 10;
    private String result;
    private GlobalClass globalVariable;

    private TextView txtViewQuestion, txtViewReponse;
    private ImageView imgResult;
    private Button btnNext;
    private RadioGroup rGrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);

        globalVariable = (GlobalClass) getApplicationContext();

        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewReponse = (TextView) findViewById(R.id.txtViewReponse);
        imgResult = (ImageView) findViewById(R.id.imgResult);
        rGrp = (RadioGroup) findViewById(R.id.RadioGrp);
        btnNext = (Button) findViewById(R.id.btnNext);

        if (savedInstanceState != null) {
            // Récupère les données
            bonnesReponses = savedInstanceState.getInt(STATE_SCORE);
            level = savedInstanceState.getInt(STATE_LEVEL);
            tour = savedInstanceState.getInt(STATE_NBTOUR);
        }else {
            // Récupère le niveau choisis
            level = getIntent().getIntExtra(NiveauActivity.NIVEAU_NUMBER, 1);
        }

        // Et affecte le tag correspondant
        switch(level) {
            case 1:
                tag = "Departement";
                break;
        }

        Question();

    }

    /**
     * Renvoie la question sur la catégorie données
     */
    public void Question(){
        QcmDAO qDAO = new QcmDAO(this);
        Qcm question = qDAO.getQcmRandom(tag);
        result = question.getReponse();

        // Affecte la valeur à son champs
        txtViewQuestion.setText(question.getQuestion());

        rGrp.removeAllViews();
        RadioButton[] tabRadioButton = new RadioButton[4];

        tabRadioButton[0] = new RadioButton(this);
        tabRadioButton[1] = new RadioButton(this);
        tabRadioButton[2] = new RadioButton(this);
        tabRadioButton[0].setText(question.getReponse());
        tabRadioButton[1].setText(question.getMauvaiseReponse());
        tabRadioButton[2].setText(question.getMauvaiseReponse1());

        for(int i : randNb(0, 2, 3)){
            rGrp.addView(tabRadioButton[i]);
        }

        rGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tour++;

                RadioButton rb = (RadioButton)findViewById(checkedId);

                // Si le champ est egal au résultat attendu
                if(rb.getText().equals(result)){
                    Drawable myDrawable = getResources().getDrawable(R.drawable.ok);
                    imgResult.setImageDrawable(myDrawable);
                    bonnesReponses++;

                    for (int i = 0; i < rGrp.getChildCount(); i++) {
                        rGrp.getChildAt(i).setEnabled(false);
                    }

                }else {
                    txtViewReponse.setText(result);
                    Drawable myDrawable = getResources().getDrawable(R.drawable.ko);
                    imgResult.setImageDrawable(myDrawable);

                    for (int i = 0; i < rGrp.getChildCount(); i++) {
                        rGrp.getChildAt(i).setEnabled(false);
                    }

                }

                // Si c'est la Xeme réponse, l'activité est fini
                if (tour >= nbTourDeJeu){
                    //Enregistre le score si l'utilisateur est enregistré
                    if (globalVariable.getId() != 0){
                        score();
                    }

                    onItent();
                }

                // affiche le bouton suivant
                btnNext.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * Affiche la prochaine question
     * @param vue
     */
    public void onClickSuivant(View vue) {
        Question();
        txtViewReponse.setText("");
        rGrp.setEnabled(true);
        btnNext.setVisibility(View.INVISIBLE);
        imgResult.setImageDrawable(null);
    }

    /**
     */
    public void onItent() {
        // Création d'un intention
        Intent intent = new Intent(this, ResultatActivity.class);
        // Ajout de la chaine de nom à l'intent
        intent.putExtra(QCM_MEILLEUR_SCORE, meilleurScore);
        intent.putExtra(QCM_SCORE, bonnesReponses);
        intent.putExtra("caller", "Cultureg");
        // lancement de la demande de changement d'activité
        startActivity(intent);
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

    public ArrayList<Integer> randNb(int min, int max, int nb) {

        ArrayList<Integer> listChiffre = new ArrayList<Integer>();
        ArrayList<Integer> listQuestion = new ArrayList<Integer>();
        for (int i= min; i <= max; i++) {
            listChiffre.add(new Integer(i));
        }
        Collections.shuffle(listChiffre);
        for (int i=0; i < nb; i++) {
            listQuestion.add(listChiffre.get(i));
        }

        return listQuestion;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qcm, menu);
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
