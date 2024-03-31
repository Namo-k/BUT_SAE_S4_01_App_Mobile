package fr.iut.sae_s4_01_app_mobile.bd;

import android.annotation.SuppressLint;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Medicament extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_medicament";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    public Medicament(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE medicament (codeCip INTEGER, nomMedicament VARCHAR(100), Libelle VARCHAR(255), PRIMARY KEY(codeCip, Libelle))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS medicament");
        onCreate(db);
    }

    public void importCSVToDatabase(InputStream inputStream) {
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM medicament", null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count == 0) {
                try {
                    CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                            .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
                            .build();
                    String[] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        ContentValues values = new ContentValues();
                        values.put("codeCip", nextLine[0]);
                        values.put("nomMedicament", nextLine[1]);
                        values.put("Libelle", nextLine[2]);

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

    @SuppressLint("Range")
    public String getNomMedicamentByCodeCIP(String codeCIP) {
        SQLiteDatabase database = this.getReadableDatabase();
        String nomMedicament = null;

        Cursor cursor = database.query("medicament",
                new String[]{"nomMedicament"},
                "codeCip = ?",
                new String[]{codeCIP},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
            cursor.close();
        }

        return nomMedicament;
    }

    public List<String> getPathologiesByCodeCIP(String codeCIS) {
        SQLiteDatabase database = this.getReadableDatabase();
        List<String> pathologies = new ArrayList<>();

        Cursor cursor = database.query("medicament",
                new String[]{"Libelle"},
                "codeCip = ?",
                new String[]{codeCIS},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String pathologie = cursor.getString(cursor.getColumnIndex("Libelle"));
                pathologies.add(pathologie);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return pathologies;
    }


}