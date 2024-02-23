package fr.iut.sae_s4_01_app_mobile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        Button validateButton = findViewById(R.id.loginBtn);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.email);
                EditText passwordEditText = findViewById(R.id.mdp);
                String password = passwordEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                CheckBox checkbox = findViewById(R.id.checkbox);

                if (!isValidEmail(email)){
                    Toast.makeText(RegisterActivity2.this, "Adresse email non valide", Toast.LENGTH_SHORT).show();
                }else if (!isPasswordValid(password)) {
                    Toast.makeText(RegisterActivity2.this, "Le mot de passe doit avoir au moins 8 caractères, une majuscule et un chiffre.", Toast.LENGTH_SHORT).show();
                }else if(!checkbox.isChecked()){
                    Toast.makeText(RegisterActivity2.this, "Veuillez accepter les conditions générales d'utilisation", Toast.LENGTH_SHORT).show();
                }


    } });

    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }
}
