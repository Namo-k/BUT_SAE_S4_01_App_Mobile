package fr.iut.sae_s4_01_app_mobile.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Users extends SQLiteOpenHelper {

    private static final String databaseName = "bdUsers";
    private static final int version = 1;


    public Users(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id integer PRIMARY KEY,genre VARCHAR(20), nom VARCHAR(50), prenom VARCHAR(50), dataNaissance VARCHAR(10), pharmacie VARCHAR(50), medecin VARCHAR(50), UNIQUE(nom,prenom))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users ");
        onCreate(db);
    }

    public long insertData(String genre,String name, String prenom, String date, String pharmacie, String medecin) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();

        String nomC = name.substring(0, 1).toUpperCase() + name.substring(1);
        String prenomC = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);

        contentval.put("nom", nomC);
        contentval.put("prenom", prenomC);
        contentval.put("genre", genre);
        contentval.put("dataNaissance", date);
        contentval.put("pharmacie", pharmacie);
        contentval.put("medecin", medecin);

        return MyDatabase.insert("users", null, contentval);
    }

    public long updateData(Integer id, String genre,String name, String prenom, String date, String pharmacie, String medecin) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();

        String nomC = name.substring(0, 1).toUpperCase() + name.substring(1);
        String prenomC = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);

        contentval.put("nom", nomC);
        contentval.put("prenom", prenomC);
        contentval.put("genre", genre);
        contentval.put("dataNaissance", date);
        contentval.put("pharmacie", pharmacie);
        contentval.put("medecin", medecin);

        String whereClause = "ID = ?";
        String[] whereArgs = { String.valueOf(id) };

        return MyDatabase.update("users", contentval, whereClause, whereArgs);
    }

    public long deleteUserData(Integer id) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        String whereClause = "ID = ?";
        String[] whereArgs = { String.valueOf(id) };

        return MyDatabase.delete("users", whereClause, whereArgs);
    }

    @SuppressLint("Range")
    public String getNom (Integer id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String nom = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT nom FROM users WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            nom = cursor.getString(cursor.getColumnIndex("nom"));
            cursor.close();
        }
        return nom;

    }

    @SuppressLint("Range")
    public String getPrenom (Integer id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String prenom = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT prenom FROM users WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            prenom = cursor.getString(cursor.getColumnIndex("prenom"));
            cursor.close();
        }
        return prenom;

    }

    @SuppressLint("Range")
    public String getSexe (Integer id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String sexe = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT genre FROM users WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            sexe = cursor.getString(cursor.getColumnIndex("genre"));
            cursor.close();
        }
        return sexe;
    }

    @SuppressLint("Range")
    public String getDataNais(Integer id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String date = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT dataNaissance FROM users WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            date = cursor.getString(cursor.getColumnIndex("dataNaissance"));
            cursor.close();
        }
        return date;
    }

    @SuppressLint("Range")
    public String getPharmacie(Integer id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String pharmacie = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT pharmacie FROM users WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            pharmacie = cursor.getString(cursor.getColumnIndex("pharmacie"));
            cursor.close();
        }
        return pharmacie;
    }

    @SuppressLint("Range")
    public String getMedecin(Integer id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String medecin = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT medecin FROM users WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            medecin = cursor.getString(cursor.getColumnIndex("medecin"));
            cursor.close();
        }
        return medecin;
    }
}

