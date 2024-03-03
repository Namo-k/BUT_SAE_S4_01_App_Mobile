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

public class ancienneAlerteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancienne_alerte);

        // Bouton de la navbar
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

        // se deconnecter
        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ancienneAlerteActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // à faire avec BD
        ListView listView = findViewById(R.id.ancienneAlertelistView);

        List<Alerte> alertes = new ArrayList<>();

        /*alertes.add(new Alerte(1, "Doliprane", "Renouvellement impossible", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(2, "Ranbaxy", "Rupture de stock", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));

*/
        nbAlerte.append("Vous avez " + alertes.size() +  " alertes au total");

        AlerteAdapter adapter = new AlerteAdapter(this, alertes);
        listView.setAdapter(adapter);

    }

}