package fr.iut.sae_s4_01_app_mobile;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class AccueilAdminActivity extends AppCompatActivity {

    private Users DatabaseUser;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_admin);

        DatabaseUser = new Users(this);
        context = this;

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();

        // Initialisation des éléments de l'interface
        ImageView toutAlerteBtn = findViewById(R.id.toutAlerteBtn);
        ImageView statsBtn = findViewById(R.id.statsBtn);
        TextView btnStats = findViewById(R.id.btnStats);
        TextView btnAlertes = findViewById(R.id.btnAlertes);
        TextView btnPrenom = findViewById(R.id.prenom);

        // Récupération du prénom de l'utilisateur connecté
        String prenom = DatabaseUser.getPrenom(userID);
        btnPrenom.append(prenom);

        //Section Stats
        TextView nbrSignalementTV = (TextView) findViewById(R.id.nbrSignalementTV);
        TextView medicamentLePlusSignaleTV = (TextView) findViewById(R.id.medicamentLePlusSignaleTV);
        TextView typeMedicamentLePlusSignaleTV = (TextView) findViewById(R.id.typeMedicamentLePlusSignaleTV);
        TextView nbrSaisiCIPTV = (TextView) findViewById(R.id.nbrSaisiCIPTV);
        TextView nbrSaisiDataMatrixTV = (TextView) findViewById(R.id.nbrSaisiDataMatrixTV);


        Alertes alertesDb = new Alertes(this);

        try {
            nbrSignalementTV.setText(String.valueOf(alertesDb.getNombreTotalAlertes()));
            medicamentLePlusSignaleTV.setText(alertesDb.getMedicamentLePlusSignale());
            typeMedicamentLePlusSignaleTV.setText(alertesDb.getCategorieMedicamentLePlusSignale());
            nbrSaisiCIPTV.setText(String.valueOf(alertesDb.getNombreAlertesParSaisie()));
            nbrSaisiDataMatrixTV.setText(String.valueOf(alertesDb.getNombreAlertesParScan()));
            Log.i("TypeAlerte", alertesDb.getOccurrencesPathologies().toString());

        } catch (Exception e) {
            Toast.makeText(AccueilAdminActivity.this, "Erreurs dans les stats !", Toast.LENGTH_SHORT).show();
        }

        // Gestion des actions sur les boutons
        toutAlerteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, ToutAlerteActivity.class);
                finish();
                startActivity(intent);
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, StatistiqueActivity.class);
                finish();
                startActivity(intent);

                /*ProfilFragment ProfilFragment = new ProfilFragment();
                replaceFragment(ProfilFragment);*/
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, StatistiqueActivity.class);
                startActivity(intent);

                /*ProfilFragment ProfilFragment = new ProfilFragment();
                replaceFragment(ProfilFragment);*/
            }
        });

        btnAlertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, ToutAlerteActivity.class);
                startActivity(intent);
            }
        });

        // Animation des colombes
        animateColombes(R.id.colombes1);
        animateColombes(R.id.colombes2);

        // Gestion du bouton de déconnexion
        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Bloc d'anciennes alertes
        CardView blocAncienneAlerte = findViewById(R.id.blocAncienneAlerte);
        blocAncienneAlerte.setVisibility(View.INVISIBLE);

        // Récupération de la dernière alerte
        List<Alerte> alertes = alertesDb.getAllAlertesDESC();
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            blocAncienneAlerte.setVisibility(View.VISIBLE);
            alerteLaPlusRecente = alertes.get(0);
        }

        // Affichage des détails de la dernière alerte
        if (alerteLaPlusRecente != null) {
            ((TextView) findViewById(R.id.medicamentTV)).setText(alerteLaPlusRecente.getMedicament());
            ((TextView) findViewById(R.id.raisonTV)).setText(alerteLaPlusRecente.getRaison());
            ((TextView) findViewById(R.id.messageTV)).setText(alerteLaPlusRecente.getMessage());
            ((TextView) findViewById(R.id.dataTV)).setText(alerteLaPlusRecente.getDateAlerte());

            Users users = new Users(context);
            String nomAlerte = users.getNom(alerteLaPlusRecente.getIdUser());
            String prenomAlerte = users.getPrenom(alerteLaPlusRecente.getIdUser());
            TextView personneTV = findViewById(R.id.personneTV);
            personneTV.setText(prenomAlerte + " " + nomAlerte.toUpperCase());
        }
    }

    // Méthode pour animer les colombes
    private void animateColombes(int imageViewId) {
        ImageView colombeImage = findViewById(imageViewId);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(colombeImage, "translationY", 0f, -25f);
        translationY.setRepeatCount(ObjectAnimator.INFINITE); // Répéter l'animation indéfiniment
        translationY.setRepeatMode(ObjectAnimator.REVERSE); // Inverser l'animation pour créer l'effet de retour
        translationY.setDuration(1000);
        translationY.setInterpolator(new LinearInterpolator());
        translationY.start();
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
