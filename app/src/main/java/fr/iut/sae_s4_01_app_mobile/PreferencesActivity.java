package fr.iut.sae_s4_01_app_mobile;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class PreferencesActivity extends AppCompatActivity {
    ImageView imageView;
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);

        imageView = findViewById(R.id.imageView);
        switchCompat = findViewById(R.id.switchCompat);
        sharedPreferences = getSharedPreferences("night", 0);

        boolean isNightMode = sharedPreferences.getBoolean("night_mode", true);
        setNightMode(isNightMode);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setNightMode(isChecked);
            }
        });
    }

    private void setNightMode(boolean isNightMode) {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            imageView.setImageResource(R.drawable.thumbtrue);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            imageView.setImageResource(R.drawable.thumb);
        }

        switchCompat.setChecked(isNightMode);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("night_mode", isNightMode);
        editor.apply();
    }
}
