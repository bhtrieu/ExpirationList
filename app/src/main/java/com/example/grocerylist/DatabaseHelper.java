package com.example.grocerylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "items.db";
    public static final String TABLE_NAME = "items";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM_NAMES";
    public static final String COL3 = "EXPIRATION_DATE";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME +
            "( INTEGER PRIMARY KEY,"  +
            COL2 + " TEXT, " +
            COL3 + " TEXT)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean addData(String itemName, String expirationDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL2, itemName);
        values.put(COL3, expirationDate);

        if (db.insert(TABLE_NAME, null, values) == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getExpirationList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}
