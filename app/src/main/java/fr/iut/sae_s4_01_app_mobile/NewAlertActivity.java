package fr.iut.sae_s4_01_app_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;
import fr.iut.sae_s4_01_app_mobile.bd.Medicament;
import fr.iut.sae_s4_01_app_mobile.bd.Notifications;

public class NewAlertActivity extends AppCompatActivity {
    private Medicament medicamentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createalert);

        Alertes alertesDB = new Alertes(this);
        Notifications notificationsDB = new Notifications(this);

        medicamentDatabase = new Medicament(this);

        String codeCIP = getCodeCIP();
        String moyen = getMoyen();
        String nomMedoc =  medicamentDatabase.getNomMedicamentByCodeCIP(codeCIP);

        TextView codeCipEditText = findViewById(R.id.inputCip);
        codeCipEditText.setText(codeCIP);
        TextView nomMedocTextView = findViewById(R.id.inputMedoc);
        nomMedocTextView.setText(nomMedoc);

        Spinner spinnerRaison = findViewById(R.id.spinnerRaison);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        codeCIP = bundle.getString("codeCIP");

        List<String> spinnerList = new ArrayList<>();
        spinnerList.add(getString(R.string.select_reason));
        spinnerList.add(getString(R.string.renewal_impossible));
        spinnerList.add(getString(R.string.stock_unavailable));
        spinnerList.add(getString(R.string.stock_insuffisant));
        spinnerList.add(getString(R.string.late_delivery));
        spinnerList.add(getString(R.string.other));
        spinnerRaison.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList));

        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);

        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(NewAlertActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        CardView valider = findViewById(R.id.btnEnregistrer);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView codeCipTextView = findViewById(R.id.inputCip);
                String codeCIP = codeCipTextView.getText().toString();

                String raison = spinnerRaison.getSelectedItem().toString();

                EditText messageTextView = findViewById(R.id.inputMessage);
                String messageAlerte = messageTextView.getText().toString();

                if (messageAlerte.isEmpty()) {
                    messageAlerte = getResources().getString(R.string.nomsg);
                }
                UserId myApp = (UserId) getApplication();
                int userID = myApp.getUserID();

                if (userID != -1 && codeCIP != null && !raison.equals(getString(R.string.select_reason))) {
                    boolean insertionReussie = alertesDB.insertData(userID, codeCIP, raison, messageAlerte, moyen);
                    boolean insertionNotifReussie = notificationsDB.insertData((int) userID, getResources().getString(R.string.insertion_notif_reussie2), getResources().getString(R.string.insertion_notif_reussie), "alerte");

                    if (notificationsDB.getNombreTotalAlertesNotif(userID) == 3) {
                        boolean insertionNotifReussie2 = notificationsDB.insertData((int) userID, getResources().getString(R.string.insertion_notif_avis2), getResources().getString(R.string.insertion_notif_avis), "avis");
                    }

                    if (insertionReussie && insertionNotifReussie) {
                        Toast.makeText(NewAlertActivity.this, getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
                        long idAlerte = alertesDB.getLastInsertedAlertId();

                        //Toast.makeText(NewAlertActivity.this, "ID de l'alerte créée : " + String.valueOf(idAlerte), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(NewAlertActivity.this, MainActivity.class);
                        intent.putExtra("NewAlerte", "alerte");
                        startActivity(intent);



                        CheckBox checkbox = findViewById(R.id.checkbox);
                        if (checkbox.isChecked()) {
                            alertesDB.marquerImportanceTrue(idAlerte);
                        }
                        finish();
                    } else {
                        Toast.makeText(NewAlertActivity.this, getString(R.string.insert_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewAlertActivity.this, getString(R.string.valid_reason), Toast.LENGTH_SHORT).show();
                }
            }
        });

        CardView annuler = findViewById(R.id.btnAnnuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewAlertActivity.this);
                builder.setMessage(getString(R.string.cancel_confirmation));
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

    private String getCodeCIP() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle.getString("codeCIP");
        }
        return null;
    }
    private String getMoyen() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            return bundle.getString("moyen");
        }
        return null;

    }


}
