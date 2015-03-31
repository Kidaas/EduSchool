package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import fr.kevinsarrazin.eduschool.activity.NiveauActivity;
import fr.kevinsarrazin.eduschool.activity.UserActivity;
import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;


public class MainActivity extends Activity {

    // ID REQUETES
    public final static int MATH_ACTIVITY_REQUEST = 1;
    public final static int CULTUREG_ACTIVITY_REQUEST = 2;
    public final static int QCM_ACTIVITY_REQUEST = 2;

    // Caller
    public static final String CALLER = "caller";

    // Constante pur le caller
    private String MATH = "Math";
    private String CULTURE = "Culture";
    private String QCM = "QCM";

    // Variable de stockage local
    SharedPreferences sharedpreferences;
    public static final String MesPreferences = "Preference" ;
    public static final String nom = "login";
    public static final String mdp = "mdp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MesPreferences, Context.MODE_PRIVATE);
        // Test si le login & mdp sont stocké en local, si c'est le cas, on n'affichera pas de champ login/inscription
        // Et on le connectera automatiquement
        if (sharedpreferences.contains(nom) && sharedpreferences.contains(mdp))
        {
            UserDAO userDAO = new UserDAO(this);
            // Récupère l'user via son login stocké en local
            User u = userDAO.getUserByLogin(sharedpreferences.getString(nom, "login"));
            // Créer une instance de la classe variable globale
            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
            // Ajoute l'id de l'utilisateur à la variable globale
            globalVariable.setId(u.getId());

            LinearLayout linearConnexion = (LinearLayout) findViewById(R.id.lLayoutCoIns);
            // Masque le LinearLayout de connexion
            linearConnexion.setVisibility(LinearLayout.INVISIBLE);
            // Récupère le LinearLayout de deconnexion
            LinearLayout linearDeconnexion = (LinearLayout) findViewById(R.id.LinearDeconnexion);
            // Créer un bouton de deconnexion
            Button btnDeconnexion = new Button(this);
            btnDeconnexion.setText("Deconnexion " + sharedpreferences.getString(nom, "login"));
            btnDeconnexion.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    onDeconnexionClick(v);
                }
            });
            // Ajoute le btn de deconnexion au linearLayout de deconnexion
            linearDeconnexion.addView(btnDeconnexion);
        // Sinon affiche login/inscription (et place les placeholder)
        }else {
            EditText EditTxtLogin = (EditText) findViewById(R.id.editTxtLogin);
            EditText EditTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
            EditText EditTxtInscriptionLogin = (EditText) findViewById(R.id.editTxtInscriptionLogin);
            EditText EditTxtInscriptionPassword = (EditText) findViewById(R.id.editTxtInscriptionPassword);
            EditTxtLogin.setHint("Login");
            EditTxtPassword.setHint("Password");
            EditTxtInscriptionLogin.setHint("Login");
            EditTxtInscriptionPassword.setHint("Password");
        }

    }

    /**
     * Fonction d'inscription
     * @param view
     */
    public void onInscriptionClick(View view) {
        EditText EditTxtInscriptionLogin = (EditText) findViewById(R.id.editTxtInscriptionLogin);
        EditText EditTxtInscriptionPassword = (EditText) findViewById(R.id.editTxtInscriptionPassword);

        // Vérifie que les champs ne sont pas vident
        if (!TextUtils.isEmpty(EditTxtInscriptionLogin.getText()) && !TextUtils.isEmpty(EditTxtInscriptionPassword.getText())){
            UserDAO uDao = new UserDAO(this);
            User u = new User(EditTxtInscriptionLogin.getText().toString(), EditTxtInscriptionPassword.getText().toString());
            uDao.insert(u);
            EditTxtInscriptionLogin.getText().clear();
            EditTxtInscriptionPassword.getText().clear();

            // Affiche une notification
            String notification =  "Vous pouvez désormais vous authentifier";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();

        // Sinon ne fait rien et affiche un message a l'user
        }else {
            // Affiche une notification
            String notification =  "Veuillez remplir tout les champs";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * Fonction de Connexion
     * @param view
     */
    public void onConnexionClick(View view) {
        sharedpreferences = getSharedPreferences(MesPreferences, Context.MODE_PRIVATE);

        // Récupère le LinearLayout de connexion
        LinearLayout linearConnexion = (LinearLayout) findViewById(R.id.lLayoutCoIns);
        EditText EditTxtLogin = (EditText) findViewById(R.id.editTxtLogin);
        EditText EditTxtPassword = (EditText) findViewById(R.id.editTxtPassword);

        String login = EditTxtLogin.getText().toString();
        String password = EditTxtPassword.getText().toString();

        User user = new User();
        UserDAO uDAO = new UserDAO(this);
        // Si l'user est en base, le récupère
        if (uDAO.getUserByLogin(login) != null){

            user = uDAO.getUserByLogin(login);

            // Test si le mot de passe correspond à celui en base
            if (password.equals(user.getPassword())){
                // Sauvegarde le login et le mot de passe en local
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(nom, user.getLogin());
                editor.putString(mdp, user.getPassword());
                editor.commit();

                // Créer une instance de la classe variable globale
                final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                // Ajoute l'id de l'utilisateur à la variable globale
                globalVariable.setId(user.getId());

                // Masque le LinearLayout de connexion
                linearConnexion.setVisibility(LinearLayout.INVISIBLE);
                // Récupère le LinearLayout de deconnexion
                LinearLayout linearDeconnexion = (LinearLayout) findViewById(R.id.LinearDeconnexion);
                // Créer un bouton de deconnexion
                Button btnDeconnexion = new Button(this);
                btnDeconnexion.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        onDeconnexionClick(v);
                    }
                });
                btnDeconnexion.setText("Deconnexion de " + user.getLogin());
                // Ajoute le btn de deconnexion au linearLayout de deconnexion
                linearDeconnexion.addView(btnDeconnexion);

                // Affiche une notification
                String notification =  "Vous êtes connecté";
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
            // Sinon averti l'utilisateur
            }else {
                // Affiche une notification
                String notification =  "Mauvais mot de passe";
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
            }
        // Sinon averti l'utilisateur
        } else {
            // Affiche une notification
            String notification =  "Mauvais login";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        }

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

        LinearLayout linearConnexion = (LinearLayout) findViewById(R.id.lLayoutCoIns);
        // Masque le LinearLayout de connexion
        linearConnexion.setVisibility(LinearLayout.VISIBLE);
        // Récupère le LinearLayout de deconnexion
        LinearLayout linearDeconnexion = (LinearLayout) findViewById(R.id.LinearDeconnexion);
        linearDeconnexion.setVisibility(LinearLayout.INVISIBLE);
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
     * Lance les questions de cultures générales
     * @param view
     */
    public void onQcmClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, NiveauActivity.class);
        intent.putExtra(CALLER, QCM);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivityForResult(intent, QCM_ACTIVITY_REQUEST);
    }

    /**
     * Test le retour d'activité
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Vérification du retour à l'aide du code requête
        if (requestCode == MATH_ACTIVITY_REQUEST || requestCode == CULTUREG_ACTIVITY_REQUEST) {
            // Afficher une notification
            String notification =  "Retour à l'activité principale";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();

        }
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
        }

        return super.onOptionsItemSelected(item);
    }
}
