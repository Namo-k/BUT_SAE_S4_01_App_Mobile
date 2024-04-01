package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;

public class AncienneAlerteFragment extends Fragment {

    private TextView nbAlerte;
    private ListView listView;
    private Spinner spinner;
    private Alertes alertesDb;
    private int userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ancienne_alerte, container, false);


        nbAlerte = rootView.findViewById(R.id.nbAlertes);
        listView = rootView.findViewById(R.id.ancienneAlertelistView);
        spinner = rootView.findViewById(R.id.list_tri);

        UserId myApp = (UserId) getActivity().getApplication();
        userID = myApp.getUserID();
        alertesDb = new Alertes(getContext());

        ImageView sedeconnecterBtn = rootView.findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageView btnNotif = rootView.findViewById(R.id.notifBtn);

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        // Récupérer les alertes depuis la base de données
        //List<Alerte> alertes = alertesDb.getAllAlertes(userID);

        int totalAlertCount = alertesDb.getNombreTotalAlertes(userID);
        nbAlerte.append(getResources().getString(R.string.avoir) + " " + totalAlertCount + " " + getResources().getString(R.string.ale));

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add(getResources().getString(R.string.recent));
        spinnerItems.add(getResources().getString(R.string.ancien));
        spinnerItems.add(getResources().getString(R.string.pertinance));


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                if (selectedItem.equals(getResources().getString(R.string.recent))) {
                    List<Alerte> alertes = alertesDb.getAlertesDESC(userID, getContext());
                    AlerteAdapter adapter = new AlerteAdapter(getContext(), alertes);
                    listView.setAdapter(adapter);
                } else if (selectedItem.equals(getResources().getString(R.string.ancien))) {
                    List<Alerte> alertes = alertesDb.getAlertesASC(userID,getContext());
                    AlerteAdapter adapter = new AlerteAdapter(getContext(), alertes);
                    listView.setAdapter(adapter);
                }else if (selectedItem.equals(getResources().getString(R.string.pertinance))) {
                    List<Alerte> alertesImportantes = alertesDb.getAlertesImportantes(userID);
                    List<Alerte> alertesNONImportantes = alertesDb.getAlertesNotImportantes(userID);
                    List<Alerte> alertesPertinentes = new ArrayList<>();

                    alertesPertinentes.addAll(alertesImportantes);
                    alertesPertinentes.addAll(alertesNONImportantes);


                    AlerteAdapter adapter = new AlerteAdapter(getContext(), alertesPertinentes);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        return rootView;
    }
}
