package fr.iut.sae_s4_01_app_mobile.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Identifiants extends SQLiteOpenHelper {

    private static final String databaseName = "bdIdentifiaction";
    private static final int version = 1;
    public Identifiants(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE identifiants (id integer PRIMARY KEY,email VARCHAR(50), mdp VARCHAR(50),code INTEGER DEFAULT NULL, date_expiration DATETIME DEFAULT NULL, UNIQUE(email, mdp))");
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

        long result = MyDatabase.insert("identifiants", null, contentval);

        if (result != -1) {
            return true;
        } else {
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

    public void updateResetCode(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int code = (int) ((Math.random() * (999999 - 100000 + 1)) + 100000); // Correction ici

        contentValues.put("code", code);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTime = dateFormat.format(new Date());

        contentValues.put("date_expiration", currentDateTime);

        String whereClause = "email = ?";
        String[] whereArgs = { email };

        MyDatabase.update("identifiants", contentValues, whereClause, whereArgs);
    }

    @SuppressLint("Range")
    public String getCode(String email){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String code = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT code FROM identifiants WHERE email=?", new String[]{String.valueOf(email)});
        if (cursor != null && cursor.moveToFirst()) {
            code = cursor.getString(cursor.getColumnIndex("code"));
            cursor.close();
        }
        return code;
    }

    @SuppressLint("Range")
    public String getDateCode(String email){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String date_expiration = " ";
        Cursor cursor = MyDatabase.rawQuery("SELECT date_expiration FROM identifiants WHERE email=?", new String[]{String.valueOf(email)});
        if (cursor != null && cursor.moveToFirst()) {
            date_expiration = cursor.getString(cursor.getColumnIndex("date_expiration"));
            cursor.close();
        }
        return date_expiration;
    }



    public static final int RESET_CODE_VALID = 0;
    public static final int RESET_CODE_INVALID = 1;
    public static final int RESET_CODE_EXPIRED = 2;
    @SuppressLint("Range")
    public int checkResetCodeValidity(String email, String code) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();

        Cursor cursor = MyDatabase.rawQuery("SELECT code, date_expiration FROM identifiants WHERE email=?", new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            String dbCode = cursor.getString(cursor.getColumnIndex("code"));
            String dateString = cursor.getString(cursor.getColumnIndex("date_expiration"));

            if (code.equals(dbCode)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    Date expirationDate = dateFormat.parse(dateString);
                    Date currentDate = new Date();
                    long diffInMillis = currentDate.getTime() - expirationDate.getTime();
                    long diffInMinutes = diffInMillis / (60 * 1000);
                    if (diffInMinutes <= 30) {
                        cursor.close();
                        return RESET_CODE_VALID;
                    } else {
                        cursor.close();
                        return RESET_CODE_EXPIRED;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    cursor.close();
                    return RESET_CODE_INVALID;
                }
            }
            cursor.close();
        }
        return RESET_CODE_INVALID;
    }


    public void updatePassword(String email, String newPassword) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("mdp", newPassword);

        String whereClause = "email = ?";
        String[] whereArgs = { email };

        MyDatabase.update("identifiants", contentValues, whereClause, whereArgs);
    }



}
