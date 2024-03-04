package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;

public class ancienneAlerteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancienne_alerte);
        Alertes alertesDb = new Alertes(this);
        int totalAlertCount = alertesDb.getNombreTotalAlertes();

        ImageView btnHome = (ImageView) findViewById(R.id.homeBtn);
        ImageView btnProfil = (ImageView) findViewById(R.id.userBtn);
        TextView nbAlerte = (TextView) findViewById(R.id.nbAlertes);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ancienneAlerteActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ancienneAlerteActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = findViewById(R.id.ancienneAlertelistView);

        // Récupérer les alertes depuis la base de données
        List<Alerte> alertes = alertesDb.getAllAlertes();

        nbAlerte.append("Vous avez " + totalAlertCount +  " alertes au total");

        AlerteAdapter adapter = new AlerteAdapter(this, alertes);
        listView.setAdapter(adapter);
    }
}
