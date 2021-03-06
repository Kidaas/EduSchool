package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.data.Score;
import fr.kevinsarrazin.eduschool.data.ScoreDAO;
import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file ScoreActivity.java
 *
 * Class gérant l'affichage des scores de l'user
 * Récupère et affiche les scores de l'user courant
 */

public class ScoreActivity extends Activity {

    private LinearLayout layoutScore;
    private GlobalClass globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        layoutScore = (LinearLayout) findViewById(R.id.layoutScore);

        // Récupère l'objet globale
        globalVariable = (GlobalClass) getApplicationContext();
        long idUser = globalVariable.getId();

        if (idUser != 0){

            // Initialise les DAO
            UserDAO userDAO = new UserDAO(this);
            MatiereDAO matiereDAO = new MatiereDAO(this);
            ScoreDAO scoreDAO = new ScoreDAO(this);

            // Récupère l'user via son id
            User user = userDAO.getUserById(idUser);
            // Récupère les scores de l'utilisateurs
            List<Score> listeScores = scoreDAO.getAllScoreByUser(idUser);

            if (listeScores != null){
                for(Score score : listeScores){
                    LinearLayout layoutMatiere = new LinearLayout(this);
                    TextView txtViewMatiere = new TextView(this);
                    TextView txtViewScore = new TextView(this);

                    txtViewMatiere.setText(matiereDAO.getMatiere(score.getIdMatiere()).getLibelle() + " : ");
                    txtViewScore.setText("" + score.getScore());

                    layoutMatiere.addView(txtViewMatiere);
                    layoutMatiere.addView(txtViewScore);
                    layoutScore.addView(layoutMatiere);
                }
            }else {
                TextView txtViewMessage = new TextView(this);
                txtViewMessage.setText("Aucun score pour le moment !");
                layoutScore.addView(txtViewMessage);
            }

        }else {
            TextView txtViewMessage = new TextView(this);
            txtViewMessage.setText("Enregistrez/connectez vous pour profiter des avantages liés au compte");
            txtViewMessage.setTextColor(getResources().getColor(R.color.errorColor));
            layoutScore.addView(txtViewMessage);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }
}
