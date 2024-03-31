package fr.iut.sae_s4_01_app_mobile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import fr.iut.sae_s4_01_app_mobile.bd.Medicament;

public class CipScanActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cip_scan);

        EditText editCodeCIP = findViewById(R.id.editCodeCIP);
        //editCodeCIP.setFilters(new InputFilter[] {new InputFilter.LengthFilter(13)});

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String CodeCip;
        if (bundle != null) {
            CodeCip = bundle.getString("datamatrix").substring(4, 17);
        }else{
            CodeCip = "3400938197235";
        }

        editCodeCIP.setText(CodeCip);

        TextView valider = findViewById(R.id.btnEnregistrer);

        //EditText finalEditCodeCIP1 = editCodeCIP;
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String codeCIP;
                if (bundle != null) {
                    codeCIP = bundle.getString("datamatrix").substring(4, 17);
                }else{
                    codeCIP = "3400938197235";
                }

                Medicament medicamentDatabase = new Medicament(CipScanActivity.this);
                String nomMedicament = null;

                try {
                    nomMedicament = medicamentDatabase.getNomMedicamentByCodeCIP(codeCIP);

                } catch (Exception e) {
                    Toast.makeText(CipScanActivity.this, "Aucun médicament trouvé pour ce numéro CIP.", Toast.LENGTH_SHORT).show();
                }

                if (nomMedicament == null || nomMedicament.isEmpty()) {
                    Toast.makeText(CipScanActivity.this, "Aucun médicament trouvé pour ce numéro CIP.", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(CipScanActivity.this, NewAlertActivity.class);
                    intent.putExtra("codeCIP", codeCIP);
                    intent.putExtra("moyen", "scan");
                    finish();
                    startActivity(intent);
                }


            }
        });

        TextView annuler = findViewById(R.id.btnAnnuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CipScanActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir retourner en arriere ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CipScanActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'accueil ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CipScanActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'ancienne alerte ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CipScanActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à votre profil ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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