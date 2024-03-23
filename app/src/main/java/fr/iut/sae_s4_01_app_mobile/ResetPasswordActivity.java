package fr.iut.sae_s4_01_app_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;

public class ResetPasswordActivity extends AppCompatActivity {
    private Identifiants DatabaseIdentifiant;


    private EditText emailEditText;
    private Button confirmButton;
    String emailStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailEditText = findViewById(R.id.usernameET);
        confirmButton = findViewById(R.id.loginBtn);



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailStr = emailEditText.getText().toString().trim();

                if (!emailStr.isEmpty()) {
                    if(!isValidEmail(emailStr)){
                        Toast.makeText(ResetPasswordActivity.this, "Le format de l'email n'est pas correct", Toast.LENGTH_SHORT).show();
                    }else {
                        DatabaseIdentifiant = new Identifiants(ResetPasswordActivity.this);
                        if(DatabaseIdentifiant.checkEmail(emailStr)){
                            DatabaseIdentifiant.updateResetCode(emailStr);
                            sendResetEmail();
                            finish();
                            Intent intent = new Intent(ResetPasswordActivity.this, ResetCodeActivity.class);
                            intent.putExtra("email", emailStr);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "L'email qui a été saisie est associé à aucun compte", Toast.LENGTH_LONG).show();
                        }
                    }

                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Veuillez saisir une adresse e-mail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendResetEmail() {

        String code = DatabaseIdentifiant.getCode(emailStr);
        String date = DatabaseIdentifiant.getDateCode(emailStr);


        String url = "http://10.0.2.2/colombes/code_password.php?email=" + emailStr + "&code=" + code;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Toast.makeText(getApplicationContext(), "OOOUUUIII", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erreur) {
                Toast.makeText(getApplicationContext(), erreur.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }


    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}