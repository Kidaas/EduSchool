package fr.kevinsarrazin.eduschool.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        TextView txtViewPseudo = (TextView) findViewById(R.id.txtViewPseudo);
        TextView txtViewNom = (TextView) findViewById(R.id.txtViewNom);
        TextView txtViewPrenom = (TextView) findViewById(R.id.txtViewPrenom);
        ImageView imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        LinearLayout layoutUser = (LinearLayout) findViewById(R.id.layoutUser);

        txtViewPseudo.setText("");
        txtViewNom.setText("");
        txtViewPrenom.setText("");

        // Récupère l'objet globale
        GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        long idUser = globalVariable.getId();

        if (idUser != 0){
            userDAO = new UserDAO(this);
            user = userDAO.getUserById(idUser);
            if (user.getPathImage() != null){
                imgAvatar.setImageBitmap(BitmapFactory.decodeFile(user.getPathImage()));
            }else {
                imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.default_icon));
            }

            txtViewPseudo.setText(user.getLogin());
            txtViewNom.setText(user.getNom());
            txtViewPrenom.setText(user.getPrenom());

        }else {
            TextView txtViewMessage = new TextView(this);
            txtViewMessage.setText("Enregistrez/connectez vous pour profiter des avantages liés au compte");
            txtViewMessage.setTextColor(getResources().getColor(R.color.errorColor));
            layoutUser.addView(txtViewMessage);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

}
