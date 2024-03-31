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
import java.util.Collections;
import java.util.Date; // Ajout de l'import pour la classe Date
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.iut.sae_s4_01_app_mobile.Alerte;
import fr.iut.sae_s4_01_app_mobile.R;

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
        db.execSQL("CREATE TABLE alertes (id integer PRIMARY KEY, codeCip INTEGER, idUser INTEGER, nomMedicament VARCHAR(255), raison VARCHAR(50), message VARCHAR(500),moyen VARCHAR(50), dateAlerte DATETIME DEFAULT (datetime('now')), FOREIGN KEY (idUser) REFERENCES users(id), FOREIGN KEY (codeCip) REFERENCES medicament(codeCip), FOREIGN KEY (nomMedicament) REFERENCES medicament(nomMedicament))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alertes ");
        onCreate(db);
    }

    public Boolean insertData(int idUser, String codeCip, String raison, String message, String moyen) {
        SQLiteDatabase maBaseDeDonnees = this.getWritableDatabase();

        // Récupérer le nom du médicament en fonction du codeCIS
        String nomMedicament = medicamentDatabase.getNomMedicamentByCodeCIP(String.valueOf(codeCip));
        if (nomMedicament == null || nomMedicament.isEmpty()) {
            return false;
        }
        ContentValues valeurs = new ContentValues();
        valeurs.put("idUser", idUser);
        valeurs.put("codeCip", codeCip);
        valeurs.put("nomMedicament", nomMedicament);
        valeurs.put("raison", raison);
        valeurs.put("message", message);
        valeurs.put("moyen", moyen);
        valeurs.put("dateAlerte", getCurrentDateTime()); // Ajout de la date et l'heure actuelles

        long resultat = maBaseDeDonnees.insert("alertes", null, valeurs);

        return resultat != -1;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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

    public List<Alerte> getAllAlertesDESC(int idUser, Context context) {
        List<Alerte> listeAlertes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes where idUser = ? ORDER BY id DESC", new String[] {String.valueOf(idUser)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int codeCip = cursor.getInt(cursor.getColumnIndex("codeCip"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                String newRaison;
                switch (raison) {
                    case "Renewal not possible":
                        newRaison = context.getResources().getString(R.string.renewal_impossible);
                        break;
                    case "Renouvellement impossible":
                        newRaison = context.getResources().getString(R.string.renewal_impossible);
                        break;
                    case "Stock unavailable":
                        newRaison = context.getResources().getString(R.string.stock_unavailable);
                        break;
                    case "Stock indisponible":
                        newRaison = context.getResources().getString(R.string.stock_unavailable);
                        break;
                    case "Late delivery":
                        newRaison = context.getResources().getString(R.string.late_delivery);
                        break;
                    case "Livraison tardive":
                        newRaison = context.getResources().getString(R.string.late_delivery);
                        break;
                    case "Other":
                        newRaison = context.getResources().getString(R.string.other);
                        break;
                    case "Autre":
                        newRaison = context.getResources().getString(R.string.other);
                        break;
                    case "Select a reason":
                        newRaison = context.getResources().getString(R.string.select_reason);
                        break;
                    case "Sélectionnez une raison":
                        newRaison = context.getResources().getString(R.string.select_reason);
                        break;
                    default:
                        newRaison = raison;
                        break;
                }

                Alerte alerte = new Alerte(idUser, codeCip, idUser, nomMedicament, newRaison, message, dateAlerte);
                listeAlertes.add(alerte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeAlertes;
    }


    public List<Alerte> getAllAlertesASC(int idUser, Context context) {
        List<Alerte> listeAlertes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes where idUser = ? ORDER BY id ASC", new String[] {String.valueOf(idUser)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int codeCip = cursor.getInt(cursor.getColumnIndex("codeCip"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                String newRaison;
                switch (raison) {
                    case "Renewal not possible":
                        newRaison = context.getResources().getString(R.string.renewal_impossible);
                        break;
                    case "Renouvellement impossible":
                        newRaison = context.getResources().getString(R.string.renewal_impossible);
                        break;
                    case "Stock unavailable":
                        newRaison = context.getResources().getString(R.string.stock_unavailable);
                        break;
                    case "Stock indisponible":
                        newRaison = context.getResources().getString(R.string.stock_unavailable);
                        break;
                    case "Late delivery":
                        newRaison = context.getResources().getString(R.string.late_delivery);
                        break;
                    case "Livraison tardive":
                        newRaison = context.getResources().getString(R.string.late_delivery);
                        break;
                    case "Other":
                        newRaison = context.getResources().getString(R.string.other);
                        break;
                    case "Autre":
                        newRaison = context.getResources().getString(R.string.other);
                        break;
                    case "Select a reason":
                        newRaison = context.getResources().getString(R.string.select_reason);
                        break;
                    case "Sélectionnez une raison":
                        newRaison = context.getResources().getString(R.string.select_reason);
                        break;
                    default:
                        newRaison = raison;
                        break;
                }

                Alerte alerte = new Alerte(idUser, codeCip, idUser, nomMedicament, newRaison, message, dateAlerte);
                listeAlertes.add(alerte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeAlertes;
    }

    //Partie Admin :

    public List<Alerte> getAllAlertes() {
        List<Alerte> listeAlertes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int codeCip = cursor.getInt(cursor.getColumnIndex("codeCip"));
                @SuppressLint("Range") int idUser = cursor.getInt(cursor.getColumnIndex("idUser"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                Alerte alerte = new Alerte(id, codeCip, idUser, nomMedicament, raison, message, dateAlerte);
                listeAlertes.add(alerte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeAlertes;
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

    @SuppressLint("Range")
    public String getMedicamentLePlusSignale() {
        SQLiteDatabase db = this.getReadableDatabase();
        String medicamentLePlusSignale = null;

        String query = "SELECT nomMedicament, COUNT(*) AS nombreAlertes FROM alertes GROUP BY nomMedicament ORDER BY nombreAlertes DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            medicamentLePlusSignale = cursor.getString(cursor.getColumnIndex("nomMedicament"));
            cursor.close();
        }

        return medicamentLePlusSignale;
    }

    public String getCategorieMedicamentLePlusSignale() {
        SQLiteDatabase db = this.getReadableDatabase();
        String categorieMedicamentLePlusSignale = null;

        String query = "SELECT codeCip FROM alertes";
        Cursor cursor = db.rawQuery(query, null);

        List<String> pathologiesList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String codeCip = cursor.getString(cursor.getColumnIndex("codeCip"));
                List<String> pathologies = medicamentDatabase.getPathologiesByCodeCIP(codeCip);
                pathologiesList.addAll(pathologies);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Trouver les pathologies les plus fréquentes
        if (!pathologiesList.isEmpty()) {
            Map<String, Integer> pathologieCounts = new HashMap<>();
            for (String pathologie : pathologiesList) {
                pathologieCounts.put(pathologie, pathologieCounts.getOrDefault(pathologie, 0) + 1);
            }

            // Trier les pathologies par fréquence décroissante
            List<Map.Entry<String, Integer>> sortedPathologies = new ArrayList<>(pathologieCounts.entrySet());
            Collections.sort(sortedPathologies, (a, b) -> b.getValue().compareTo(a.getValue()));

            // Choisir la première pathologie non-"AUCUNE"
            for (Map.Entry<String, Integer> entry : sortedPathologies) {
                if (!entry.getKey().equals("AUCUNE")) {
                    categorieMedicamentLePlusSignale = entry.getKey();
                    break;
                }
            }
        }

        return categorieMedicamentLePlusSignale;
    }


    public Map<String, Integer> getOccurrencesPathologies() {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Integer> occurrencesPathologies = new HashMap<>();

        String query = "SELECT codeCip FROM alertes";
        Cursor cursor = db.rawQuery(query, null);

        List<String> pathologiesList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String codeCip = cursor.getString(cursor.getColumnIndex("codeCip"));
                List<String> pathologies = medicamentDatabase.getPathologiesByCodeCIP(codeCip);
                pathologiesList.addAll(pathologies);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Compter les occurrences de chaque pathologie
        for (String pathologie : pathologiesList) {
            occurrencesPathologies.put(pathologie, occurrencesPathologies.getOrDefault(pathologie, 0) + 1);
        }

        return occurrencesPathologies;
    }

    public int getNombreAlertesParSaisie() {
        SQLiteDatabase db = this.getReadableDatabase();
        int nombreAlertesSaisie = 0;

        String query = "SELECT COUNT(*) FROM alertes WHERE moyen = 'saisie'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            nombreAlertesSaisie = cursor.getInt(0);
            cursor.close();
        }

        return nombreAlertesSaisie;
    }

    public int getNombreAlertesParScan() {
        SQLiteDatabase db = this.getReadableDatabase();
        int nombreAlertesSaisie = 0;

        String query = "SELECT COUNT(*) FROM alertes WHERE moyen = 'scan'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            nombreAlertesSaisie = cursor.getInt(0);
            cursor.close();
        }

        return nombreAlertesSaisie;
    }

}