package fr.iut.sae_s4_01_app_mobile;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class AccueilAdminActivity extends AppCompatActivity {

    private Users DatabaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_admin);

        DatabaseUser = new Users(this);


        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();

        //navbar
        ImageView toutAlerteBtn = (ImageView) findViewById(R.id.toutAlerteBtn);
        ImageView statsBtn = (ImageView) findViewById(R.id.statsBtn);

        //bouton dans les sections
        TextView btnStats = (TextView) findViewById(R.id.btnStats);
        TextView btnAlertes = (TextView) findViewById(R.id.btnAlertes);

        TextView btnPrenom = (TextView) findViewById(R.id.prenom);

        String prenom = DatabaseUser.getPrenom(userID);
        btnPrenom.append(prenom);

        //Section Stats
        TextView nbrSignalementTV = (TextView) findViewById(R.id.nbrSignalementTV);
        TextView medicamentLePlusSignaleTV = (TextView) findViewById(R.id.medicamentLePlusSignaleTV);
        TextView typeMedicamentLePlusSignaleTV = (TextView) findViewById(R.id.typeMedicamentLePlusSignaleTV);
        TextView nbrSaisiCIPTV = (TextView) findViewById(R.id.nbrSaisiCIPTV);
        TextView nbrSaisiDataMatrixTV = (TextView) findViewById(R.id.nbrSaisiDataMatrixTV);


        Alertes alertesDb = new Alertes(this);
        nbrSignalementTV.setText(String.valueOf(alertesDb.getNombreTotalAlertes()));
        medicamentLePlusSignaleTV.setText(alertesDb.getMedicamentLePlusSignale());

        toutAlerteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, ToutAlerteActivity.class);
                startActivity(intent);
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });
        btnAlertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, ToutAlerteActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(AccueilAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        CardView blocAncienneAlerte = findViewById(R.id.blocAncienneAlerte);
        blocAncienneAlerte.setVisibility(View.INVISIBLE);


        List<Alerte> alertes = alertesDb.getAllAlertes();
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            blocAncienneAlerte.setVisibility(View.VISIBLE);
            alerteLaPlusRecente = alertes.get(0);
        }

        Users users = new Users(this);

        if (alerteLaPlusRecente != null) {
            ((TextView) findViewById(R.id.medicamentTV)).setText(alerteLaPlusRecente.getMedicament());
            ((TextView) findViewById(R.id.raisonTV)).setText(alerteLaPlusRecente.getRaison());
            ((TextView) findViewById(R.id.messageTV)).setText(alerteLaPlusRecente.getMessage());
            ((TextView) findViewById(R.id.dataTV)).setText(alerteLaPlusRecente.getDateAlerte());

            String nomAlerte = users.getNom(alerteLaPlusRecente.getIdUser());
            String prenomAlerte = users.getPrenom(alerteLaPlusRecente.getIdUser());
            TextView personneTV = (TextView)findViewById(R.id.personneTV);
            personneTV.setText(prenomAlerte + " " + nomAlerte.toUpperCase());
        }





    }
}
