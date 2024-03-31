package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;
import fr.iut.sae_s4_01_app_mobile.bd.Notifications;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class RegisterActivity2 extends AppCompatActivity {

    private Users DatabaseUser;
    private Identifiants DatabaseIdentifiant;
    private String genre;
    private String name;
    private String prenom;
    private String date;
    private String pharmacie;
    private String medecin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        ImageView flecheRetour = findViewById(R.id.retour);
        Notifications notificationsDB = new Notifications(this);
        flecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Afficher cacher mdp oeil
        ImageView oeilouvert = findViewById(R.id.oeilouvert);
        ImageView oeilferme = findViewById(R.id.oeilferme);
        EditText passEdit = findViewById(R.id.mdp);

        oeilferme.setVisibility(View.VISIBLE);
        passEdit.setTransformationMethod(new PasswordTransformationMethod());
        oeilouvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeilouvert.setVisibility(View.INVISIBLE);
                oeilferme.setVisibility(View.VISIBLE);
                passEdit.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        oeilferme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeilouvert.setVisibility(View.VISIBLE);
                oeilferme.setVisibility(View.INVISIBLE);
                passEdit.setTransformationMethod(null);
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

        pharmacie = getResources().getString(R.string.nr);
        medecin = getResources().getString(R.string.nr);

        Button validateButton = findViewById(R.id.loginBtn);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.email);
                EditText password = findViewById(R.id.mdp);
                String password_ = password.getText().toString().trim();
                String email_ = email.getText().toString().trim();

                CheckBox checkbox = findViewById(R.id.checkbox);

                if (!isValidEmail(email_)) {
                    Toast.makeText(RegisterActivity2.this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
                } else if (!isPasswordValid(password_)) {
                    Toast.makeText(RegisterActivity2.this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
                } else if (!checkbox.isChecked()) {
                    Toast.makeText(RegisterActivity2.this, getString(R.string.accept_terms), Toast.LENGTH_SHORT).show();
                } else {
                    long userID = DatabaseUser.insertData(genre, name, prenom, date, pharmacie, medecin);

                    if (userID != -1) {
                        Boolean checkUserEmail = DatabaseIdentifiant.checkEmail(email_);
                        if (checkUserEmail == false) {
                            boolean successIdt = DatabaseIdentifiant.insertData(email_, password_, userID);
                            boolean insertionNotifReussie = notificationsDB.insertData((int) userID, "Bienvenue !", "Nous sommes ravis de vous accueillir. Complétez votre profil !", "bienvenue");

                            Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
                            Toast.makeText(RegisterActivity2.this, getString(R.string.account_created), Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity2.this, getString(R.string.existing_email), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity2.this, getString(R.string.existing_account), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean isValidEmail(String email) {
                return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }

            private boolean isPasswordValid(String password) {
                return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
            }
        });
    }
}
