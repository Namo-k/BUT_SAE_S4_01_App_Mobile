package fr.iut.sae_s4_01_app_mobile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import fr.iut.sae_s4_01_app_mobile.AccueilFragment;
import fr.iut.sae_s4_01_app_mobile.LoginActivity;
import fr.iut.sae_s4_01_app_mobile.R;



public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    replaceFragment(new AccueilFragment());

                } else if (item.getItemId() == R.id.nav_user) {
                    replaceFragment(new ProfilFragment());

                }else if (item.getItemId() == R.id.nav_alerte) {
                    replaceFragment(new AncienneAlerteFragment());
                }

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
