package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class RegisterActivity2 extends AppCompatActivity {

    private Users DatabaseUser;
    private Identifiants DatabaseIdentifiant;
    private String genre;
    private String name;
    private String prenom;
    private String date;
    private String pharmacie = "Non renseigné";
    private String medecin = "Non renseigné";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        ImageView flecheRetour = findViewById(R.id.retour);
        flecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            genre = intent.getStringExtra("genre");
            name = intent.getStringExtra("name");
            prenom = intent.getStringExtra("prenom");
            date = intent.getStringExtra("date");
        }
        DatabaseUser = new Users(this);
        DatabaseIdentifiant = new Identifiants(this);

        Button validateButton = findViewById(R.id.loginBtn);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.email);
                EditText password = findViewById(R.id.mdp);
                String password_ = password.getText().toString().trim();
                String email_ = email.getText().toString().trim();

                CheckBox checkbox = findViewById(R.id.checkbox);

                if (!isValidEmail(email_)){
                    Toast.makeText(RegisterActivity2.this, "Adresse email non valide", Toast.LENGTH_SHORT).show();
                }else if (!isPasswordValid(password_)) {
                    Toast.makeText(RegisterActivity2.this, "Le mot de passe doit avoir au moins 8 caractères, une majuscule et un chiffre.", Toast.LENGTH_SHORT).show();
                }else if(!checkbox.isChecked()){
                    Toast.makeText(RegisterActivity2.this, "Veuillez accepter les conditions générales d'utilisation", Toast.LENGTH_SHORT).show();
                }else {
                    long userID = DatabaseUser.insertData(genre, name, prenom, date, pharmacie, medecin);

                    if(userID != -1) {
                        Boolean checkUserEmail = DatabaseIdentifiant.checkEmail(email_);
                        if (checkUserEmail == false) {
                            boolean successIdt = DatabaseIdentifiant.insertData(email_, password_, userID);
                            Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity2.this, "Un compte avec ce mail existe déjà! Connectez-vous", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity2.this, "Une compte avec ces nom et prenom existe déjà! Connectez-vous ", Toast.LENGTH_SHORT).show();

                    }
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
