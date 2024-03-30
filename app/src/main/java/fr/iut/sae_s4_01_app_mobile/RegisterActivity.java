package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;



import androidx.appcompat.app.AppCompatActivity;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Spinner genreSpinner = findViewById(R.id.genre);

        List<String> genresList = new ArrayList<>();
        genresList.add(getResources().getString(R.string.selectGenre));
        genresList.add(getResources().getString(R.string.homme));
        genresList.add(getResources().getString(R.string.femme));
        genresList.add(getResources().getString(R.string.nr));
        genreSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genresList));


        Button passersuite = findViewById(R.id.loginBtn);

        passersuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nom = findViewById(R.id.prenom);
                EditText prenom = findViewById(R.id.prenom1);
                EditText jour = findViewById(R.id.Djour);
                EditText mois = findViewById(R.id.Dmois);
                EditText annee = findViewById(R.id.annee);
                String nom_ = nom.getText().toString();
                String prenom_ = prenom.getText().toString();
                String jour_ = jour.getText().toString();
                String mois_ = mois.getText().toString();
                String annee_ = annee.getText().toString();
                String genre = genreSpinner.getSelectedItem().toString();

                if (nom_.isEmpty() || prenom_.isEmpty() || jour_.isEmpty() || mois_.isEmpty() || annee_.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();

                } else {
                    if(genre.equals(getResources().getString(R.string.selection))){
                        Toast.makeText(RegisterActivity.this, getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
                    } else if (!verifjour(jour_)) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.invalid_day), Toast.LENGTH_SHORT).show();
                    } else if (!verifmois(mois_)) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.invalid_month), Toast.LENGTH_SHORT).show();
                    } else if (!verifanne(annee_)) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.invalid_year), Toast.LENGTH_SHORT).show();

                    }else {
                        Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                        //startActivity(intent);
                        String datecomplete = jour_ + "/" + mois_ + "/" + annee_;
                        intent.putExtra("genre", genre);
                        intent.putExtra("name", nom_);
                        intent.putExtra("prenom", prenom_);
                        intent.putExtra("date", datecomplete);
                        startActivity(intent);

                    }
                }
            }
        });


        ImageView flecheRetour = findViewById(R.id.retour);
        flecheRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public boolean verifanne(String annee){
        int year;
        if (annee.length() == 4) {
            year = Integer.parseInt(annee);
            if (year >= 1900 && year <= 2006) {
                return true;
            }
        }
        return false;
    }
    public boolean verifmois(String mois){
        int month;
        if (mois.length() == 2) {
            month = Integer.parseInt(mois);
            if (month >= 01 && month <= 12) {
                return true;
            }
        }
        return false;
    }

    public boolean verifjour(String jour){
        int day;
        if (jour.length() == 2) {
            day = Integer.parseInt(jour);
            if (day >= 1 && day <= 31) {
                return true;
            }
        }
        return false;
    }


}
