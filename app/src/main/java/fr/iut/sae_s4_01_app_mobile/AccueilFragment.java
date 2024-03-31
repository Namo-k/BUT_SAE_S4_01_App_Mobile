package fr.iut.sae_s4_01_app_mobile;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.iut.sae_s4_01_app_mobile.bd.Alertes;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class AccueilFragment extends Fragment {

    private Users DatabaseUser;
    private Context context;
    private boolean isFrench;
    private ImageView btnFrancais, btnAnglais;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_accueil, container, false);

        DatabaseUser = new Users(getContext());
        context = getContext();

        UserId myApp = (UserId) getActivity().getApplication();
        int userID = myApp.getUserID();
        ImageView btnSaisie = rootView.findViewById(R.id.saisie_logo);

        ImageView btnNotif = rootView.findViewById(R.id.notifBtn);
        ImageView btnNuit = rootView.findViewById(R.id.nuitbtn);
        TextView btnAlertes = rootView.findViewById(R.id.btnAlertes);
        TextView btnPrenom = rootView.findViewById(R.id.prenom);

        String prenom = DatabaseUser.getPrenom(userID);
        btnPrenom.append(prenom);

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        btnSaisie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CipManuelleActivity.class);
                startActivity(intent);
            }
        });

        btnNuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PreferencesActivity.class);
                startActivity(intent);
            }
        });
        btnAlertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AncienneAlerteFragment AncienneAlerteFragment = new AncienneAlerteFragment();
                replaceFragment(AncienneAlerteFragment);
            }
        });
        btnPrenom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilFragment ProfilFragment = new ProfilFragment();
                replaceFragment(ProfilFragment);
            }
        });

        // Affichage des informations lors du clic sur le point d'interrogation
        CardView interrogation1 = rootView.findViewById(R.id.interrogation1);
        CardView interrogation2 = rootView.findViewById(R.id.interrogation2);
        CardView interrogation1text = rootView.findViewById(R.id.interrogation1text);
        CardView interrogation2text = rootView.findViewById(R.id.interrogation2text);
        interrogation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(interrogation1text);
            }
        });

        interrogation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(interrogation2text);
            }
        });

        // Animation des 2 colombes
        ImageView colombeDroiteImage = rootView.findViewById(R.id.colombes1);
        ImageView colombeGaucheImage = rootView.findViewById(R.id.colombes2);
        animateColombe(colombeDroiteImage);
        animateColombe(colombeGaucheImage);

        ImageView sedeconnecterBtn = rootView.findViewById(R.id.sedeconnecterBtn);
        sedeconnecterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        CardView blocAncienneAlerte = rootView.findViewById(R.id.blocAncienneAlerte);
        blocAncienneAlerte.setVisibility(View.INVISIBLE);

        Alertes alertesDb = new Alertes(getContext());

        List<Alerte> alertes = alertesDb.getAlertesDESC(userID,getContext());
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            blocAncienneAlerte.setVisibility(View.VISIBLE);
            alerteLaPlusRecente = alertes.get(0);
        }

        if (alerteLaPlusRecente != null) {
            ((TextView) rootView.findViewById(R.id.medicamentTV)).setText(alerteLaPlusRecente.getMedicament());
            ((TextView) rootView.findViewById(R.id.raisonTV)).setText(alerteLaPlusRecente.getRaison());
            ((TextView) rootView.findViewById(R.id.messageTV)).setText(alerteLaPlusRecente.getMessage());
            ((TextView) rootView.findViewById(R.id.dataTV)).setText(alerteLaPlusRecente.getDateAlerte());
        }

        btnFrancais = rootView.findViewById(R.id.francais);
        btnAnglais = rootView.findViewById(R.id.anglais);

        String currentLanguage = Locale.getDefault().getLanguage();
        isFrench = currentLanguage.equals("fr");

        updateButtonVisibility(isFrench);
        btnFrancais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                AccueilFragment AccueilFragment = new AccueilFragment();
                replaceFragment(AccueilFragment);
            }
        });

        btnAnglais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("fr");
                AccueilFragment AccueilFragment = new AccueilFragment();
                replaceFragment(AccueilFragment);

            }
        });

        return rootView;
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    private void updateButtonVisibility(boolean isFrench) {
        if (isFrench) {
            btnFrancais.setVisibility(View.VISIBLE);
            btnAnglais.setVisibility(View.GONE);
        } else {
            btnFrancais.setVisibility(View.GONE);
            btnAnglais.setVisibility(View.VISIBLE);
        }
    }

    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void animateColombe(ImageView imageView) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", 0f, -25f);
        translationY.setRepeatCount(ObjectAnimator.INFINITE);
        translationY.setRepeatMode(ObjectAnimator.REVERSE);
        translationY.setDuration(1000);
        translationY.setInterpolator(new LinearInterpolator());
        translationY.start();
    }

    private void updateDateTextView(Alerte alerte) {
        String dateAlerte = alerte.getDateAlerte();

        Log.d("Date avant formatage", dateAlerte);

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sourceFormat.parse(dateAlerte);

            // Choix du format de date en fonction de la langue par défaut
            SimpleDateFormat targetFormat;
            if (Locale.getDefault().getLanguage().equals("fr")) {
                targetFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
            } else {
                targetFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
            }

            // Formatage de la date dans le format cible
            String formattedDate = targetFormat.format(date);

            Log.d("Date après formatage", formattedDate);

            // Affichage de la date formatée dans le TextView
            ((TextView) rootView.findViewById(R.id.dataTV)).setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Appelez la méthode de mise à jour lorsque l'activité reprend
        UserId myApp = (UserId) getActivity().getApplication();
        int userID = myApp.getUserID();
        Alertes alertesDb = new Alertes(getActivity());
        List<Alerte> alertes = alertesDb.getAlertesDESC(userID,getContext());
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            alerteLaPlusRecente = alertes.get(0);
        }

        if (alerteLaPlusRecente != null) {
            updateDateTextView(alerteLaPlusRecente);
        }
    }

    // Méthode appelée lorsque la langue est modifiée
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Appelez la méthode de mise à jour lorsque la langue est modifiée
        UserId myApp = (UserId) getContext();
        int userID = myApp.getUserID();
        Alertes alertesDb = new Alertes(getContext());
        List<Alerte> alertes = alertesDb.getAlertesDESC(userID,getContext());
        Alerte alerteLaPlusRecente = null;
        if (!alertes.isEmpty()) {
            alerteLaPlusRecente = alertes.get(0);
        }

        if (alerteLaPlusRecente != null) {
            updateDateTextView(alerteLaPlusRecente);
        }
    }

    /*private void applyNightMode() {
        setTheme(R.style.Base_Theme_SAE_S4_01_App_Mobile);
    }*/

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
