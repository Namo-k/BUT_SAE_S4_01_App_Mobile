package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;

public class StatistiqueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);


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
            Toast.makeText(StatistiqueActivity.this, "Erreurs dans les stats !", Toast.LENGTH_SHORT).show();
        }

        Spinner spinner = findViewById(R.id.spinnerGraphique);

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Nombre de signalement par types de maladie");
        spinnerItems.add("Nombre de signalement par mois");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

    }

}