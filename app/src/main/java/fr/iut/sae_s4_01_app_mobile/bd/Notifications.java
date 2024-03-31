package fr.iut.sae_s4_01_app_mobile.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.iut.sae_s4_01_app_mobile.Notification;
import fr.iut.sae_s4_01_app_mobile.R;

public class Notifications extends SQLiteOpenHelper {
    private static final String databaseName = "db_notifications";
    private static final int version = 1;
    public Notifications(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notifications (id integer PRIMARY KEY, idUser integer, title VARCHAR(80), notification VARCHAR(80), categorie VARCHAR(80), dateNotif DATETIME DEFAULT (datetime('now')), FOREIGN KEY (idUser) REFERENCES users(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notifications ");
        onCreate(db);
    }

    public Boolean insertData(int idUser, String title, String notification, String categorie) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();

        contentval.put("idUser", idUser);
        contentval.put("title", title);
        contentval.put("notification", notification);
        contentval.put("categorie", categorie);
        contentval.put("dateNotif", getCurrentDateTime());

        long result = MyDatabase.insert("notifications", null, contentval);

        return result != -1;
    }

    public int getNombreTotalAlertes(int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM notifications where idUser = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[] {String.valueOf(idUser)});
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public List<Notification> getAllNotifications(int idUser, Context context) {
        List<Notification> listeNotifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notifications where idUser = ? ORDER BY id DESC", new String[] {String.valueOf(idUser)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String notification = cursor.getString(cursor.getColumnIndex("notification"));
                @SuppressLint("Range") String categorie = cursor.getString(cursor.getColumnIndex("categorie"));
                @SuppressLint("Range") String dateNotif = cursor.getString(cursor.getColumnIndex("dateNotif"));

                String newTitle;
                switch (title) {
                    case "Enregistrement d`une alerte":
                        newTitle = context.getResources().getString(R.string.insertion_notif_reussie2);
                        break;
                    case "Enregistrement d'une alerte":
                        newTitle = context.getResources().getString(R.string.insertion_notif_reussie2);
                        break;
                    case "Recording an alert":
                        newTitle = context.getResources().getString(R.string.insertion_notif_reussie2);
                        break;
                    case "Votre avis compte":
                        newTitle = context.getResources().getString(R.string.insertion_notif_avis2);
                        break;
                    case "Your opinion matters":
                        newTitle = context.getResources().getString(R.string.insertion_notif_avis2);
                        break;
                    case "Action sur votre profil":
                        newTitle = context.getResources().getString(R.string.insertion_notif_edit2);
                        break;
                    case "Action on your profile":
                        newTitle = context.getResources().getString(R.string.insertion_notif_edit2);
                        break;
                    case "Bienvenue !":
                        newTitle = context.getResources().getString(R.string.insertion_notif_beinvenu2);
                        break;
                    case "Welcome!":
                        newTitle = context.getResources().getString(R.string.insertion_notif_beinvenu2);
                        break;
                    case "Sécurité de votre compte":
                        newTitle = context.getResources().getString(R.string.insertion_notif_sec2);
                        break;
                    case "Account security":
                        newTitle = context.getResources().getString(R.string.insertion_notif_sec2);
                        break;
                    default:
                        newTitle = title;
                        break;
                }

                String newNotif;
                switch (notification) {
                    case "Votre alerte a bien été enregistrée. Merci pour votre contribution !":
                        newNotif = context.getResources().getString(R.string.insertion_notif_reussie);
                        break;
                    case "Your alert has been successfully recorded. Thank you for your contribution!":
                        newNotif = context.getResources().getString(R.string.insertion_notif_reussie);
                        break;
                    case "Nous aimerions avoir votre avis. Complétez ce sondage de Colombes !":
                        newNotif = context.getResources().getString(R.string.insertion_notif_avis);
                        break;
                    case "We would like to hear your opinion. Complete this Colombes survey!":
                        newNotif = context.getResources().getString(R.string.insertion_notif_avis);
                        break;
                    case "Votre avez actualisé les informations de votre profil.":
                        newNotif = context.getResources().getString(R.string.insertion_notif_edit);
                        break;
                    case "You have updated your profile information.":
                        newNotif = context.getResources().getString(R.string.insertion_notif_edit);
                        break;
                    case "Nous sommes ravis de vous accueillir. Complétez votre profil !":
                        newNotif = context.getResources().getString(R.string.insertion_notif_beinvenu);
                        break;
                    case "We are delighted to welcome you. Complete your profile!":
                        newNotif = context.getResources().getString(R.string.insertion_notif_beinvenu);
                        break;
                    case "Votre mot de passe a été changé recémment. Si ce n`est pas vous, notifiez-le !":
                        newNotif = context.getResources().getString(R.string.insertion_notif_sec);
                        break;
                    case "Your password has been changed recently. If this wasn`t you, please notify us!":
                        newNotif = context.getResources().getString(R.string.insertion_notif_sec);
                        break;
                    default:
                        newNotif = notification;
                        break;
                }

                Notification notifications = new Notification(id, idUser, newTitle, newNotif, categorie, dateNotif);
                listeNotifications.add(notifications);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeNotifications;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
