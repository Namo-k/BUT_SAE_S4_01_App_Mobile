package fr.iut.sae_s4_01_app_mobile;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.Random;

import fr.iut.sae_s4_01_app_mobile.bd.Medicament;

public class SplashScreenActivity extends AppCompatActivity {



    private final static int TEMPS = 4500;
    private String[] tabQuote;
    private TextView citation;
    private ImageView colombeDroiteImage;
    private ImageView colombeGaucheImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String Quote1 = getResources().getString(R.string.quote1);
        String Quote2 = getResources().getString(R.string.quote2);
        String Quote3 = getResources().getString(R.string.quote3);
        String Quote4 = getResources().getString(R.string.quote4);
        String Quote5 = getResources().getString(R.string.quote5);
        tabQuote = new String[]{Quote1, Quote2, Quote3, Quote4, Quote5};


        citation = findViewById(R.id.citationText);
        Random random = new Random();
        int nombreAleatoire = random.nextInt(5);
        citation.setText(tabQuote[nombreAleatoire]);

        colombeDroiteImage = findViewById(R.id.colombeDroiteImage);
        colombeGaucheImage = findViewById(R.id.colombeGaucheImage);

        // Créez un ObjectAnimator pour la translation en Y (de haut en bas)
        ObjectAnimator translationY = ObjectAnimator.ofFloat(colombeDroiteImage, "translationY", 0f, 50f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(colombeGaucheImage, "translationY", 0f, 50f);
        translationY.setRepeatCount(ObjectAnimator.INFINITE); // Répéter l'animation indéfiniment
        translationY.setRepeatMode(ObjectAnimator.REVERSE); // Inverser l'animation pour créer l'effet de retour
        translationX.setRepeatCount(ObjectAnimator.INFINITE); // Répéter l'animation indéfiniment
        translationX.setRepeatMode(ObjectAnimator.REVERSE); // Inverser l'animation pour créer l'effet de retour

        // Définissez la durée de l'animation en millisecondes
        translationY.setDuration(1000);
        translationX.setDuration(1000);

        // Ajoutez un interpolateur pour rendre l'animation plus fluide (facultatif)
        translationY.setInterpolator(new LinearInterpolator());
        translationX.setInterpolator(new LinearInterpolator());

        // Lancez l'animation
        translationY.start();
        translationX.start();

        //redigirer la page aprés 3.5 secondes



        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                loadMedicamentData();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        new Handler().postDelayed(runnable,TEMPS);


    }
    private void loadMedicamentData() {
        Medicament medicament = new Medicament(this);
        SQLiteDatabase database = medicament.getWritableDatabase();
        InputStream inputStream = getResources().openRawResource(R.raw.medicament);
        medicament.importCSVToDatabase(inputStream);
    }



}