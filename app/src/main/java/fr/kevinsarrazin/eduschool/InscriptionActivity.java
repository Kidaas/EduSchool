package fr.kevinsarrazin.eduschool;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import fr.kevinsarrazin.eduschool.data.User;
import fr.kevinsarrazin.eduschool.data.UserDAO;


public class InscriptionActivity extends Activity {

    public final static int LOAD_IMAGE_ACTIVITY_REQUEST = 1;

    private EditText EditTxtLogin, EditTxtPassword;
    private String pathImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        EditTxtLogin = (EditText) findViewById(R.id.editTxtLogin);
        EditTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
    }

    /**
     * Fonction d'inscription
     * @param view
     */
    public void onClickInscription(View view) {

        // Vérifie que les champs login & password ne sont pas vident
        if (!TextUtils.isEmpty(EditTxtLogin.getText()) && !TextUtils.isEmpty(EditTxtPassword.getText())){
            if (pathImg == null){
                pathImg = "";
            }
            UserDAO uDao = new UserDAO(this);

            if (uDao.getUserByLogin(EditTxtLogin.getText().toString()) == null){
                User u = new User(EditTxtLogin.getText().toString(), EditTxtPassword.getText().toString(), pathImg);
                uDao.insert(u);
                EditTxtLogin.getText().clear();
                EditTxtPassword.getText().clear();

                // Affiche une notification
                String notification =  "Vous pouvez désormais vous authentifier";
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();

                // Création d'une intention
                Intent intent = new Intent(this, LoginActivity.class);
                // Lancement de la demande de changement d'activité + demande de retour
                startActivity(intent);
            }else {
                // Affiche une notification
                String notification =  "Nom d'utilisateur indisponible";
                Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
            }

            // Sinon ne fait rien et affiche un message a l'user
        }else {
            // Affiche une notification
            String notification =  "Veuillez remplir tout les champs";
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Permet de choisir une image dans la gallerie
     *
     * @param view
     */
    public void onClickChoixImage(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, LOAD_IMAGE_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == LOAD_IMAGE_ACTIVITY_REQUEST && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           // Récupère le chemin de la photo
            pathImg = cursor.getString(columnIndex);
            cursor.close();

            ImageView imgVPreview = (ImageView) findViewById(R.id.imgVPreview);
            imgVPreview.setImageBitmap(BitmapFactory.decodeFile(pathImg));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inscription, menu);
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
