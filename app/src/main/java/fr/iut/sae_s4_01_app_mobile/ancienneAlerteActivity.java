package fr.iut.sae_s4_01_app_mobile;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ancienneAlerteActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancienne_alerte);

        ListView listView = findViewById(R.id.ancienneAlertelistView);

        List<Alerte> alertes = new ArrayList<>();

        alertes.add(new Alerte(1, "Doliprane", "Renouvellement impossible", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(2, "Ranbaxy", "Rupture de stock", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));
        alertes.add(new Alerte(3, "Paracetamol", "Livraison tardive", "Ma pharmacie m’a prévenu qu’il n’y a plus aucun stock de ce médicament dans toute la ville !", "18 février 2024"));


        AlerteAdapter adapter = new AlerteAdapter(this, alertes);
        listView.setAdapter(adapter);

    }

}