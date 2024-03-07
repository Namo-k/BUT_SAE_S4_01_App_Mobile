package fr.iut.sae_s4_01_app_mobile.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date; // Ajout de l'import pour la classe Date
import java.util.List;

import fr.iut.sae_s4_01_app_mobile.Alerte;

public class Alertes extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_alerte";
    private static final int version = 1; // Modification de la version de la base de données
    private Medicament medicamentDatabase;

    public Alertes(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
        medicamentDatabase = new Medicament(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE alertes (id integer PRIMARY KEY, codeCis INTEGER, idUser INTEGER, nomMedicament VARCHAR(100), raison VARCHAR(50), message VARCHAR(500), dateAlerte TEXT DEFAULT (date('now')), FOREIGN KEY (idUser) REFERENCES users(id), FOREIGN KEY (codeCis) REFERENCES medicament(codeCis), FOREIGN KEY (nomMedicament) REFERENCES medicament(nomMedicament))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alertes ");
        onCreate(db);
    }

    public Boolean insertData(int idUser, int codeCis, String raison, String message) {
        SQLiteDatabase maBaseDeDonnees = this.getWritableDatabase();

        // Récupérer le nom du médicament en fonction du codeCIS
        String nomMedicament = medicamentDatabase.getNomMedicamentByCodeCIP(String.valueOf(codeCis));
        if (nomMedicament == null || nomMedicament.isEmpty()) {
            return false;
        }
        ContentValues valeurs = new ContentValues();
        valeurs.put("idUser", idUser);
        valeurs.put("codeCis", codeCis);
        valeurs.put("nomMedicament", nomMedicament);
        valeurs.put("raison", raison);
        valeurs.put("message", message);

        long resultat = maBaseDeDonnees.insert("alertes", null, valeurs);

        return resultat != -1;
    }

    public int getNombreTotalAlertes(int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM alertes where idUser = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[] {String.valueOf(idUser)});
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }



    public List<Alerte> getAllAlertes(int idUser) {
        List<Alerte> listeAlertes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes where idUser = ? ORDER BY id DESC", new String[] {String.valueOf(idUser)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int codeCis = cursor.getInt(cursor.getColumnIndex("codeCis"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                Alerte alerte = new Alerte(idUser, codeCis, idUser, nomMedicament, raison, message, dateAlerte);
                listeAlertes.add(alerte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeAlertes;
    }

}