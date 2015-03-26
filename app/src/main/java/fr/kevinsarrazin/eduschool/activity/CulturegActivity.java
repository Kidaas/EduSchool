package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.kevinsarrazin.eduschool.ErreurActivity;
import fr.kevinsarrazin.eduschool.FelicitationActivity;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.data.Cultureg;
import fr.kevinsarrazin.eduschool.data.CulturegDAO;

public class CulturegActivity extends Activity {

    public static final String GEO_NBERREUR = "GeoErreurs";
    public static String tag = "geographie";
    public static final int nbQuestion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultureg);
        Question();
        tag = getIntent().getStringExtra("caller");
    }

    /**
     * Renvoie l'activité de question sur la catégorie données
     */
    public void Question(){

        CulturegDAO cDAO = new CulturegDAO(this);
        //List<Cultureg> listQuestion = cDAO.getAllCulturegByTag(tag);

        LinearLayout firstlayout = (LinearLayout) findViewById(R.id.layoutquestion);

        for (int i = 1; i<nbQuestion; i++){
            LinearLayout lLayout = new LinearLayout(this);
            TextView txtView = new TextView(this);
            EditText editTextReponse = new EditText(this);

            Cultureg c = cDAO.getQuestion(i);

            txtView.setText(c.getQuestion());
            editTextReponse.setHint(" ? "); // = Placeholder
            editTextReponse.setInputType(InputType.TYPE_CLASS_TEXT);
            lLayout.addView(txtView);
            lLayout.addView(editTextReponse);
            firstlayout.addView(lLayout);
        }

    }

    public void validerResultat(View vue) {
        CulturegDAO cDAO = new CulturegDAO(this);
        //List<Cultureg> listQuestion = cDAO.getAllCulturegByTag(tag);

        LinearLayout lLayoutNumber = (LinearLayout) findViewById(R.id.layoutquestion);
        int erreur = 0;

        for (int i = 0; i < lLayoutNumber.getChildCount(); i++){
            // Récupère le layout i du layout principal
            LinearLayout l = (LinearLayout)lLayoutNumber.getChildAt(i);
            // Récupère le EditText du layout l, à la positon 1
            EditText e = (EditText)l.getChildAt(1);

            if (TextUtils.isEmpty(e.getText())){
                erreur++;
            }else {
                String result = e.getText().toString().toLowerCase();
                int j = i+1;
                String q = cDAO.getQuestion(j).getReponse().toLowerCase();
                if (q.equals(result)){
                    Log.d("result", "ok");
                }else {
                    Log.d("result","ko");
                    erreur++;
                }
            }
        }

        if (erreur == 0){
            // Création d'un intention
            Intent intent = new Intent(this, FelicitationActivity.class);
            // lancement de la demande de changement d'activité
            startActivity(intent);
            //startActivityForResult(intent, MULTIPLICATION_FELICITATION_REQUEST);
        }else {
            // Création d'un intention
            Intent intent = new Intent(this, ErreurActivity.class);
            // Ajout de la chaine de nom à l'intent
            intent.putExtra(GEO_NBERREUR, erreur);
            intent.putExtra("caller", "CulturegGeoActivity");
            // lancement de la demande de changement d'activité
            startActivity(intent);
            //startActivityForResult(intent, MULTIPLICATION_ERREUR_REQUEST);
        }
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
