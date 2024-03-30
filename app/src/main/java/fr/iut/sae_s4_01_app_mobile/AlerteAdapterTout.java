package fr.iut.sae_s4_01_app_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.iut.sae_s4_01_app_mobile.bd.Users;

public class AlerteAdapterTout extends ArrayAdapter<Alerte> {

    private final LayoutInflater inflater;
    private final Context context;

    public AlerteAdapterTout(Context context, List<Alerte> alertes) {
        super(context, 0, alertes);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.item_tout_alerte, parent, false);
        }

        Alerte currentAlerte = getItem(position);

        TextView personneTV = itemView.findViewById(R.id.personneTV);
        personneTV.setText(getFullName(currentAlerte.getIdUser()));

        TextView medicamentTV = itemView.findViewById(R.id.medicamentTV);
        medicamentTV.setText(currentAlerte.getMedicament().toUpperCase());

        TextView raisonTV = itemView.findViewById(R.id.raisonTV);
        raisonTV.setText(currentAlerte.getRaison());

        TextView messageTV = itemView.findViewById(R.id.messageTV);
        messageTV.setText(currentAlerte.getMessage());

        TextView dataTV = itemView.findViewById(R.id.dataTV);
        dataTV.setText(currentAlerte.getDateAlerte());

        return itemView;
    }

    // Méthode pour obtenir le nom complet de l'utilisateur à partir de son ID
    private String getFullName(int userId) {
        Users users = new Users(context);
        String nom = users.getNom(userId);
        String prenom = users.getPrenom(userId);
        return prenom + " " + nom.toUpperCase();
    }
}
