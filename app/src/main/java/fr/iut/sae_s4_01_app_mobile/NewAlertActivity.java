package fr.iut.sae_s4_01_app_mobile;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class NewAlertActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createalert);

        ImageView sedeconnecterBtn = findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(NewAlertActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(NewAlertActivity.this, "Votre compte a bien été désactivé", Toast.LENGTH_SHORT).show();

            }

        });
    }


}