package fr.iut.sae_s4_01_app_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;

public class ResetCodeActivity extends AppCompatActivity {
    private Identifiants DatabaseIdentifiant;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_code);

        DatabaseIdentifiant = new Identifiants(ResetCodeActivity.this);


        EditText resetCodeInput = findViewById(R.id.resetCodeInput);
        Button reset = findViewById(R.id.reset);

        Intent intent = getIntent();

        if (intent != null) {
             email = intent.getStringExtra("email");
        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = resetCodeInput.getText().toString();
                checkResetCode(email, code);
            }
        } );
    }
    public void checkResetCode(String email, String code) {
        int resultCode = DatabaseIdentifiant.checkResetCodeValidity(email, code);
        switch (resultCode) {
            case Identifiants.RESET_CODE_VALID:
                Intent intent = new Intent(ResetCodeActivity.this, ChangePasswordActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                break;
            case Identifiants.RESET_CODE_INVALID:
                Toast.makeText(ResetCodeActivity.this, "Le code est erroné", Toast.LENGTH_SHORT).show();
                break;
            case Identifiants.RESET_CODE_EXPIRED:
                Toast.makeText(ResetCodeActivity.this, "Le code a expiré", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }




}