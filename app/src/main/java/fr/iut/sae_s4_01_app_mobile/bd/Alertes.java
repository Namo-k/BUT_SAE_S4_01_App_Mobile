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

    public int getNombreTotalAlertes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM alertes";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public List<Alerte> getAllAlertes() {
        List<Alerte> listeAlertes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes ORDER BY id ASC, dateAlerte ASC", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int codeCis = cursor.getInt(cursor.getColumnIndex("codeCis"));
                int idUser = cursor.getInt(cursor.getColumnIndex("idUser"));
                String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                String raison = cursor.getString(cursor.getColumnIndex("raison"));
                String message = cursor.getString(cursor.getColumnIndex("message"));
                String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                Alerte alerte = new Alerte(id, codeCis, idUser, nomMedicament, raison, message, dateAlerte);
                listeAlertes.add(alerte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeAlertes;
    }
}
