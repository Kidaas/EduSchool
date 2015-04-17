package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file LoginActivity.java
 *
 * Class gérant l'activité Login
 * Récupère le login/mdp, les test,
 * si ok : enregistrement de l'id user dans la GlobalClass, stock le login en sharedPreference & retour à l'activité principale
 * Si ko : Affichage d'un message d'erreur
 */
public class LoginActivity extends Activity {

    // Variable de stockage local
    SharedPreferences sharedpreferences;
    public static final String MesPreferences = "Preference";
    public static final String nom = "login";
    public static final String mdp = "mdp";

    private String login, password;
    private EditText EditTxtLogin, EditTxtPassword;
    //Global class
    private GlobalClass globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditTxtLogin = (EditText) findViewById(R.id.editTxtLogin);
        EditTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
    }

    /**
     * Fonction de Connexion
     *
     * @param view
     */
    public void onConnexionClick(View view) {
        sharedpreferences = getSharedPreferences(MesPreferences, Context.MODE_PRIVATE);

        login = EditTxtLogin.getText().toString();
        password = EditTxtPassword.getText().toString();

        UserDAO uDAO = new UserDAO(this);

        // Si l'user est en base, le récupère
        if (uDAO.getUserByLogin(login) != null) {

            User user = uDAO.getUserByLogin(login);

            // Test si le mot de passe correspond à celui en base
            if (password.equals(user.getPassword())) {
                // Sauvegarde le login et le mot de passe en local
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(nom, user.getLogin());
                editor.putString(mdp, user.getPassword());
                editor.commit();

                // Créer une instance de la classe variable globale
                globalVariable = (GlobalClass) getApplicationContext();
                // Ajoute l'id de l'utilisateur à la variable globale
                globalVariable.setId(user.getId());

                // Affiche une notification
                String notification = "Vous êtes connecté(e)";
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();

                // Création d'une intention
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                // Sinon averti l'utilisateur
            } else {
                // Affiche une notification
                String notification = "Mauvais mot de passe";
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
            }
            // Sinon averti l'utilisateur
        } else {
            // Affiche une notification
            String notification = "Mauvais login";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
}
