package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import fr.kevinsarrazin.eduschool.activity.MultiplicationActivity;
import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;


public class MainActivity extends Activity {

    // ID REQUETES
    public final static int MULTIPLICATION_ACTIVITY_REQUEST = 1;
    public final static int ADDITION_ACTIVITY_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserDAO uDAO = new UserDAO(this);
        // Ajout d'un user (test)
        User user = new User("login", "password");
        uDAO.ajouter(user);

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

        if (password == user.getPassword()){
            // Masque le LinearLayout de cconnexion
            linearConnexion.setVisibility(LinearLayout.INVISIBLE);
            // Récupère le LinearLayout de deconnexion
            LinearLayout linearDeconnexion = (LinearLayout) findViewById(R.id.LinearDeconnexion);
            // Créer un bouton de deconnexion
            Button btnDeconnexion = new Button(this);
            // Ajoute le btn de deconnexion au linearLayout de deconnexion
            linearDeconnexion.addView(btnDeconnexion);
        }else {
            TextView txtViewError = new TextView(this);
            txtViewError.setText("Erreur de Mdp");
            txtViewError.setTextColor(Color.rgb(128, 0,0));
            linearConnexion.addView(txtViewError);
        }


    }

    public void onMultiplicationClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, MultiplicationActivity.class);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivityForResult(intent, MULTIPLICATION_ACTIVITY_REQUEST);
    }

    public void onAdditionClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, AdditionActivity.class);
        // Lancement de la demande de changement d'activité + demande de retour
        startActivityForResult(intent, ADDITION_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Vérification du retour à l'aide du code requête
        if (requestCode == MULTIPLICATION_ACTIVITY_REQUEST || requestCode == ADDITION_ACTIVITY_REQUEST) {
            // Afficher une notification
            String notification =  "Retour";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();

        }
    }
}
