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
    public AlerteAdapterTout(Context context, List<Alerte> alertes) {
        super(context, 0, alertes);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        boolean admin = true;
        if (itemView == null && admin==false) {
            itemView = inflater.inflate(R.layout.item_alerte, parent, false);
        }else if(itemView == null && admin){
            itemView = inflater.inflate(R.layout.item_tout_alerte, parent, false);
        }

        Users users = new Users(this.getContext());

        Alerte currentAlerte = getItem(position);

        String nom = users.getNom(currentAlerte.getIdUser());
        String prenom = users.getPrenom(currentAlerte.getIdUser());

        TextView personneTV = itemView.findViewById(R.id.personneTV);
        personneTV.setText(prenom + " " + nom.toUpperCase());

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

}
