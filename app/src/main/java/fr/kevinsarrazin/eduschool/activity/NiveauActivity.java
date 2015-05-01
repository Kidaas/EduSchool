package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;

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
    //private ImageView ImageViewbtn1, ImageViewbtn2, ImageViewbtn3, ImageViewbtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau);

        globalVariable = (GlobalClass) getApplicationContext();

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
/*        ImageViewbtn1 = (ImageView) findViewById(R.id.imageViewbtn1);
        ImageViewbtn2 = (ImageView) findViewById(R.id.imageViewbtn2);
        ImageViewbtn3 = (ImageView) findViewById(R.id.imageViewbtn3);
        ImageViewbtn4 = (ImageView) findViewById(R.id.imageViewbtn4);*/

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
            btn4.setVisibility(View.INVISIBLE);
        }else {
            btn1.setText(R.string.departement);
            btn2.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
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
