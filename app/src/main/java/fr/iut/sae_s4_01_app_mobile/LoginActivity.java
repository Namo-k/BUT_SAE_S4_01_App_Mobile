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

import fr.iut.sae_s4_01_app_mobile.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.loginBtn);
        EditText usernameLN = findViewById(R.id.usernameET);
        EditText passwordLN = findViewById(R.id.passwordET);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginActivity", "Bouton cliqué");
                String usernameCo = usernameLN.getText().toString();
                String passwordCo = passwordLN.getText().toString();

                Log.i("LoginActivity", usernameCo);
                Log.i("LoginActivity", passwordCo);

                if (usernameCo.equals("abcd") && passwordCo.equals("EFGH")) {
                    Toast.makeText(LoginActivity.this, "Vous êtes connecté !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); // page apres co
                    Bundle bundle = new Bundle();
                    bundle.putString("username", usernameCo);
                    bundle.putString("password", passwordCo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login échoué", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView textView5 = findViewById(R.id.textView5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Vous avez appuyé sur la TextView !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
