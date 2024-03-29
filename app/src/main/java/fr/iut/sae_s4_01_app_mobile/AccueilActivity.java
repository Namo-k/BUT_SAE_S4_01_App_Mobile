package fr.iut.sae_s4_01_app_mobile;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class AccueilActivity extends AppCompatActivity {


    private Users DatabaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        DatabaseUser = new Users(this);


        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        ImageView btnSaisie = findViewById(R.id.saisie_logo);


        ImageView btnAncienne = (ImageView) findViewById(R.id.ancienneBtn);
        ImageView btnProfil = (ImageView) findViewById(R.id.userBtn);
        TextView btnAlertes = (TextView) findViewById(R.id.btnAlertes);
        TextView btnPrenom = (TextView) findViewById(R.id.prenom);

        String prenom = DatabaseUser.getPrenom(userID);
        btnPrenom.append(prenom);

        TextView medicamentTV = findViewById(R.id.medicamentTV);
        TextView raisonTV = findViewById(R.id.raisonTV);
        TextView messageTV = findViewById(R.id.messageTV);
        TextView dataTV = findViewById(R.id.dataTV);

        btnSaisie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, CipManuelleActivity.class);
                startActivity(intent);
            }
        });
        btnAncienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ancienneAlerteActivity.class);
                startActivity(intent);
            }
        });
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });
        btnAlertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ancienneAlerteActivity.class);
                startActivity(intent);
            }
        });
        btnPrenom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        // Affichage des informations lors du click sur point interrogation
        CardView interrogation1 = (CardView) findViewById(R.id.interrogation1);
        CardView interrogation2 = (CardView) findViewById(R.id.interrogation2);
        CardView interrogation1text = (CardView) findViewById(R.id.interrogation1text);
        CardView interrogation2text = (CardView) findViewById(R.id.interrogation2text);
        interrogation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interrogation1text.getVisibility() == View.VISIBLE) {
                    interrogation1text.setVisibility(View.INVISIBLE);
                } else {
                    interrogation1text.setVisibility(View.VISIBLE);
                }
            }
        });

        interrogation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interrogation2text.getVisibility() == View.VISIBLE) {
                    interrogation2text.setVisibility(View.INVISIBLE);
                } else {
                    interrogation2text.setVisibility(View.VISIBLE);
                }
            }
        });


        // Animation des 2 colombes
        ImageView colombeDroiteImage = (ImageView) findViewById(R.id.colombes1);
        ImageView colombeGaucheImage = (ImageView) findViewById(R.id.colombes2);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(colombeDroiteImage, "translationY", 0f, -25f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(colombeGaucheImage, "translationY", 0f, -25f);
        translationY.setRepeatCount(ObjectAnimator.INFINITE); // Répéter l'animation indéfiniment
        translationY.setRepeatMode(ObjectAnimator.REVERSE); // Inverser l'animation pour créer l'effet de retour
        translationX.setRepeatCount(ObjectAnimator.INFINITE); // Répéter l'animation indéfiniment
        translationX.setRepeatMode(ObjectAnimator.REVERSE); // Inverser l'animation pour créer l'effet de retour
        translationY.setDuration(1000);
        translationX.setDuration(1000);
        translationY.setInterpolator(new LinearInterpolator());
        translationX.setInterpolator(new LinearInterpolator());
        translationY.start();
        translationX.start();

        ImageView sedeconnecterBtn = (ImageView) findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        CardView blocAncienneAlerte = findViewById(R.id.blocAncienneAlerte);
        blocAncienneAlerte.setVisibility(View.INVISIBLE);


        Alertes alertesDb = new Alertes(this);

        List<Alerte> alertes = alertesDb.getAllAlertesDESC(userID);
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            blocAncienneAlerte.setVisibility(View.VISIBLE);
            alerteLaPlusRecente = alertes.get(0);
        }

        if (alerteLaPlusRecente != null) {
            ((TextView) findViewById(R.id.medicamentTV)).setText(alerteLaPlusRecente.getMedicament());
            ((TextView) findViewById(R.id.raisonTV)).setText(alerteLaPlusRecente.getRaison());
            ((TextView) findViewById(R.id.messageTV)).setText(alerteLaPlusRecente.getMessage());
            ((TextView) findViewById(R.id.dataTV)).setText(alerteLaPlusRecente.getDateAlerte());
        }

    }
}