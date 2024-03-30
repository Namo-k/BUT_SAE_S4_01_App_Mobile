package fr.iut.sae_s4_01_app_mobile;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class AccueilActivity extends AppCompatActivity {

    private Users DatabaseUser;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        DatabaseUser = new Users(this);
        context = this;

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        ImageView btnSaisie = findViewById(R.id.saisie_logo);

        ImageView btnAncienne = findViewById(R.id.ancienneBtn);
        ImageView btnProfil = findViewById(R.id.userBtn);
        ImageView btnNuit = findViewById(R.id.nuitbtn);
        TextView btnAlertes = findViewById(R.id.btnAlertes);
        TextView btnPrenom = findViewById(R.id.prenom);

        String prenom = DatabaseUser.getPrenom(userID);
        btnPrenom.append(prenom);

        btnSaisie.setOnClickListener(new View.OnClickListener() {
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
        btnNuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, PreferencesActivity.class);
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

        // Affichage des informations lors du clic sur le point d'interrogation
        CardView interrogation1 = findViewById(R.id.interrogation1);
        CardView interrogation2 = findViewById(R.id.interrogation2);
        CardView interrogation1text = findViewById(R.id.interrogation1text);
        CardView interrogation2text = findViewById(R.id.interrogation2text);
        interrogation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(interrogation1text);
            }
        });

        interrogation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(interrogation2text);
            }
        });

        // Animation des 2 colombes
        ImageView colombeDroiteImage = findViewById(R.id.colombes1);
        ImageView colombeGaucheImage = findViewById(R.id.colombes2);
        animateColombe(colombeDroiteImage);
        animateColombe(colombeGaucheImage);

        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);
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

    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void animateColombe(ImageView imageView) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", 0f, -25f);
        translationY.setRepeatCount(ObjectAnimator.INFINITE);
        translationY.setRepeatMode(ObjectAnimator.REVERSE);
        translationY.setDuration(1000);
        translationY.setInterpolator(new LinearInterpolator());
        translationY.start();
    }

    private void updateDateTextView(Alerte alerte) {
        String dateAlerte = alerte.getDateAlerte();

        Log.d("Date avant formatage", dateAlerte);

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sourceFormat.parse(dateAlerte);

            // Choix du format de date en fonction de la langue par défaut
            SimpleDateFormat targetFormat;
            if (Locale.getDefault().getLanguage().equals("fr")) {
                targetFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
            } else {
                targetFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
            }

            // Formatage de la date dans le format cible
            String formattedDate = targetFormat.format(date);

            Log.d("Date après formatage", formattedDate);

            // Affichage de la date formatée dans le TextView
            ((TextView) findViewById(R.id.dataTV)).setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        // Appelez la méthode de mise à jour lorsque l'activité reprend
        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        Alertes alertesDb = new Alertes(this);
        List<Alerte> alertes = alertesDb.getAllAlertesDESC(userID);
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            alerteLaPlusRecente = alertes.get(0);
        }

        if (alerteLaPlusRecente != null) {
            updateDateTextView(alerteLaPlusRecente);
        }
    }

    // Méthode appelée lorsque la langue est modifiée
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Appelez la méthode de mise à jour lorsque la langue est modifiée
        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        Alertes alertesDb = new Alertes(this);
        List<Alerte> alertes = alertesDb.getAllAlertesDESC(userID);
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            alerteLaPlusRecente = alertes.get(0);
        }

        if (alerteLaPlusRecente != null) {
            updateDateTextView(alerteLaPlusRecente);
        }
    }

    private void applyNightMode() {
        setTheme(R.style.Base_Theme_SAE_S4_01_App_Mobile);
    }
}
