package fr.iut.sae_s4_01_app_mobile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Alerte {
    private int id;
    private int codeCis;
    private int idUser;
    private String nomMedicament;
    private String raison;
    private String message;
    private String dateAlerte; // Ajout de la date d'alerte


    public Alerte(int id, int codeCis, int idUser, String nomMedicament, String raison, String message, String dateAlerte) {
        this.id = id;
        this.codeCis = codeCis;
        this.idUser = idUser;
        this.nomMedicament = nomMedicament;
        this.raison = raison;
        this.message = message;
        this.dateAlerte = dateAlerte;
    }


    public int getId() { return id; }
    public int getCodeCis() { return codeCis; }
    public int getIdUser() { return idUser; }
    public String getMedicament() { return nomMedicament; } // Correction du nom de la méthode
    public String getRaison() { return raison; }
    public String getMessage() { return message; }
    public String getDateAlerte() {
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
            Date date = sourceFormat.parse(dateAlerte);
            SimpleDateFormat targetFormat = new SimpleDateFormat("d MMMM yyyy", Locale.FRENCH);
            return targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
