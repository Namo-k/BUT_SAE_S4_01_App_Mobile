package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;

public class ToutAlerteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tout_alerte);

        // Bouton de la navbar
        ImageView btnHome = (ImageView) findViewById(R.id.homeBtn);
        ImageView btnStat = (ImageView) findViewById(R.id.statBtn);
        TextView nbTotalAlertes = (TextView) findViewById(R.id.nbTotalAlertes);

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        Alertes alertesDb = new Alertes(this);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToutAlerteActivity.this, AccueilAdminActivity.class);
                startActivity(intent);
            }
        });

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToutAlerteActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        // se deconnecter
        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ToutAlerteActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // à faire avec BD
        ListView listView = findViewById(R.id.toutAlertelistView);

        // Récupérer les alertes depuis la base de données
        List<Alerte> alertes = alertesDb.getAllAlertes();

        nbTotalAlertes.setText("Vous avez " + alertes.size() +  " alertes au total");

        AlerteAdapterTout adapter = new AlerteAdapterTout(this, alertes);
        listView.setAdapter(adapter);

    }

}