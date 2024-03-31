package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;

public class StatistiqueActivity extends AppCompatActivity {

    private BarChart barChart;
    private PieChart pieChart;

    private PieChart pieChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        ImageView homeAdminBtn = findViewById(R.id.homeAdminBtn);
        ImageView toutAlerteBtn = findViewById(R.id.toutAlerteBtn);
        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);

        homeAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatistiqueActivity.this, AccueilAdminActivity.class);
                finish();
                startActivity(intent);
            }
        });
        toutAlerteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatistiqueActivity.this, ToutAlerteActivity.class);
                finish();
                startActivity(intent);
            }
        });

        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StatistiqueActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        //Section Stats
        TextView nbrSignalementTV = (TextView) findViewById(R.id.nbrSignalementTV);
        TextView medicamentLePlusSignaleTV = (TextView) findViewById(R.id.medicamentLePlusSignaleTV);
        TextView typeMedicamentLePlusSignaleTV = (TextView) findViewById(R.id.typeMedicamentLePlusSignaleTV);
        TextView nbrSaisiCIPTV = (TextView) findViewById(R.id.nbrSaisiCIPTV);
        TextView nbrSaisiDataMatrixTV = (TextView) findViewById(R.id.nbrSaisiDataMatrixTV);
        TextView nbrMedicamentImportantTV = (TextView) findViewById(R.id.nbrMedicamentImportantTV);


        Alertes alertesDb = new Alertes(this);

        try {
            nbrSignalementTV.setText(String.valueOf(alertesDb.getNombreTotalAlertes()));
            medicamentLePlusSignaleTV.setText(alertesDb.getMedicamentLePlusSignale());
            typeMedicamentLePlusSignaleTV.setText(alertesDb.getCategorieMedicamentLePlusSignale());
            nbrSaisiCIPTV.setText(String.valueOf(alertesDb.getNombreAlertesParSaisie()));
            nbrSaisiDataMatrixTV.setText(String.valueOf(alertesDb.getNombreAlertesParScan()));
            nbrMedicamentImportantTV.setText(String.valueOf(alertesDb.getNombreAlertesImportant()));
            Log.i("TypeAlerte", alertesDb.getOccurrencesPathologies().toString());
            Log.i("SignalementParMois", alertesDb.getNombreSignalementsParMois().toString());

        } catch (Exception e) {
            Toast.makeText(StatistiqueActivity.this, "Erreurs dans les stats !", Toast.LENGTH_SHORT).show();
        }

        Spinner spinner = findViewById(R.id.spinnerGraphique);

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Nombre de signalement par mois");
        spinnerItems.add("Occurence par pathologie");
        spinnerItems.add("Nombre de saisie et scan");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);


        LinearLayout graphBloc1 = (LinearLayout)findViewById(R.id.graphBloc1);
        LinearLayout graphBloc2 = (LinearLayout)findViewById(R.id.graphBloc2);
        LinearLayout graphBloc3 = (LinearLayout)findViewById(R.id.graphBloc3);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    graphBloc1.setVisibility(View.VISIBLE);
                    graphBloc2.setVisibility(View.GONE);
                    graphBloc3.setVisibility(View.GONE);
                } else if(position == 1){
                    graphBloc1.setVisibility(View.GONE);
                    graphBloc3.setVisibility(View.GONE);
                    graphBloc2.setVisibility(View.VISIBLE);
                }else if(position == 2){
                    graphBloc1.setVisibility(View.GONE);
                    graphBloc2.setVisibility(View.GONE);
                    graphBloc3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Graphique

        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        pieChart2 = findViewById(R.id.pie_chart2);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries2 = new ArrayList<>();


        /*barEntries.add(new BarEntry(0f, 10, "Janvier"));
        barEntries.add(new BarEntry(1f, 20, "26/03 "));
        barEntries.add(new BarEntry(2f, 30, "27/03 "));
        barEntries.add(new BarEntry(3f, 40, "29/03 "));
        barEntries.add(new BarEntry(4f, 50, "31/03 "));

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Janvier");
        labels.add("Février");
        labels.add("Mars");
        labels.add("Avril");
        labels.add("Mai");*/

        // Récupérer les données des signalements par mois
        Map<String, Integer> signalementsParMois = alertesDb.getNombreSignalementsParMois();

        String[] mois = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

      /*  Set<String> moisSet = signalementsParMois.keySet();
        List<String> moisList = new ArrayList<>(moisSet);
        Collections.sort(moisList);*/

        for (int i = 0; i < mois.length; i++) {
            int nombreSignalements = signalementsParMois.containsKey(mois[i]) ? signalementsParMois.get(mois[i]) : 0;
            barEntries.add(new BarEntry(i, nombreSignalements));
        }


        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1f);

        barChart.setData(barData);
        barChart.setFitBars(true);

        /*XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // Définir les libellés des barres sur l'axe X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Positionner l'axe X en bas
        xAxis.setGranularity(1f); // Définir la granularité de l'axe X
        xAxis.setCenterAxisLabels(false); // Centrer les libellés sur les barres
        *//*xAxis.setAxisMinimum(0f); // Définir la valeur minimale de l'axe X
        xAxis.setAxisMaximum(4f); // Définir la valeur maximale de l'axe X*//*
        xAxis.setLabelCount(labels.size()); // Définir le nombre de libellés à afficher*/

        // Configurer l'axe X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(mois));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(mois.length);
        xAxis.setDrawGridLines(false);


        barChart.animateY(1500);
        barChart.getDescription().setText("Employees Chart");
        barChart.getDescription().setTextColor(Color.BLUE);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(true); // Désactiver les lignes de grille verticales

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false); // Désactiver les lignes de grille verticales



        //graphique 2

        // Récupérer les occurrences des pathologies
        Map<String, Integer> occurrencesPathologies = alertesDb.getOccurrencesPathologies();

        // Créer une liste pour stocker les entrées de PieChart
        pieEntries = new ArrayList<>();

        // Parcourir la carte des occurrences des pathologies
        for (Map.Entry<String, Integer> entry : occurrencesPathologies.entrySet()) {
            String pathology = entry.getKey();
            int occurrences = entry.getValue();
            pieEntries.add(new PieEntry(occurrences, pathology));
        }

        // Créer un ensemble de couleurs pour les données du PieChart
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextSize(12f);

        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Animer le PieChart
        pieChart.animateXY(1500, 1500);

        // Désactiver la description du PieChart
        pieChart.setDrawMarkers(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(true);




        /*pieEntries.add(new PieEntry(48, "A"));
        pieEntries.add(new PieEntry(50, "B"));
        pieEntries.add(new PieEntry(37, "C"));
        pieEntries.add(new PieEntry(25, "D"));
        pieEntries.add(new PieEntry(13, "E"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Nombre de signalement par types de maladie");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.animateXY(1500, 1500);
        pieChart.getDescription().setEnabled(false);*/






        //Graphique 3
        pieEntries2.add(new PieEntry(Float.parseFloat(String.valueOf(nbrSaisiCIPTV.getText())), "Saisie"));
        pieEntries2.add(new PieEntry(Float.parseFloat(String.valueOf(nbrSaisiDataMatrixTV.getText())), "Scan"));



        PieDataSet pieDataSet2 = new PieDataSet(pieEntries2, "");
        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet2.setValueTextSize(12f);

        pieDataSet2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        PieData pieData2 = new PieData(pieDataSet2);
        pieChart2.setData(pieData2);

        pieChart2.animateXY(1500, 1500);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.getLegend().setEnabled(false);
    }

}