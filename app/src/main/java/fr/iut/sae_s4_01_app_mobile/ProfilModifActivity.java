package fr.iut.sae_s4_01_app_mobile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;
import fr.iut.sae_s4_01_app_mobile.bd.Notifications;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class ProfilModifActivity extends AppCompatActivity {

    private Users DatabaseUser;
    private Identifiants DatabaseId;
    String sexe_;

    String pharmacie_;
    String medecin_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilmodif);

        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        Notifications notificationsDB = new Notifications(this);

        DatabaseUser = new Users(this);
        DatabaseId = new Identifiants(this);

        EditText sexe = (EditText) findViewById(R.id.sexeEdit);
        EditText nom = (EditText) findViewById(R.id.nomEdit);
        EditText prenom = (EditText) findViewById(R.id.prenomEdit);
        EditText mail = (EditText) findViewById(R.id.mailEdit);
        EditText dateNaissance = (EditText) findViewById(R.id.naissanceEdit);
        EditText pharmacie = (EditText) findViewById(R.id.pharmacieEdit);
        EditText medecin = (EditText) findViewById(R.id.medecinEdit);

        updateSexeValue();
        updateMedValue();
        updatePharmaValue();

        String prenom_ = DatabaseUser.getPrenom(userID);
        String nom_ = DatabaseUser.getNom(userID);
        String dateNaissance_ =  DatabaseUser.getDataNais(userID);
        String mail_ = DatabaseId.getMail(userID);

        sexe.setText(sexe_);
        nom.setText(nom_);
        prenom.setText(prenom_);
        mail.setText(mail_);
        dateNaissance.setText(dateNaissance_);
            pharmacie.setText(pharmacie_);
            medecin.setText(medecin_);

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
                    Toast.makeText(ProfilModifActivity.this, getResources().getString(R.string.cob), Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(mailText)) {
                    Toast.makeText(ProfilModifActivity.this, getResources().getString(R.string.format), Toast.LENGTH_SHORT).show();
                } else {
                    if (pharmacieText.isEmpty()) { pharmacieText = getResources().getString(R.string.nr); }
                    if (medecinText.isEmpty()) { medecinText = getResources().getString(R.string.nr); }
                    DatabaseUser.updateData(userID, sexeText, nomText, prenomText, dateNaissanceText, pharmacieText, medecinText);
                    DatabaseId.updateData(userID, mailText);
                    boolean insertionNotifReussie = notificationsDB.insertData((int) userID, getResources().getString(R.string.insertion_notif_edit2), getResources().getString(R.string.insertion_notif_edit), "profil");

                    Toast.makeText(ProfilModifActivity.this, getResources().getString(R.string.ienr), Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
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
                builder.setMessage(getResources().getString(R.string.back));
                builder.setPositiveButton(getResources().getString(R.string.Oui), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

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

    private boolean isValidEmail(String mail) {
        return !TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    private void updateSexeValue() {
        int userID = ((UserId) getApplication()).getUserID();
        sexe_ = DatabaseUser.getSexe(userID);
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

    private void updatePharmaValue() {
        int userID = ((UserId) getApplication()).getUserID();
        pharmacie_ = DatabaseUser.getPharmacie(userID);
        switch (pharmacie_) {
            case "Non renseigné":
                pharmacie_ = getString(R.string.nr);
                break;
            case "Not specified":
                pharmacie_ = getString(R.string.nr);
                break;
        }
    }

    private void updateMedValue() {
        int userID = ((UserId) getApplication()).getUserID();
        medecin_ = DatabaseUser.getMedecin(userID);
        switch (medecin_) {
            case "Non renseigné":
                medecin_ = getString(R.string.nr);
                break;
            case "Not specified":
                medecin_ = getString(R.string.nr);
                break;
        }
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}