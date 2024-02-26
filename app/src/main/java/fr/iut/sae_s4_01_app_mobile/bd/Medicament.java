package fr.iut.sae_s4_01_app_mobile.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Medicament extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "medicament";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    public Medicament(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE medicament (codeCis INTEGER PRIMARY KEY,nomMedicament VARCHAR(50), forma VARCHAR(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS medicament");
        onCreate(db);
    }

    public void importCSVToDatabase(InputStream inputStream) {
        Log.e(TAG, "BJR");

        SQLiteDatabase database = this.getWritableDatabase();

        //verif si y'a des donnée dans la page
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM medicament", null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count == 0) {
                try {

                    CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                            .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
                            .build();
                    String[] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        ContentValues values = new ContentValues();
                        values.put("codeCis", nextLine[0]);
                        values.put("nomMedicament", nextLine[1]);
                        values.put("forma", nextLine[2]);

                        long ligneid = database.insert("medicament", null, values);
                        if (ligneid == -1) {
                            Log.e(TAG, "Erreur lors de l'insertion des données");
                        }
                    }
                    reader.close();
                } catch (IOException | CsvValidationException e) {
                    Log.e(TAG, "Erreur lors de la lecture du fichier CSV", e);
                }
            } else {
                Log.e(TAG, "La table 'medicament' existe déjà.");
            }
        }
    }


}


