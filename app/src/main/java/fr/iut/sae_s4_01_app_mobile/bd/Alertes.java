package fr.iut.sae_s4_01_app_mobile.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Alertes extends SQLiteOpenHelper {

    private static final String databaseName = "bdUsers";
    private static final int version = 1;

    public Alertes(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE alertes (id integer PRIMARY KEY, codeCis INTEGER, idUser INTEGER, nomMedicament VARCHAR(50), raison VARCHAR(50), message VARCHAR(500), FOREIGN KEY (idUser) REFERENCES users(id), FOREIGN KEY (codeCis) REFERENCES medicament(codeCis),FOREIGN KEY (nomMedicament) REFERENCES medicament(nomMedicament) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alertes");
        onCreate(db);
    }

    public Boolean insertData(int idUser, int codeCis, String nomMedicament, String raison, String message) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();
        contentval.put("idUser", idUser);
        contentval.put("nomMedicament", nomMedicament);
        contentval.put("raison", raison);
        contentval.put("codeCis", codeCis);
        contentval.put("message", message);

        long result = MyDatabase.insert("alertes", null, contentval);

        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }


}
