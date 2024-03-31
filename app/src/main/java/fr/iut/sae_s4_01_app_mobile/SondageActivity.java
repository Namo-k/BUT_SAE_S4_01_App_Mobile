package fr.iut.sae_s4_01_app_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class SondageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondage);

        Spinner spinnerNote= findViewById(R.id.spinnerNote);
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("Sélectionnez...");
        spinnerList.add("1/5 étoile");
        spinnerList.add("2/5 étoiles");
        spinnerList.add("3/5 étoiles");
        spinnerList.add("4/5 étoiles");
        spinnerList.add("5/5 étoiles");
        spinnerNote.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList));

        Spinner spinnerMethode = findViewById(R.id.spinnerMethode);
        List<String> spinnerListMeth = new ArrayList<>();
        spinnerListMeth.add("Sélectionnez...");
        spinnerListMeth.add("Scan DataMatrix");
        spinnerListMeth.add("Saisie code CIS");
        spinnerMethode.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerListMeth));

        EditText mess = (EditText) findViewById(R.id.inputMessage);

        CardView enregistrer = findViewById(R.id.btnEnregistrer);
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = spinnerNote.getSelectedItem().toString();
                String methode = spinnerMethode.getSelectedItem().toString();

                if (note.equals("Sélectionnez...")) {
                    Toast.makeText(SondageActivity.this, "Veuillez selectionner une note", Toast.LENGTH_SHORT).show();
                }
                else if (methode.equals("Sélectionnez...")) {
                    Toast.makeText(SondageActivity.this, "Veuillez selectionner une méthode préférée", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SondageActivity.this, "Réponse envoyée, merci pour votre contribution !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SondageActivity.this, AccueilActivity.class);
                    startActivity(intent);
                }
            }
        });

        // btn deco
        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SondageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // bouton annuler
        CardView annuler = findViewById(R.id.btnAnnuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SondageActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir retourner en arriere ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SondageActivity.this, ProfilActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // navbar click
        ImageView btnAncienne = (ImageView) findViewById(R.id.ancienneBtn);
        ImageView btnHome = (ImageView) findViewById(R.id.homeBtn);
        btnAncienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SondageActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'ancienne alerte ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SondageActivity.this, ancienneAlerteActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SondageActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'accueil ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SondageActivity.this, AccueilActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView etoile1 = (ImageView) findViewById(R.id.etoile1);
        ImageView etoile2 = (ImageView) findViewById(R.id.etoile2);
        ImageView etoile3 = (ImageView) findViewById(R.id.etoile3);
        ImageView etoile4 = (ImageView) findViewById(R.id.etoile4);
        ImageView etoile5 = (ImageView) findViewById(R.id.etoile5);

        spinnerNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Récupérer le texte sélectionné dans le Spinner
                String selectedText = spinnerNote.getSelectedItem().toString();

                // Initialiser le nombre d'étoiles à afficher
                int numberOfStars = 0;

                // Vérifier le texte sélectionné et attribuer le nombre d'étoiles en conséquence
                switch (selectedText) {
                    case "1/5 étoile":
                        numberOfStars = 1;
                        break;
                    case "2/5 étoiles":
                        numberOfStars = 2;
                        break;
                    case "3/5 étoiles":
                        numberOfStars = 3;
                        break;
                    case "4/5 étoiles":
                        numberOfStars = 4;
                        break;
                    case "5/5 étoiles":
                        numberOfStars = 5;
                        break;
                    default:
                        numberOfStars = 0; // Aucune sélection ou "Sélectionnez une note"
                        break;
                }

                // Afficher les étoiles en fonction du nombre sélectionné
                etoile1.setVisibility(numberOfStars >= 1 ? View.VISIBLE : View.GONE);
                etoile2.setVisibility(numberOfStars >= 2 ? View.VISIBLE : View.GONE);
                etoile3.setVisibility(numberOfStars >= 3 ? View.VISIBLE : View.GONE);
                etoile4.setVisibility(numberOfStars >= 4 ? View.VISIBLE : View.GONE);
                etoile5.setVisibility(numberOfStars >= 5 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Ne rien faire si rien n'est sélectionné
            }
        });
    }
}
