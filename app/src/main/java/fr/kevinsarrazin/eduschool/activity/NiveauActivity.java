package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.data.MatiereDAO;
import fr.kevinsarrazin.eduschool.data.ScoreDAO;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file NiveauActivity.java
 *
 * Class gérant le choix de niveau
 * Créer les boutons de niveau en fonction de la matière choisi
 * Envoie le niveau au activité de matière
 */
public class NiveauActivity extends Activity {

    public static final String NIVEAU_NUMBER = "level";
    private int level = 1;
    private String caller;
    private GlobalClass globalVariable;

    // Constante pur le caller
    private String MATH = "Math";
    private String CULTURE = "Culture";
    private String QCM = "Culture";

    private Button btn1, btn2, btn3, btn4;
    private ImageView ImageViewbtn1, ImageViewbtn2, ImageViewbtn3, ImageViewbtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau);

        globalVariable = (GlobalClass) getApplicationContext();


        Button[] tabButton = new Button[4];
        ImageView[] tabImage = new ImageView[4];

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        ImageViewbtn1 = (ImageView) findViewById(R.id.imageViewbtn1);
        ImageViewbtn2 = (ImageView) findViewById(R.id.imageViewbtn2);
        ImageViewbtn3 = (ImageView) findViewById(R.id.imageViewbtn3);
        ImageViewbtn4 = (ImageView) findViewById(R.id.imageViewbtn4);

        caller = getIntent().getStringExtra("caller");

        if (caller.equals(MATH)){
            btn1.setText(R.string.addition);
            btn2.setText(R.string.soustraction);
            btn3.setText(R.string.multiplication);
            btn4.setText(R.string.division);

        }else if(caller.equals(CULTURE)) {
            btn1.setText(R.string.geographie);
            btn2.setText(R.string.francais);

            btn3.setVisibility(View.INVISIBLE);
            ImageViewbtn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            ImageViewbtn4.setVisibility(View.INVISIBLE);
        }else {
            btn1.setText(R.string.departement);

            btn2.setVisibility(View.INVISIBLE);
            ImageViewbtn2.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            ImageViewbtn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            ImageViewbtn4.setVisibility(View.INVISIBLE);
        }

        tabButton[0] = btn1;
        tabButton[1] = btn2;
        tabButton[2] = btn3;
        tabButton[3] = btn4;

        tabImage[0] = ImageViewbtn1;
        tabImage[1] = ImageViewbtn2;
        tabImage[2] = ImageViewbtn3;
        tabImage[3] = ImageViewbtn4;

        niveauDebloque(tabButton, tabImage);
    }

    private void niveauDebloque(Button[] tabButton, ImageView[] tabImage){

        MatiereDAO matiereDAO = new MatiereDAO(this);
        ScoreDAO scoreDAO = new ScoreDAO(this);
        long idUser = globalVariable.getId();

        if (idUser != 0){ // Si utilisateur est connecté
            for(int i = 0; i < tabButton.length; i++){ // Parcour tous les boutons
                long idMatiere = matiereDAO.getMatiereByLibelle(tabButton[i].getText().toString()).getId();
                if (scoreDAO.getScoreByMatiereAndUser(idMatiere, idUser) != null) { // Si le score de cette matière existe
                    if (scoreDAO.getScoreByMatiereAndUser(idMatiere, idUser).getScore() >= 5) { // Si ce score est supérieur a 5, valide le bouton
                        Drawable myDrawable = getResources().getDrawable(R.drawable.ok);
                        tabImage[i].setImageDrawable(myDrawable);
                    }else{ // Sinon invalide le bouton et desactive ses suivants
                        Drawable myDrawable = getResources().getDrawable(R.drawable.en_cour);
                        tabImage[i].setImageDrawable(myDrawable);
                        tabImage[i+1].setImageDrawable(myDrawable);
                        tabButton[i+1].setEnabled(false);
                    }
                }else{ // Sinon desactive le bouton si il est différent du 1er boutons
                    Drawable myDrawable = getResources().getDrawable(R.drawable.ko);
                    tabImage[i].setImageDrawable(myDrawable);
                    if (tabButton[i] != btn1){
                        tabButton[i].setEnabled(false);
                    }else{
                        Drawable myDrawable2 = getResources().getDrawable(R.drawable.en_cour);
                        tabImage[i].setImageDrawable(myDrawable2);
                    }
                }
            }
        }

    }
    /**
     * Ecoute sur les boutons
     * @param v
     */
    //@Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (caller.equals(MATH)){
            switch (v.getId()) {
                case (R.id.btn1):
                    intent = new Intent(this, MathActivity.class);
                    level = 1;
                    break;
                case (R.id.btn2):
                    intent = new Intent(this, MathActivity.class);
                    level = 2;
                    break;
                case (R.id.btn3):
                    intent = new Intent(this, MultiplicationActivity.class);
                    level = 3;
                    break;
                case (R.id.btn4):
                    intent = new Intent(this, MathActivity.class);
                    level = 4;
                    break;
            }
        }else if(caller.equals(CULTURE)) {
            switch (v.getId()) {
                case (R.id.btn1):
                    intent = new Intent(this, CulturegActivity.class);
                    level = 1;
                    break;
                case (R.id.btn2):
                    intent = new Intent(this, CulturegActivity.class);
                    level = 2;
                    break;
            }
        }else{
            switch (v.getId()) {
                case (R.id.btn1):
                    intent = new Intent(this, QCMActivity.class);
                    level = 1;
                    break;
            }
        }

        // Ajout du niveau d'exercice
        intent.putExtra(NIVEAU_NUMBER, level);
        // lancement de la demande de changement d'activité
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_niveau, menu);
        return true;
    }

}
