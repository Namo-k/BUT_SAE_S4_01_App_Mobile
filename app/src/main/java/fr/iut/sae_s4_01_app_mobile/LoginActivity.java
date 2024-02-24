package fr.iut.sae_s4_01_app_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;

public class LoginActivity extends AppCompatActivity {

    private Identifiants DatabaseIdentifiant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button loginBtn = findViewById(R.id.loginBtn);
        EditText usernameLN = findViewById(R.id.usernameET);
        EditText passwordLN = findViewById(R.id.passwordET);

        DatabaseIdentifiant = new Identifiants(this);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginActivity", "Bouton cliqué");
                String usernameCo = usernameLN.getText().toString();
                String passwordCo = passwordLN.getText().toString();

                Log.i("LoginActivity", usernameCo);
                Log.i("LoginActivity", passwordCo);

                if (DatabaseIdentifiant.checkEmailPassword(usernameCo, passwordCo )) {

                    Toast.makeText(LoginActivity.this, "Vous êtes connecté !", Toast.LENGTH_SHORT).show();
                    int userId = DatabaseIdentifiant.getId(usernameCo, passwordCo);
                    UserId myApp = (UserId) getApplication();
                    myApp.setUserID(userId);
                    Intent intent = new Intent(LoginActivity.this, AccueilActivity.class); // page apres co
                    Bundle bundle = new Bundle();
                    bundle.putString("username", usernameCo);
                    bundle.putString("password", passwordCo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Votre email ou mot de passe est incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView textView5 = findViewById(R.id.textView5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
