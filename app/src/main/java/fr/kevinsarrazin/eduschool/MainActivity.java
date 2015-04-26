package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import fr.kevinsarrazin.eduschool.activity.NiveauActivity;
import fr.kevinsarrazin.eduschool.activity.ScoreActivity;
import fr.kevinsarrazin.eduschool.activity.UserActivity;
import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;


public class MainActivity extends Activity {

    // ID REQUETES
    public final static int MATH_ACTIVITY_REQUEST = 1;
    public final static int CULTUREG_ACTIVITY_REQUEST = 2;

    // Caller
    public static final String CALLER = "caller";

    // Constante pur le caller
    private String MATH = "Math";
    private String CULTURE = "Culture";

    // Variable de stockage local
    SharedPreferences sharedpreferences;
    public static final String MesPreferences = "Preference";
    public static final String nom = "login";
    public static final String mdp = "mdp";

    //Global class
    private GlobalClass globalVariable;

    private LinearLayout layoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutBtn = (LinearLayout) findViewById(R.id.LinearBtn);

        sharedpreferences = getSharedPreferences(MesPreferences, Context.MODE_PRIVATE);
        // Test si le login & mdp sont stocké en local, si c'est le cas, on n'affichera pas de champ login/inscription
        // Et on le connectera automatiquement
        if (sharedpreferences.contains(nom) && sharedpreferences.contains(mdp)) {
            UserDAO userDAO = new UserDAO(this);
            // Récupère l'user via son login stocké en local
            User u = userDAO.getUserByLogin(sharedpreferences.getString(nom, "login"));
            // Créer une instance de la classe variable globale
            globalVariable = (GlobalClass) getApplicationContext();
            // Ajoute l'id de l'utilisateur à la variable globale
            globalVariable.setId(u.getId());
            // Créer le bouton de deconnexion
            createBtnDeconnexion();

        }else { // Sinon affiche login/inscription (et place les placeholder)
            createBtnLoginInscription();
        }
    }

    /**
     * Fonction d'inscription
     *
     * @param view
     */
    public void onInscriptionClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
    }

    /**
     * Fonction de Connexion
     *
     * @param view
     */
    public void onConnexionClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, LoginActivity.class);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivity(intent);
    }

    /**
     * Fonction de Deconnexion
     */
    public void onDeconnexionClick(View v) {
        // Récupère les préferences et les effaces
        sharedpreferences = getSharedPreferences(MesPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        // Met l'id à zéro (signifie qu'il n'y a pas d'user enregistré)
        globalVariable.setId(0);

        // Recréer les btn de connexions
        createBtnLoginInscription();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Test le bouton de retour provenant des activités
        if (requestCode == MATH_ACTIVITY_REQUEST || requestCode == CULTUREG_ACTIVITY_REQUEST) {
            // Afficher une notification
            String notification = "Retour au menu";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Lance l'activité Math
     * @param view
     */
    public void onMathClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, NiveauActivity.class);
        intent.putExtra(CALLER, MATH);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivityForResult(intent, MATH_ACTIVITY_REQUEST);
    }

    /**
     * Lance les questions de cultures générales
     * @param view
     */
    public void onCulturegClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, NiveauActivity.class);
        intent.putExtra(CALLER, CULTURE);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivityForResult(intent, CULTUREG_ACTIVITY_REQUEST);
    }

    /**
     * Fonction de Création des boutons login/inscription
     *
     */
    public void createBtnLoginInscription() {
        layoutBtn.removeAllViews();
        // Créer un bouton de connexion
        Button btnLogin = new Button(this);
        btnLogin.setText("Login");
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onConnexionClick(v);
            }
        });
        // Créer un bouton d'inscription
        Button btnInscription = new Button(this);
        btnInscription.setText("Inscription");
        btnInscription.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onInscriptionClick(v);
            }
        });

        // Ajoute un margin right au 1er bouton (sinon collé)
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 25, 0);
        btnLogin.setLayoutParams(params);

        btnLogin.setMinWidth(250);
        btnInscription.setMinWidth(250);

        layoutBtn.addView(btnLogin);
        layoutBtn.addView(btnInscription);
    }

    /**
     * Fonction de Création du btn deconnexion
     *
     */
    public void createBtnDeconnexion() {
        layoutBtn.removeAllViews();
        // Créer un bouton de deconnexion
        Button btnDeconnexion = new Button(this);
        btnDeconnexion.setText("Deconnexion " + sharedpreferences.getString(nom, "login"));
        btnDeconnexion.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onDeconnexionClick(v);
            }
        });
        btnDeconnexion.setMinWidth(500);
        // Ajoute le btn de deconnexion au linearLayout de deconnexion
        layoutBtn.addView(btnDeconnexion);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.compte) {
            // Création d'une intention
            Intent intent = new Intent(this, UserActivity.class);
            // Lancement de la demande de changement d'activité + demande de retour
            startActivity(intent);
            return true;
        }else if(id == R.id.score){
            // Création d'une intention
            Intent intent = new Intent(this, ScoreActivity.class);
            // Lancement de la demande de changement d'activité + demande de retour
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
