package fr.iut.sae_s4_01_app_mobile.bd;

import android.content.ContentValues;
import android.content.Context;
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
        db.execSQL("CREATE TABLE users (id integer PRIMARY KEY,genre VARCHAR(20), name VARCHAR(50), prenom VARCHAR(50), dataNaissance varchar(10), UNIQUE(name,prenom))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users ");
        onCreate(db);
    }


    public long insertData(String genre,String name, String prenom, String date) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentval = new ContentValues();
        contentval.put("name", name);
        contentval.put("prenom", prenom);
        contentval.put("genre", genre);
        contentval.put("dataNaissance", date);

        return MyDatabase.insert("users", null, contentval);


    }



}

