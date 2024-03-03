package fr.iut.sae_s4_01_app_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CipManuelleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cip_manuelle);

        TextView textView = findViewById(R.id.textView);
        String text = "Vous avez plutôt une DataMatrix ?";
        SpannableString spannableString = new SpannableString(text);

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        int start = text.indexOf("DataMatrix");
        int end = start + "DataMatrix".length();
        spannableString.setSpan(boldSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(spannableString);

        TextView valider = findViewById(R.id.btnEnregistrer);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CipManuelleActivity.this, NewAlertActivity.class);
                startActivity(intent);
            }
        });

        TextView annuler = findViewById(R.id.btnAnnuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir retourner en arrière ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CipManuelleActivity.this, AccueilActivity.class);
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

        ImageView accueil = findViewById(R.id.homeBtn);
        accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                    builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'accueil ? Votre saisie sera annulée.");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CipManuelleActivity.this, AccueilActivity.class);
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


        ImageView ancienneAlerte = findViewById(R.id.ancienneBtn);
        ancienneAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'ancienne alerte ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CipManuelleActivity.this, ancienneAlerteActivity.class);
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

        ImageView profil = findViewById(R.id.userBtn);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipManuelleActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à votre profil ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CipManuelleActivity.this, ProfilActivity.class);
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

    }
}