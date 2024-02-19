package fr.iut.sae_s4_01_app_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import java.util.Random;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int TEMPS = 3500;
    private String Quote1 = "Savez-vous que 10 minutes de méditation peut réduire l’anxiété et booster votre confiance ?";
    private String Quote2 = "Savez-vous que consommer des aliments riches en fibres peut favoriser une meilleure digestion ?";
    private String Quote3 = "Savez-vous que consommer 5 fruits et légumes par jour renforce votre système immunitaire ?";
    private String Quote4 = "Savez-vous que dormir 7 à 8 heures par nuit peut favoriser une meilleure récupération musculaire ?";
    private String Quote5 = "Savez-vous que marcher 10 000 pas par jour peut  améliorer votre santé cardiovasculaire ?";
    private String[] tabQuote = {Quote1, Quote2, Quote3, Quote4, Quote5};
    private TextView citation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        citation = findViewById(R.id.citationText);
        Random random = new Random();
        int nombreAleatoire = random.nextInt(5);
        citation.setText(tabQuote[nombreAleatoire]);

        //redigirer la page aprés 3.5 secondes
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        new Handler().postDelayed(runnable,TEMPS);
    }
}