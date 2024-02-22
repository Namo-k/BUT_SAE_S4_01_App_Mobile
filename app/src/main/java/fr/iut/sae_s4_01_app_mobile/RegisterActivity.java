package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button passersuite = findViewById(R.id.loginBtn);
        passersuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nom = findViewById(R.id.prenom);
                EditText prenom = findViewById(R.id.prenom1);
                EditText jour = findViewById(R.id.Djour);
                EditText mois = findViewById(R.id.Dmois);
                EditText annee = findViewById(R.id.annee);
                String nom_ = nom.getText().toString();
                String prenom_ = prenom.getText().toString();
                String jour_ = jour.getText().toString();
                String mois_ = mois.getText().toString();
                String annee_ = annee.getText().toString();
                if (nom_.isEmpty() || prenom_.isEmpty() || jour_.isEmpty() || mois_.isEmpty() || annee_.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Veuillez remplir tous le champs", Toast.LENGTH_SHORT).show();
                } else {
                    if (!verifjour(jour_)) {
                        Toast.makeText(RegisterActivity.this, "Le jour saisie n'est pas conforme", Toast.LENGTH_SHORT).show();
                    }else if(!verifmois(mois_)){
                        Toast.makeText(RegisterActivity.this, "Le mois saisie n'est pas conforme", Toast.LENGTH_SHORT).show();
                    }else if(!verifanne(annee_)){
                        Toast.makeText(RegisterActivity.this, "L'année saisie n'est pas conforme", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public boolean verifanne(String annee){
        int year;
        if (annee.length() == 4) {
            year = Integer.parseInt(annee);
            if (year >= 1900 && year <= 2024) {
                return true;
            }
        }
        return false;
    }
    public boolean verifmois(String mois){
        int month;
        if (mois.length() == 2) {
            month = Integer.parseInt(mois);
            if (month >= 01 && month <= 12) {
                return true;
            }
        }
        return false;
    }

    public boolean verifjour(String jour){
        int day;
        if (jour.length() == 2) {
            day = Integer.parseInt(jour);
            if (day >= 1 && day <= 31) {
                return true;
            }
        }
        return false;
    }


}