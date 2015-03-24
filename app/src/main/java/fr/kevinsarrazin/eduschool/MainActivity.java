package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import fr.kevinsarrazin.eduschool.activity.AdditionActivity;
import fr.kevinsarrazin.eduschool.activity.CulturegGeoActivity;
import fr.kevinsarrazin.eduschool.activity.MultiplicationActivity;
import fr.kevinsarrazin.eduschool.data.Cultureg;
import fr.kevinsarrazin.eduschool.data.CulturegDAO;
import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;


public class MainActivity extends Activity {

    // ID REQUETES
    public final static int MULTIPLICATION_ACTIVITY_REQUEST = 1;
    public final static int ADDITION_ACTIVITY_REQUEST = 2;
    public final static int CULTUREG_GEO_ACTIVITY_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /****************************************
         * Ajout base Culture G
         ***************************************/
/*      CulturegDAO cDAO = new CulturegDAO(this);
        Cultureg c1 = new Cultureg("Geographie", "Capitale de la France ? ", "Paris");
        Cultureg c2 = new Cultureg("Geographie", "Capitale de l'Espagne ? ", "Madrid");
        Cultureg c3 = new Cultureg("Geographie", "Capitale de la Belgique ? ", "Bruxelles");
        Cultureg c4 = new Cultureg("Geographie", "Capitale des Etats Unis ? ", "Washington");
        Cultureg c5 = new Cultureg("Geographie", "Capitale de la Chine ? ", "Pekin");
        Cultureg c6 = new Cultureg("Geographie", "Capitale du Japon ? ", "Tokyo");

        cDAO.ajouter(c1);
        cDAO.ajouter(c2);
        cDAO.ajouter(c3);
        cDAO.ajouter(c4);
        cDAO.ajouter(c5);
        cDAO.ajouter(c6);*/
        /****************************************
         * Fin ajout base Culture G
         ***************************************/

        EditText EditTxtLogin = (EditText) findViewById(R.id.editTxtLogin);
        EditText EditTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
        EditTxtLogin.setHint("Login");
        EditTxtPassword.setHint("Password");

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onConnexionClick(View view) {
        // Récupère le LinearLayout de connexion
        LinearLayout linearConnexion = (LinearLayout) findViewById(R.id.LinearLogin);
        EditText EditTxtLogin = (EditText) findViewById(R.id.editTxtLogin);
        EditText EditTxtPassword = (EditText) findViewById(R.id.editTxtPassword);

        String login = EditTxtLogin.getText().toString();
        String password = EditTxtPassword.getText().toString();

        User user = new User();
        UserDAO uDAO = new UserDAO(this);
        if (uDAO.getUserByLogin(login) != null){
            user = uDAO.getUserByLogin(login);
        } else {
            TextView txtViewError = new TextView(this);
            txtViewError.setText("Erreur de Login");
            txtViewError.setTextColor(Color.rgb(128, 0,0));
            linearConnexion.addView(txtViewError);
        }

        if (password.equals(user.getPassword())){
            // Masque le LinearLayout de cconnexion
            linearConnexion.setVisibility(LinearLayout.INVISIBLE);
            // Récupère le LinearLayout de deconnexion
            LinearLayout linearDeconnexion = (LinearLayout) findViewById(R.id.LinearDeconnexion);
            // Créer un bouton de deconnexion
            Button btnDeconnexion = new Button(this);
            btnDeconnexion.setText("Deconnexion");
            // Ajoute le btn de deconnexion au linearLayout de deconnexion
            linearDeconnexion.addView(btnDeconnexion);
        }else {
            TextView txtViewError = new TextView(this);
            txtViewError.setText("Erreur de Mdp");
            txtViewError.setTextColor(Color.rgb(128, 0,0));
            linearConnexion.addView(txtViewError);
        }

    }

    /**
     * Lance l'activité multiplication
     * @param view
     */
    public void onMultiplicationClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, MultiplicationActivity.class);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivityForResult(intent, MULTIPLICATION_ACTIVITY_REQUEST);
    }

    /**
     * Lance l'activité Addition
     * @param view
     */
    public void onAdditionClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, AdditionActivity.class);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivityForResult(intent, ADDITION_ACTIVITY_REQUEST);
    }

    /**
     * Lance l'activité cultureg -> Geographie
     * @param view
     */
    public void onGeographieClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, CulturegGeoActivity.class);
        // Lancement de la demande de changement d'activité + demande de retour
        intent.putExtra("caller", "Geographie");
        startActivityForResult(intent, CULTUREG_GEO_ACTIVITY_REQUEST);
    }

    /**
     * Lance l'activité cultureg -> Français
     * @param view
     */
    public void onFrançaisClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, CulturegGeoActivity.class);
        // Lancement de la demande de changement d'activité + demande de retour
        intent.putExtra("caller", "Français");
        startActivityForResult(intent, CULTUREG_GEO_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Vérification du retour à l'aide du code requête
        if (requestCode == MULTIPLICATION_ACTIVITY_REQUEST || requestCode == ADDITION_ACTIVITY_REQUEST || requestCode == CULTUREG_GEO_ACTIVITY_REQUEST) {
            // Afficher une notification
            String notification =  "Retour à l'activité principale";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();

        }
    }
}
