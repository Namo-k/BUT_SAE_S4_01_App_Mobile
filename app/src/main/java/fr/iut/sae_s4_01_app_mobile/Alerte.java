package fr.iut.sae_s4_01_app_mobile;

public class Alerte {

    private int id;
    private String medicament;
    private String raison;
    private String message;

    private String date;

    public Alerte(int id, String m, String r, String msg, String date){
        this.id = id;
        this.medicament = m;
        this.raison = r;
        this.message = msg;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getMedicament() {
        return medicament;
    }

    public String getMessage() {
        return message;
    }

    public String getRaison() {
        return raison;
    }

    public String getDate() {
        return date;
    }
}
