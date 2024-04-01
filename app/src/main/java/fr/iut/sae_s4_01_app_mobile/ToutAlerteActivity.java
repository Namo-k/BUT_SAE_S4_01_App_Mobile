package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;

public class ToutAlerteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tout_alerte);

        ImageView homeAdminBtn = findViewById(R.id.homeAdminBtn);
        ImageView statsBtn = findViewById(R.id.statsBtn);

        homeAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToutAlerteActivity.this, AccueilAdminActivity.class);
                finish();
                startActivity(intent);
            }
        });
        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToutAlerteActivity.this, StatistiqueActivity.class);
                finish();
                startActivity(intent);
            }
        });



        Spinner spinner = findViewById(R.id.list_tri);

        TextView nbTotalAlertes = (TextView) findViewById(R.id.nbTotalAlertes);

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        Alertes alertesDb = new Alertes(this);


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
        List<Alerte> alertes = alertesDb.getAllAlertesDESC();

        nbTotalAlertes.setText(getResources().getString(R.string.avoir) + " " + alertes.size() + " " +getResources().getString(R.string.ale) );

        AlerteAdapterTout adapter = new AlerteAdapterTout(this, alertes);
        listView.setAdapter(adapter);


        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add(getResources().getString(R.string.recent));
        spinnerItems.add(getResources().getString(R.string.ancien));
        spinnerItems.add(getResources().getString(R.string.pertinance));

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                if (selectedItem.equals(getResources().getString(R.string.recent))) {
                    List<Alerte> alertes = alertesDb.getAllAlertesDESC();
                    AlerteAdapterTout adapter = new AlerteAdapterTout(getApplication(), alertes);
                    listView.setAdapter(adapter);
                } else if (selectedItem.equals(getResources().getString(R.string.ancien))) {
                    List<Alerte> alertes = alertesDb.getAllAlertesASC();
                    AlerteAdapterTout adapter = new AlerteAdapterTout(getApplication(), alertes);
                    listView.setAdapter(adapter);
                }else if (selectedItem.equals(getResources().getString(R.string.pertinance))) {
                    List<Alerte> alertesImportantes = alertesDb.getAllAlertesImportantes();
                    List<Alerte> alertesNONImportantes = alertesDb.getAllAlertesNotImportantes();
                    List<Alerte> alertesPertinentes = new ArrayList<>();

                    alertesPertinentes.addAll(alertesImportantes);
                    alertesPertinentes.addAll(alertesNONImportantes);

                    AlerteAdapterTout adapter = new AlerteAdapterTout(getApplication(), alertesPertinentes);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


    }

}