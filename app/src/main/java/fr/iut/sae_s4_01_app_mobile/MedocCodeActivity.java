package fr.iut.sae_s4_01_app_mobile;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MedocCodeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medoc_code);

        TextView msgTV = (TextView)findViewById(R.id.codecipTV);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String codeCIP = bundle.getString("codeCIP");

        String message = "Le code CIP : " + codeCIP;

        msgTV.setText(message);

    }


}