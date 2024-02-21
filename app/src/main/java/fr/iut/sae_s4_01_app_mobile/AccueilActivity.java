package fr.iut.sae_s4_01_app_mobile;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Navbar code
        ImageView btnAncienne = (ImageView) findViewById(R.id.ancienneBtn);
        ImageView btnProfil = (ImageView) findViewById(R.id.userBtn);

        btnAncienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ancienneAlerteActivity.class);
                startActivity(intent);
            }
        });
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, ProfilActivity.class);
                startActivity(intent);
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

        // Animation des 2 colombes
        ImageView colombeDroiteImage = (ImageView) findViewById(R.id.colombes1);
        ImageView colombeGaucheImage = (ImageView) findViewById(R.id.colombes2);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(colombeDroiteImage, "translationY", 0f, -25f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(colombeGaucheImage, "translationY", 0f, -25f);
        translationY.setRepeatCount(ObjectAnimator.INFINITE); // Répéter l'animation indéfiniment
        translationY.setRepeatMode(ObjectAnimator.REVERSE); // Inverser l'animation pour créer l'effet de retour
        translationX.setRepeatCount(ObjectAnimator.INFINITE); // Répéter l'animation indéfiniment
        translationX.setRepeatMode(ObjectAnimator.REVERSE); // Inverser l'animation pour créer l'effet de retour
        translationY.setDuration(1000);
        translationX.setDuration(1000);
        translationY.setInterpolator(new LinearInterpolator());
        translationX.setInterpolator(new LinearInterpolator());
        translationY.start();
        translationX.start();
    }
}