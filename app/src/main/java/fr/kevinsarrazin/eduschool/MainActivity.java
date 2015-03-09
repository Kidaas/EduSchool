package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    // ID REQUETES
    public final static int MULTIPLICATION_ACTIVITY_REQUEST = 1;
    public final static int ADDITION_ACTIVITY_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onMultiplicationClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, MultiplicationActivity.class);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, MULTIPLICATION_ACTIVITY_REQUEST);
    }

    public void onAdditionClick(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, AdditionActivity.class);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, ADDITION_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du retour à l'aide du code requête
        if (requestCode == MULTIPLICATION_ACTIVITY_REQUEST) {
            // Afficher une notification
            String notification =  "Retour";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();

        }
    }
}
