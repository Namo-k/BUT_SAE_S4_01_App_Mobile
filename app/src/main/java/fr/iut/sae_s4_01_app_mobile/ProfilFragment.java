package fr.iut.sae_s4_01_app_mobile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import fr.iut.sae_s4_01_app_mobile.bd.Identifiants;
import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class ProfilFragment extends Fragment {

    private Users DatabaseUser;
    private Identifiants DatabaseId;
    private String sexe_;


    TextView sexe;
    TextView nom;
    TextView prenom;
    TextView mail;
    TextView dateNaissance;
    TextView pharmacie;
    TextView medecin;
    String pharmacie_;
    String medecin_;

    private static final int REQUEST_CODE_PROFIL_MODIFICATION = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment`
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        DatabaseUser = new Users(requireContext());
        DatabaseId = new Identifiants(requireContext());

         sexe = view.findViewById(R.id.sexeLabelTV);
         nom = view.findViewById(R.id.nomLabelTV);
         prenom = view.findViewById(R.id.prenomLabelTV);
         mail = view.findViewById(R.id.mailLabelTV);
         dateNaissance = view.findViewById(R.id.naissanceLabelTV);
         pharmacie = view.findViewById(R.id.pharmacieLabelTV);
         medecin = view.findViewById(R.id.medecinLabelTV);

        UserId myApp = (UserId) requireActivity().getApplication();
        int userID = myApp.getUserID();

        sexe_ = DatabaseUser.getSexe(userID);
        updateSexeValue();

        String prenom_ = DatabaseUser.getPrenom(userID);
        String nom_ = DatabaseUser.getNom(userID);
        String dateNaissance_ = DatabaseUser.getDataNais(userID);
        String mail_ = DatabaseId.getMail(userID);
        pharmacie_ = DatabaseUser.getPharmacie(userID);
        updatePharmaValue();
        medecin_ = DatabaseUser.getMedecin(userID);
        updateMedValue();

        sexe.setText(sexe_);
        nom.setText(nom_);
        prenom.setText(prenom_);
        mail.setText(mail_);
        dateNaissance.setText(dateNaissance_);
        pharmacie.setText(pharmacie_);
        medecin.setText(medecin_);


        ImageView btnNotif = view.findViewById(R.id.notifBtn);

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        ImageView sedeconnecterBtn = view.findViewById(R.id.sedeconnecterBtn);

        sedeconnecterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // bouton modifier
        TextView modifier = view.findViewById(R.id.btnModifier);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ProfilModifActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PROFIL_MODIFICATION);
            }
        });

        // bouton suppCompte
        CardView supprimer = view.findViewById(R.id.btnSupprimerCompte);

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(userID);
            }
        });

        TextView credits = view.findViewById(R.id.credits);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialogue_apropos, null);
                builder.setView(dialogView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when OK button is clicked
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Affichage des informations lors du click sur point interrogation
        CardView interrogation1 = view.findViewById(R.id.interrogation1);
        CardView interrogation2 = view.findViewById(R.id.interrogation2);
        CardView interrogation1text = view.findViewById(R.id.interrogation1text);
        CardView interrogation2text = view.findViewById(R.id.interrogation2text);
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


        return view;
    }

    private void updateSexeValue() {
        int userID = ((UserId) requireActivity().getApplication()).getUserID();
        sexe_ = DatabaseUser.getSexe(userID);
        switch (sexe_) {
            case "Homme":
            case "Male":
                sexe_ = getString(R.string.homme);
                break;
            case "Femme":
            case "Female":
                sexe_ = getString(R.string.femme);
                break;
            case "Non renseigné":
            case "Not specified":
                sexe_ = getString(R.string.nr);
                break;
        }
    }

    private void updatePharmaValue() {
        int userID = ((UserId) requireActivity().getApplication()).getUserID();
        pharmacie_ = DatabaseUser.getPharmacie(userID);
        if (pharmacie_.equals("Non renseigné") || pharmacie_.equals("Not specified")) pharmacie_ = getResources().getString(R.string.nr);
    }

    private void updateMedValue() {
        int userID = ((UserId) requireActivity().getApplication()).getUserID();
        medecin_ = DatabaseUser.getMedecin(userID);
        if (medecin_.equals("Non renseigné") || medecin_.equals("Not specified")) medecin_ = getResources().getString(R.string.nr);
    }

    private void showConfirmationDialog(int userID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Êtes vous certain de vouloir supprimer votre compte ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(requireContext(), "Votre compte a bien été supprimé", Toast.LENGTH_SHORT).show();
                DatabaseUser.deleteUserData(userID);
                DatabaseId.deleteUserData(userID);
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Dans MainActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PROFIL_MODIFICATION && resultCode == Activity.RESULT_OK) {
            // Rafraîchir les données du fragment ProfilFragment
            Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.container);
            if (fragment instanceof ProfilFragment) {
                ((ProfilFragment) fragment).refreshData(); // Appeler la méthode de rafraîchissement dans ProfilFragment
            }
        }
    }
    public void refreshData() {
        // Mettez à jour les données affichées dans le fragment ici
        UserId myApp = (UserId) requireActivity().getApplication();
        int userID = myApp.getUserID();

        // Mettez à jour les TextViews avec les nouvelles données
        String prenom_ = DatabaseUser.getPrenom(userID);
        String nom_ = DatabaseUser.getNom(userID);
        String dateNaissance_ = DatabaseUser.getDataNais(userID);
        String mail_ = DatabaseId.getMail(userID);
        String pharmacie_ = DatabaseUser.getPharmacie(userID);
        String medecin_ = DatabaseUser.getMedecin(userID);

        sexe_ = DatabaseUser.getSexe(userID);
        updateSexeValue();

        sexe.setText(sexe_);
        nom.setText(nom_);
        prenom.setText(prenom_);
        mail.setText(mail_);
        dateNaissance.setText(dateNaissance_);
        pharmacie.setText(pharmacie_);
        medecin.setText(medecin_);

        // Assurez-vous d'appeler updateSexeValue() si nécessaire
        // Si d'autres vues doivent être mises à jour, assurez-vous de le faire ici
    }

}
