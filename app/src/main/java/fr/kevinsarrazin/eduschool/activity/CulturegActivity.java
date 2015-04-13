package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.ResultatActivity;
import fr.kevinsarrazin.eduschool.data.Cultureg;
import fr.kevinsarrazin.eduschool.data.CulturegDAO;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.data.Score;
import fr.kevinsarrazin.eduschool.data.ScoreDAO;

public class CulturegActivity extends Activity {

    public static final String CULTUREG_SCORE = "CulturegScore";
    public static final String CULTUREG_MEILLEUR_SCORE = "CulturegMeilleurScore";

    private int level;
    public static String tag = "Geographie";
    private int fin = 1, bonnesReponses = 0, meilleurScore, nbTourDeJeu = 10;
    private String result;

    private TextView txtViewQuestion, txtViewReponse;
    private EditText EditTxtResult;
    private ImageView imgResult;
    private Button btnNext, btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureg);

        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewReponse = (TextView) findViewById(R.id.txtViewReponse);
        EditTxtResult = (EditText) findViewById(R.id.editTxtResult);
        imgResult = (ImageView) findViewById(R.id.imgResult);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnValider = (Button) findViewById(R.id.btnValider);

        EditTxtResult.setHint(" ? "); // = Placeholder

        // Récupère le niveau choisis
        level = getIntent().getIntExtra(NiveauActivity.NIVEAU_NUMBER, 1);
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
     * Renvoie l'activité de question sur la catégorie données
     */
    public void Question(){

        CulturegDAO cDAO = new CulturegDAO(this);
        Cultureg question = cDAO.getQuestionRandom(tag);
        result = question.getReponse();

        // Affecte la valeur à son champs
        txtViewQuestion.setText(question.getQuestion());

    }

    public void onClickValider(View vue) {
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
        if (fin >= nbTourDeJeu){
            //Enregistre le score
            score();
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

        fin++;

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
        GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        MatiereDAO matiereDAO = new MatiereDAO(this);
        long idMatiere = matiereDAO.getMatiereByLibelle(tag).getId();
        long idUser = globalVariable.getId();
        ScoreDAO scoreDAO = new ScoreDAO(this);

        if (scoreDAO.getScoreByMatiereAndUser(idMatiere, idUser) != null) { // Si un score existe
            if(scoreDAO.getScoreByMatiereAndUser(idMatiere, idUser).getScore() > bonnesReponses){ // Si le score en BD est supérieur a l'actuel
                meilleurScore = scoreDAO.getScoreByMatiereAndUser(idMatiere, idUser).getScore();
            }else { // Sinon Maj de la BD
                meilleurScore = bonnesReponses;

                Score score = new Score();
                score.setidUser(idUser);
                score.setIdMatiere(idMatiere);
                score.setScore(bonnesReponses);
                scoreDAO.update(score);

            }
        }else{
            meilleurScore = bonnesReponses;

            Score score = new Score();
            score.setidUser(idUser);
            score.setIdMatiere(idMatiere);
            score.setScore(bonnesReponses);
            scoreDAO.insert(score);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cultureg, menu);
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
