package fr.iut.sae_s4_01_app_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;
import fr.iut.sae_s4_01_app_mobile.bd.Notifications;

public class ChangePasswordActivity extends AppCompatActivity {
    private Identifiants DatabaseIdentifiant;

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        DatabaseIdentifiant = new Identifiants(ChangePasswordActivity.this);
        Notifications notificationsDB = new Notifications(this);

        Intent intent = getIntent();

        if (intent != null) {
            email = intent.getStringExtra("email");
        }

        EditText mdp = findViewById(R.id.mdp);
        EditText mdpConfirmation = findViewById(R.id.mdpConfirmation);
        Button reset = findViewById(R.id.reset);



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mdpStr = mdp.getText().toString();
                String mdpConfStr = mdpConfirmation.getText().toString();
                if (!isPasswordValid(mdpStr) || !isPasswordValid(mdpConfStr) ) {
                    Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.ccmdp), Toast.LENGTH_SHORT).show();
                }else{

                    if (mdpStr.equals(mdpConfStr)) {

                        UserId myApp = (UserId) getApplication();
                        int userID = myApp.getUserID();

                        DatabaseIdentifiant.updatePassword(email, mdpStr);
                        boolean insertionNotifReussie = notificationsDB.insertData((int) userID, "Sécurité de votre compte", "Votre mot de passe a été changé recémment. Si ce n'est pas vous, notifiez-le !", "mdp");

                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.imdp), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });



    }




    private boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

}