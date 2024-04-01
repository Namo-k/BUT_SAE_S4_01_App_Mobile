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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date; // Ajout de l'import pour la classe Date
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        db.execSQL("CREATE TABLE alertes (id integer PRIMARY KEY, codeCip INTEGER, idUser INTEGER, nomMedicament VARCHAR(255), raison VARCHAR(50), message VARCHAR(500), moyen VARCHAR(50), dateAlerte DATETIME DEFAULT (datetime('now')), important BOOLEAN , FOREIGN KEY (idUser) REFERENCES users(id), FOREIGN KEY (codeCip) REFERENCES medicament(codeCip), FOREIGN KEY (nomMedicament) REFERENCES medicament(nomMedicament))");
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
        valeurs.put("important", false); // Valeur par défaut pour "important"
        valeurs.put("dateAlerte", getCurrentDateTime()); // Ajout de la date et l'heure actuelles

        long resultat = maBaseDeDonnees.insert("alertes", null, valeurs);

        return resultat != -1;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
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

    public List<Alerte> getAlertesDESC(int idUser, Context context) {
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

                String msg;
                switch (message) {
                    case "Aucun message rédigé...":
                        msg = context.getResources().getString(R.string.nomsg);
                        break;
                    case "No message written...":
                        msg = context.getResources().getString(R.string.nomsg);
                        break;
                    default:
                        msg = message;
                        break;
                }

                Alerte alerte = new Alerte(idUser, codeCip, idUser, nomMedicament, newRaison, msg, dateAlerte);
                listeAlertes.add(alerte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeAlertes;
    }


    public List<Alerte> getAlertesASC(int idUser, Context context) {
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

                String msg;
                switch (raison) {
                    case "Aucun message rédigé...":
                        msg = context.getResources().getString(R.string.nomsg);
                        break;
                    case "No message written...":
                        msg = context.getResources().getString(R.string.nomsg);
                        break;
                    default:
                        msg = message;
                        break;
                }

                Alerte alerte = new Alerte(idUser, codeCip, idUser, nomMedicament, newRaison, msg, dateAlerte);
                listeAlertes.add(alerte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listeAlertes;
    }

    //Partie Admin :

    public List<Alerte> getAllAlertesDESC() {
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

    public List<Alerte> getAllAlertesASC() {
        List<Alerte> listeAlertes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes ORDER BY id ASC", null);
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

    public List<Alerte> getAllAlertesImportantes() {
        List<Alerte> alertesImportantes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes WHERE important = 1",null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int codeCip = cursor.getInt(cursor.getColumnIndex("codeCip"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") int idUser = cursor.getInt(cursor.getColumnIndex("idUser"));

                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                Alerte alerte = new Alerte(id, codeCip, idUser, nomMedicament, raison, message, dateAlerte);
                alertesImportantes.add(alerte);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return alertesImportantes;
    }

    public List<Alerte> getAllAlertesNotImportantes() {
        List<Alerte> alertesImportantes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes WHERE important = 0",null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int codeCip = cursor.getInt(cursor.getColumnIndex("codeCip"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") int idUser = cursor.getInt(cursor.getColumnIndex("idUser"));

                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                Alerte alerte = new Alerte(id, codeCip, idUser, nomMedicament, raison, message, dateAlerte);
                alertesImportantes.add(alerte);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return alertesImportantes;
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

    public int getNombreAlertesImportant() {
        SQLiteDatabase db = this.getReadableDatabase();
        int nombreAlertesImportant = 0;

        String query = "SELECT COUNT(*) FROM alertes WHERE important = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            nombreAlertesImportant = cursor.getInt(0);
            cursor.close();
        }

        return nombreAlertesImportant;
    }

    public boolean marquerImportanceTrue(long idAlerte) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put("important", true);
        int rowsAffected = db.update("alertes", valeurs, "id=?", new String[] { String.valueOf(idAlerte) });
        return rowsAffected > 0;
    }

    public long getLastInsertedAlertId() {
        SQLiteDatabase db = this.getReadableDatabase();
        long idAlerte = -1;
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM alertes", null);
        if (cursor != null && cursor.moveToFirst()) {
            idAlerte = cursor.getLong(0);
            cursor.close();
        }
        return idAlerte;
    }

    public List<Alerte> getAlertesImportantes(int idUser) {
        List<Alerte> alertesImportantes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes WHERE idUser = ? AND important = 1", new String[]{String.valueOf(idUser)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int codeCip = cursor.getInt(cursor.getColumnIndex("codeCip"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                Alerte alerte = new Alerte(idUser, codeCip, idUser, nomMedicament, raison, message, dateAlerte);
                alertesImportantes.add(alerte);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return alertesImportantes;
    }

    public List<Alerte> getAlertesNotImportantes(int idUser) {
        List<Alerte> alertesImportantes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alertes WHERE idUser = ? AND important = 0", new String[]{String.valueOf(idUser)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int codeCip = cursor.getInt(cursor.getColumnIndex("codeCip"));
                @SuppressLint("Range") String nomMedicament = cursor.getString(cursor.getColumnIndex("nomMedicament"));
                @SuppressLint("Range") String raison = cursor.getString(cursor.getColumnIndex("raison"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));

                Alerte alerte = new Alerte(idUser, codeCip, idUser, nomMedicament, raison, message, dateAlerte);
                alertesImportantes.add(alerte);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return alertesImportantes;
    }


    public Map<String, Integer> getNombreSignalementsParMois() {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Integer> signalementsParMois = new HashMap<>();

        //supprimerTousSignalements();
        //insertDonneesFictives(db);

        String query = "SELECT dateAlerte FROM alertes";
        Cursor cursor = db.rawQuery(query, null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String dateAlerte = cursor.getString(cursor.getColumnIndex("dateAlerte"));
                String mois = convertirDateEnMoisAnglais(dateAlerte);
                signalementsParMois.put(mois, signalementsParMois.getOrDefault(mois, 0) + 1);
            } while (cursor.moveToNext());
            cursor.close();

        }

        String[] tousMois = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Map<String, Integer> signalementsParMoisCroissant = new LinkedHashMap<>();
        for (String mois : tousMois) {
            signalementsParMoisCroissant.put(mois, signalementsParMois.getOrDefault(mois, 0));
        }

        return signalementsParMoisCroissant;
    }


    private String convertirDateEnMois(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(date));
            String mois = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());
            return normaliserMois(mois);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertirDateEnMoisAnglais(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(date));
            String mois = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
            return normaliserMois(mois);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    private String normaliserMois(String mois) {
        mois = mois.toUpperCase();
        if (mois.length() >= 3) {
            mois = mois.substring(0, 3);
        }
        return mois.substring(0, 1).toUpperCase() + mois.substring(1).toLowerCase();
    }


    private void insertDonneesFictives(SQLiteDatabase db, int idUser) {

        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921617535, " + idUser + ", 'Medicament1', 'Raison1', 'Message1', 'saisie', '2024-01-15 08:30:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921617535, " + idUser + ", 'Medicament2', 'Raison2', 'Message2', 'scan', '2024-03-20 10:45:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921662078, "+ idUser + ", 'Medicament3', 'Raison3', 'Message3', 'saisie', '2024-02-05 14:15:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921661477, "+ idUser + ", 'Medicament4', 'Raison4', 'Message4', 'scan', '2024-04-10 16:20:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921617535, "+ idUser + ", 'Medicament5', 'Raison5', 'Message5', 'saisie', '2024-06-25 18:30:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921643497, "+ idUser + ", 'Medicament6', 'Raison6', 'Message6', 'scan', '2024-05-05 09:45:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921672763, "+ idUser + ", 'Medicament7', 'Raison7', 'Message7', 'saisie', '2024-07-15 12:00:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921662078, "+ idUser + ", 'Medicament8', 'Raison8', 'Message8', 'scan', '2024-08-20 14:20:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921625929, "+ idUser + ", 'Medicament9', 'Raison9', 'Message9', 'saisie', '2024-09-10 16:45:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921672763, "+ idUser +", 'Medicament10', 'Raison10', 'Message10', 'scan', '2024-10-30 18:30:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921643497, "+ idUser + ", 'Medicament11', 'Raison11', 'Message11', 'saisie', '2024-11-08 10:15:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921790030, "+ idUser + ", 'Medicament12', 'Raison12', 'Message12', 'scan', '2024-12-18 13:40:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921653663, "+ idUser + ", 'Medicament13', 'Raison13', 'Message13', 'saisie', '2025-01-28 15:55:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921661477, "+ idUser + ", 'Medicament14', 'Raison14', 'Message14', 'scan', '2025-02-10 08:30:00')");
        db.execSQL("INSERT INTO alertes (codeCip, idUser, nomMedicament, raison, message, moyen, dateAlerte) VALUES (3400921790030, "+ idUser + ", 'Medicament15', 'Raison15', 'Message15', 'saisie', '2025-03-20 09:45:00')");
    }



    public void supprimerTousSignalements() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM alertes");
    }


}