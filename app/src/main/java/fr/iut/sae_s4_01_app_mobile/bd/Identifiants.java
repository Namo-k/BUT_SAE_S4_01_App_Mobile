package fr.iut.sae_s4_01_app_mobile.bd;

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
        contentval.put("id", userID); // Utilisation de l'ID de l'utilisateur
        contentval.put("email", email);
        contentval.put("mdp", mdp);

        long result = MyDatabase.insert("identifiants", null, contentval);

        return result != -1;
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
}
