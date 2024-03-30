package fr.iut.sae_s4_01_app_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AlerteAdapter extends ArrayAdapter<Alerte> {

    private final LayoutInflater inflater;
    private final Context context;

    public AlerteAdapter(Context context, List<Alerte> alertes) {
        super(context, 0, alertes);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.item_alerte, parent, false);
        }

        Alerte currentAlerte = getItem(position);

        TextView medicamentTV = itemView.findViewById(R.id.medicamentTV);
        medicamentTV.setText(currentAlerte.getMedicament().toUpperCase());

        TextView raisonTV = itemView.findViewById(R.id.raisonTV);
        raisonTV.setText(currentAlerte.getRaison());

        TextView messageTV = itemView.findViewById(R.id.messageTV);
        messageTV.setText(currentAlerte.getMessage());

        TextView dateTV = itemView.findViewById(R.id.dateAlerte);
        String formattedDate = currentAlerte.getDateAlerte(); // Obtenez la date formatée à partir de getDateAlerte()
        dateTV.setText(formattedDate); // Définissez le texte du TextView avec la date formatée

        return itemView;
    }

}
