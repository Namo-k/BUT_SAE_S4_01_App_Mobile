package fr.iut.sae_s4_01_app_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

                } else if (item.getItemId() == R.id.nav_alerte) {
                    replaceFragment(new AncienneAlerteFragment());
                }

                return true;
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            String fragmentToOpen = intent.getStringExtra("fragment");
            String fragmentNew = intent.getStringExtra("NewAlerte");
            String fragmentAncienne= intent.getStringExtra("AncienneAlerte");


            if (fragmentToOpen != null && fragmentToOpen.equals("profil")) {
                replaceFragment(new ProfilFragment());
                bottomNavigationView.setSelectedItemId(R.id.nav_user);
            } else if (fragmentNew != null && fragmentNew.equals("alerte")) {
                replaceFragment(new AccueilFragment());
            }else if (fragmentAncienne != null && fragmentAncienne.equals("anciennealerte")) {
                replaceFragment(new AncienneAlerteFragment());
            }




            else{
                replaceFragment(new AccueilFragment());
            }
        } else {
            replaceFragment(new AccueilFragment());
        }
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
