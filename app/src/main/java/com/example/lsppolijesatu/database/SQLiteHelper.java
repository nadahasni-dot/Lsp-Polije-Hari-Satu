package com.example.lsppolijesatu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lsppolijesatu.model.ModelDaftar;

public class SQLiteHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    private static final String DB_NAME = "lspPolije.db";
    private static final int DB_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE USER(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAMA TEXT, " +
                "EMAIL TEXT, " +
                "USERNAME TEXT, " +
                "PASSWORD TEXT" +
                ");";

        db.execSQL(sql);
    }

    public boolean insertUser(ModelDaftar user) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(getSingleUser(user.getUsername(), user.getPassword())) {
            return false;
        }

        contentValues.put("NAMA", user.getNama());
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("USERNAME", user.getUsername());
        contentValues.put("PASSWORD", user.getPassword());
        sqLiteDatabase.insert("USER", null, contentValues);
        return true;
    }

    public boolean getSingleUser(String username, String password) {
        sqLiteDatabase = this.getReadableDatabase();
        String selectSingleUserQuery = "SELECT * FROM USER " +
                "WHERE USERNAME = '" + username + "' " +
                "AND PASSWORD = '" + password + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(selectSingleUserQuery, null);

        if (cursor.getCount() > 0) {
            return true;
        }

        return false;
    }

    public ModelDaftar getUserByUsername(String username) {
        sqLiteDatabase = this.getReadableDatabase();
        String selectSingleUserQuery = "SELECT * FROM USER " +
                "WHERE USERNAME = '" + username + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(selectSingleUserQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            ModelDaftar modelDaftar = new ModelDaftar(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );

            return modelDaftar;
        }

        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }
}
