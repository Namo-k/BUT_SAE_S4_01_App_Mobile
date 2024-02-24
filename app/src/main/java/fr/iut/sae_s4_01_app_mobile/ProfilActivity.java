package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class ProfilActivity extends AppCompatActivity {

    private int userID;

    private Users DatabaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();


        DatabaseUser = new Users(this);

        ImageView btnAncienne = (ImageView) findViewById(R.id.ancienneBtn);
        ImageView btnHome = (ImageView) findViewById(R.id.homeBtn);

        TextView nom = findViewById(R.id.nomLabelTV);
        TextView prenom = findViewById(R.id.prenomLabelTV);
        TextView dateNaissance = findViewById(R.id.naissanceLabelTV);
        String prenom_ = DatabaseUser.getPrenom(userID);
        String nom_ = DatabaseUser.getNom(userID);
        String dateNaissance_ =  DatabaseUser.getDataNais(userID);
        nom.setText(nom_);
        prenom.setText(prenom_);
        dateNaissance.setText(dateNaissance_);
        btnAncienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, ancienneAlerteActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });
    }
}