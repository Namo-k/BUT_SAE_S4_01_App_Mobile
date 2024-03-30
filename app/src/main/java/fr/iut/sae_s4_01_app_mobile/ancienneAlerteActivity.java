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

public class ancienneAlerteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancienne_alerte);


        ImageView btnHome = (ImageView) findViewById(R.id.homeBtn);
        ImageView btnProfil = (ImageView) findViewById(R.id.userBtn);
        TextView nbAlerte = (TextView) findViewById(R.id.nbAlertes);

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        Alertes alertesDb = new Alertes(this);
        int totalAlertCount = alertesDb.getNombreTotalAlertes(userID);

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
        //List<Alerte> alertes = alertesDb.getAllAlertes(userID);

        nbAlerte.append(getResources().getString(R.string.avoir) + totalAlertCount +  getResources().getString(R.string.ale));




        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add(getResources().getString(R.string.recent));
        spinnerItems.add(getResources().getString(R.string.ancien));


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.list_tri);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                if (selectedItem.equals(getResources().getString(R.string.recent))) {
                    List<Alerte> alertes = alertesDb.getAllAlertesASC(userID);

                    AlerteAdapter adapter = new AlerteAdapter(getApplicationContext(), alertes);
                    listView.setAdapter(adapter);
                } else if (selectedItem.equals(getResources().getString(R.string.ancien))) {
                    List<Alerte> alertes = alertesDb.getAllAlertesDESC(userID);

                    AlerteAdapter adapter = new AlerteAdapter(getApplicationContext(), alertes);
                    listView.setAdapter(adapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Ne rien faire en cas de non-sélection
            }
        });

}}
