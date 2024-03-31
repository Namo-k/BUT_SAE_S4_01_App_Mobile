package fr.iut.sae_s4_01_app_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.iut.sae_s4_01_app_mobile.bd.Medicament;

public class CipManuelleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cip_manuelle);
        EditText editCodeCIP = findViewById(R.id.editCodeCIP);
        editCodeCIP.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});

        TextView textView = findViewById(R.id.textView);
        String text = getResources().getString(R.string.vdata);
        SpannableString spannableString = new SpannableString(text);

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        int start = text.indexOf("DataMatrix");
        int end = start + "DataMatrix".length();
        spannableString.setSpan(boldSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(spannableString);
        editCodeCIP = findViewById(R.id.editCodeCIP);

        // Affichage des informations lors du clic sur le point d'interrogation
        CardView interrogation1 = findViewById(R.id.interrogation1);
        CardView interrogation1text = findViewById(R.id.interrogation1text);
        interrogation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(interrogation1text);
            }
        });

        TextView valider = findViewById(R.id.btnEnregistrer);
        EditText finalEditCodeCIP = editCodeCIP;
        EditText finalEditCodeCIP1 = editCodeCIP;
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeCIP = finalEditCodeCIP1.getText().toString().trim();


                if (codeCIP.length() < 8) {
                    Toast.makeText(CipManuelleActivity.this, getResources().getString(R.string.ccip), Toast.LENGTH_SHORT).show();
                    return;
                }


                Medicament medicamentDatabase = new Medicament(CipManuelleActivity.this);
                String nomMedicament = medicamentDatabase.getNomMedicamentByCodeCIP(codeCIP);
                if (nomMedicament == null || nomMedicament.isEmpty()) {

                    Toast.makeText(CipManuelleActivity.this, getResources().getString(R.string.ocip), Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(CipManuelleActivity.this, NewAlertActivity.class);
                    intent.putExtra("codeCIP", codeCIP);
                    startActivity(intent);
                }
            }
        });



        TextView annuler = findViewById(R.id.btnAnnuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                builder.setMessage(getResources().getString(R.string.back));
                builder.setPositiveButton(getResources().getString(R.string.Oui), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CipManuelleActivity.this, AccueilActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.Non), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView accueil = findViewById(R.id.homeBtn);
        accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                builder.setMessage(getResources().getString(R.string.retour));
                builder.setPositiveButton(getResources().getString(R.string.Oui), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CipManuelleActivity.this, AccueilActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.Non), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        ImageView ancienneAlerte = findViewById(R.id.ancienneBtn);
        ancienneAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                builder.setMessage(getResources().getString(R.string.acc));
                builder.setPositiveButton(getResources().getString(R.string.Oui), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CipManuelleActivity.this, ancienneAlerteActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.Non), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ImageView profil = findViewById(R.id.userBtn);

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                builder.setMessage(getResources().getString(R.string.pcc));
                builder.setPositiveButton(getResources().getString(R.string.Oui), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CipManuelleActivity.this, ProfilActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.Non), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}