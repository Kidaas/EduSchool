package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import fr.kevinsarrazin.eduschool.GlobalClass;
import fr.kevinsarrazin.eduschool.R;
import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;

/**
 * @author ksarrazin <kevin.sarrazin@live.fr>
 * @file UserActivity.java
 *
 * Class gérant l'affichage des données de l'utilisateur
 * Récupère le nom et l'avatar de l'utilisateur
 */

public class UserActivity extends Activity {

    private UserDAO userDAO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView txtViewName = (TextView) findViewById(R.id.txtViewName);
        ImageView imgAvatar = (ImageView) findViewById(R.id.imgAvatar);

        // Récupère l'objet globale
        GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        long idUser = globalVariable.getId();

        userDAO = new UserDAO(this);
        user = userDAO.getUserById(idUser);

        imgAvatar.setImageBitmap(BitmapFactory.decodeFile(user.getPathImage()));
        txtViewName.setText(user.getLogin() + ": " + idUser);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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
