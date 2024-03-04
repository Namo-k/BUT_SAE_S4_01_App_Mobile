package fr.iut.sae_s4_01_app_mobile.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Alertes extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_alerte";
    private static final int version = 1;
    private Medicament medicamentDatabase;

    public Alertes(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
        medicamentDatabase = new Medicament(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE alertes (id integer PRIMARY KEY, codeCis INTEGER, idUser INTEGER, nomMedicament VARCHAR(100), raison VARCHAR(50), message VARCHAR(500), FOREIGN KEY (idUser) REFERENCES users(id), FOREIGN KEY (codeCis) REFERENCES medicament(codeCis),FOREIGN KEY (nomMedicament) REFERENCES medicament(nomMedicament) )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alertes");
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
        valeurs.put("nomMedicament", nomMedicament); // Utiliser le nom récupéré ici
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
            ((Cursor) cursor).close();
        }
        return count;
    }
}
