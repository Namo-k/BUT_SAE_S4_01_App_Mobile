package fr.iut.sae_s4_01_app_mobile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Notification {

    private int id;
    private int idUser;
    private String title;
    private String notification;
    private String categorie;
    private String dateNotif;
    private static final Map<String, Integer> notificationImageMap = new HashMap<>();

    static {
        notificationImageMap.put("bienvenue", R.drawable.notif_bienvenue);
        notificationImageMap.put("mdp", R.drawable.notif_mdp);
        notificationImageMap.put("profil", R.drawable.notif_profil);
        notificationImageMap.put("avis", R.drawable.notif_avis);
        notificationImageMap.put("alerte", R.drawable.notification);
    }

    public Notification(int id, int idUser, String title, String notification, String categorie, String dateNotif) {
        this.id = id;
        this.idUser = idUser;
        this.title = title;
        this.notification = notification;
        this.categorie = categorie;
        this.dateNotif = dateNotif;
    }

    public int getId() {
        return id;
    }
    public int getIdUser() {
        return idUser;
    }

    public String getTitle() {
        return title;
    }

    public String getNotification() {
        return notification;
    }

    public String getCategorie() {
        return categorie;
    }

    public static int getImageResourceIdByName(String NotificationName) {
        Integer resourceId = notificationImageMap.get(NotificationName.toLowerCase());
        return resourceId != null ? resourceId : 0;
    }

    public String getDateNotif() {
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
            Date date = sourceFormat.parse(dateNotif);

            Locale targetLocale = Locale.getDefault();
            SimpleDateFormat targetFormat;

            if (Locale.getDefault().getLanguage().equals("fr")) {
                targetFormat = new SimpleDateFormat("d MMMM yyyy", Locale.FRENCH);
            } else {
                targetFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            }
            return targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
