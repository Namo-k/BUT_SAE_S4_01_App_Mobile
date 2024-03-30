package fr.iut.sae_s4_01_app_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;

public class MedocCodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medoc_code);

        Alertes alertesDB = new Alertes(this);

        TextView msgTV = findViewById(R.id.codecipTV);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String codeCIP = bundle.getString("codeCIP");

        // Construction du message à afficher
        String message = getString(R.string.cip_code) + codeCIP;
        String reason = getString(R.string.alert_reason);
        String alertMessage = getString(R.string.alert_message);

// Affichage du message
        msgTV.setText(message);

// Récupération de l'ID de l'utilisateur
        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();

// Vérification de l'ID de l'utilisateur
        if (userID != -1) {
            // Insertion de l'alerte dans la base de données
            boolean insertionReussie = alertesDB.insertData(userID, Integer.parseInt(codeCIP), reason, alertMessage);

            // Affichage d'un message de succès ou d'erreur
            if (insertionReussie) {
                Toast.makeText(MedocCodeActivity.this, R.string.alert_insert_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MedocCodeActivity.this, R.string.alert_insert_error, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Affichage d'une erreur si l'ID de l'utilisateur n'est pas disponible
            Toast.makeText(MedocCodeActivity.this, R.string.user_id_error, Toast.LENGTH_SHORT).show();
        }

// Vérification de l'existence du médicament
        boolean medicamentExists = getIntent().getBooleanExtra("medicamentExists", false);
        codeCIP = getIntent().getStringExtra("codeCIP");
        if (!medicamentExists) {
            Toast.makeText(this, R.string.no_medicament_found, Toast.LENGTH_SHORT).show();
        }


    }
    }
