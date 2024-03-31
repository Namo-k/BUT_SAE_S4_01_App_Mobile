package fr.iut.sae_s4_01_app_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class ProfilActivity extends AppCompatActivity {

    private Users DatabaseUser;
    private Identifiants DatabaseId;
    String sexe_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();

        DatabaseUser = new Users(this);
        DatabaseId = new Identifiants(this);

        TextView sexe = findViewById(R.id.sexeLabelTV);
        TextView nom = findViewById(R.id.nomLabelTV);
        TextView prenom = findViewById(R.id.prenomLabelTV);
        TextView mail = findViewById(R.id.mailLabelTV);
        TextView dateNaissance = findViewById(R.id.naissanceLabelTV);
        TextView pharmacie = findViewById(R.id.pharmacieLabelTV);
        TextView medecin = findViewById(R.id.medecinLabelTV);

        sexe_ = DatabaseUser.getSexe(userID);
        updateSexeValue();

        String prenom_ = DatabaseUser.getPrenom(userID);
        String nom_ = DatabaseUser.getNom(userID);
        String dateNaissance_ =  DatabaseUser.getDataNais(userID);
        String mail_ = DatabaseId.getMail(userID);
        String pharmacie_ = DatabaseUser.getPharmacie(userID);
        String medecin_ =  DatabaseUser.getMedecin(userID);

        sexe.setText(sexe_);
        nom.setText(nom_);
        prenom.setText(prenom_);
        mail.setText(mail_);
        dateNaissance.setText(dateNaissance_);
        pharmacie.setText(pharmacie_);
        medecin.setText(medecin_);

        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);

        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // bouton modifier
        TextView modifier = findViewById(R.id.btnModifier);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, ProfilModifActivity.class);
                startActivity(intent);
            }
        });

        // bouton suppCompte
        CardView supprimer = findViewById(R.id.btnSupprimerCompte);

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(userID);


            }
        });

        TextView credits = findViewById(R.id.credits);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialogue_apropos, null);
                builder.setView(dialogView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProfilActivity.this, ProfilActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Affichage des informations lors du click sur point interrogation
        CardView interrogation1 = (CardView) findViewById(R.id.interrogation1);
        CardView interrogation2 = (CardView) findViewById(R.id.interrogation2);
        CardView interrogation1text = (CardView) findViewById(R.id.interrogation1text);
        CardView interrogation2text = (CardView) findViewById(R.id.interrogation2text);
        interrogation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interrogation1text.getVisibility() == View.VISIBLE) interrogation1text.setVisibility(View.INVISIBLE);
                else interrogation1text.setVisibility(View.VISIBLE);
            }
        });
        interrogation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interrogation2text.getVisibility() == View.VISIBLE) interrogation2text.setVisibility(View.INVISIBLE);
                else interrogation2text.setVisibility(View.VISIBLE);
            }
        });

        // navbar click
        ImageView btnAncienne = (ImageView) findViewById(R.id.ancienneBtn);
        ImageView btnHome = (ImageView) findViewById(R.id.homeBtn);
        btnAncienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, ancienneAlerteActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateSexeValue() {
        int userID = ((UserId) getApplication()).getUserID();
        sexe_ = DatabaseUser.getSexe(userID); // Récupérer le sexe de l'utilisateur
        switch (sexe_) {
            case "Homme":
                sexe_ = getString(R.string.homme);
                break;
            case "Male":
                sexe_ = getString(R.string.homme);
                break;
            case "Femme":
                sexe_ = getString(R.string.femme);
                break;
            case "Female":
                sexe_ = getString(R.string.femme);
                break;
            case "Non renseigné":
                sexe_ = getString(R.string.nr);
                break;
            case "Not specified":
                sexe_ = getString(R.string.nr);
                break;
        }
    }

    private void showConfirmationDialog(int userID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Êtes vous certain de vouloir supprimer votre compte ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ProfilActivity.this, "Votre compte a bien été supprimé", Toast.LENGTH_SHORT).show();
                DatabaseUser.deleteUserData(userID);
                DatabaseId.deleteUserData(userID);
                Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
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
}