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

        String message = "Le code CIP : " + codeCIP;
        String raison = "Raison de l'alerte";
        String messageAlerte = "Message d'alerte";


        msgTV.setText(message);


        UserId myApp = (UserId) getApplication();
        int userID = myApp.getUserID();
        if (userID != -1) {
            // Insère l'alerte dans la base de données
            boolean insertionReussie = alertesDB.insertData(userID, Integer.parseInt(codeCIP), raison, messageAlerte);

            if (insertionReussie) {
                Toast.makeText(MedocCodeActivity.this, "Alerte insérée avec succès!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MedocCodeActivity.this, "Erreur lors de l'insertion de l'alerte.", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(MedocCodeActivity.this, "Erreur: ID de l'utilisateur non disponible.", Toast.LENGTH_SHORT).show();
        }

        boolean medicamentExists = getIntent().getBooleanExtra("medicamentExists", false);
        codeCIP = getIntent().getStringExtra("codeCIP");
        if (!medicamentExists) {
            Toast.makeText(this, "Aucun médicament trouvé pour ce numéro CIS.", Toast.LENGTH_SHORT).show();

        }
    }
}