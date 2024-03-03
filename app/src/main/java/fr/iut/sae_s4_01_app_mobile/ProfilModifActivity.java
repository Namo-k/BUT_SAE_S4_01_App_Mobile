package fr.iut.sae_s4_01_app_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class ProfilModifActivity extends AppCompatActivity {


    private Users DatabaseUser;
    private Identifiants DatabaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilmodif);

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();

        DatabaseUser = new Users(this);
        DatabaseId = new Identifiants(this);

        String sexe_ = DatabaseUser.getSexe(userID);
        String prenom_ = DatabaseUser.getPrenom(userID);
        String nom_ = DatabaseUser.getNom(userID);
        String dateNaissance_ =  DatabaseUser.getDataNais(userID);
        String mail_ = DatabaseId.getMail(userID);
        String pharmacie_ = DatabaseUser.getPharmacie(userID);
        String medecin_ =  DatabaseUser.getMedecin(userID);

        EditText sexe = (EditText) findViewById(R.id.sexeEdit);
        EditText nom = (EditText) findViewById(R.id.nomEdit);
        EditText prenom = (EditText) findViewById(R.id.prenomEdit);
        EditText mail = (EditText) findViewById(R.id.mailEdit);
        EditText dateNaissance = (EditText) findViewById(R.id.naissanceEdit);
        EditText pharmacie = (EditText) findViewById(R.id.pharmacieEdit);
        EditText medecin = (EditText) findViewById(R.id.medecinEdit);

        sexe.setText(sexe_);
        nom.setText(nom_);
        prenom.setText(prenom_);
        mail.setText(mail_);
        dateNaissance.setText(dateNaissance_);
        if (!pharmacie_.equals("Non renseigné")) {
            pharmacie.setText(pharmacie_);
        }
        if (!medecin_.equals("Non renseigné")) {
            medecin.setText(medecin_);
        }

        // bonton enregistrer
        CardView enregistrer = findViewById(R.id.btnEnregistrer);
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sexeText = sexe.getText().toString().trim();
                String nomText = nom.getText().toString().trim();
                String prenomText = prenom.getText().toString().trim();
                String mailText = mail.getText().toString().trim();
                String dateNaissanceText = dateNaissance.getText().toString().trim();
                String pharmacieText = pharmacie.getText().toString().trim();
                String medecinText = medecin.getText().toString().trim();

                if (sexeText.isEmpty() || nomText.isEmpty() || prenomText.isEmpty() || dateNaissanceText.isEmpty() || mailText.isEmpty()) {
                    Toast.makeText(ProfilModifActivity.this, "Veuillez remplir tous le champs obligatoire", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidEmail(mailText)) {
                    Toast.makeText(ProfilModifActivity.this, "L'adresse mail n'est pas sous le bon format", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pharmacieText.isEmpty()) { pharmacieText = "Non renseigné"; }
                    if (medecinText.isEmpty()) { medecinText = "Non renseigné"; }
                    DatabaseUser.updateData(userID, sexeText, nomText, prenomText, dateNaissanceText, pharmacieText, medecinText);
                    DatabaseId.updateData(userID, mailText);
                    Toast.makeText(ProfilModifActivity.this, "Vos informations ont bien été enregistré", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfilModifActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            }
        });

        // btn deco
        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ProfilModifActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // bouton annuler
        CardView annuler = findViewById(R.id.btnAnnuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilModifActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir retourner en arriere ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProfilModifActivity.this, ProfilActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilModifActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'ancienne alerte ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProfilModifActivity.this, ancienneAlerteActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilModifActivity.this);
                builder.setMessage("Êtes-vous sûr de vouloir accéder à la page d'accueil ? Votre saisie sera annulée.");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProfilModifActivity.this, AccueilActivity.class);
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

    private boolean isValidEmail(String mail) {
        return !TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }
}
