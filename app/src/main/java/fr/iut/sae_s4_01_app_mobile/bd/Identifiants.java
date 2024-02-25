package fr.iut.sae_s4_01_app_mobile.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Identifiants extends SQLiteOpenHelper {

    private static final String databaseName = "bdIdentifiaction";
    private static final int version = 1;
    public Identifiants(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE identifiants (id integer PRIMARY KEY,email VARCHAR(50), mdp VARCHAR(50), UNIQUE(email, mdp))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS identifiants ");
        onCreate(db);
    }

    public Boolean insertData(String email, String mdp, long userID) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();
        contentval.put("email", email);
        contentval.put("mdp", mdp);

        // Insertion dans la table identifiants
        long result = MyDatabase.insert("identifiants", null, contentval);

        // Vérification du succès de l'insertion
        if (result != -1) {
            // Si l'insertion dans la table identifiants réussit,
            // le résultat de l'insertion dans la table users est retourné
            return true;
        } else {
            // Si l'insertion échoue, retourne false
            return false;
        }
    }

    public long updateData(Integer id, String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();

        contentval.put("email", email);

        String whereClause = "ID = ?";
        String[] whereArgs = { String.valueOf(id) };

        return MyDatabase.update("identifiants", contentval, whereClause, whereArgs);
    }

    public long deleteUserData(Integer id) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        String whereClause = "ID = ?";
        String[] whereArgs = { String.valueOf(id) };

        return MyDatabase.delete("identifiants", whereClause, whereArgs);
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from identifiants where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }



    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from identifiants where email = ? and mdp = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public int getId(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        int id = -1;
        Cursor cursor = MyDatabase.rawQuery("Select id from identifiants where email = ? and mdp = ?", new String[]{email, password});
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
        }
        return id;
    }
    @SuppressLint("Range")
    public String getMail(Integer id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String mail = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT email FROM identifiants WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            mail = cursor.getString(cursor.getColumnIndex("email"));
            cursor.close();
        }
        return mail;
    }
}
