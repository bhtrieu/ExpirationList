package com.example.grocerylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "items.db";
    public static final String TABLE_NAME = "items";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM_NAMES";
    public static final String COL3 = "DATE_EXPIRED";
    public static final String COL4 = "DATE_ADDED";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME +
            "( INTEGER PRIMARY KEY,"  +
            COL2 + " TEXT, " +
            COL3 + " TEXT, " +
            COL4 + " TEXT)";
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

    /*
    Adds the data to the locale storage in the Android device
     */
    public boolean addData(String itemName, String dateExpired, String dateAdded){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL2, itemName);
        values.put(COL3, dateExpired);
        values.put(COL4, dateAdded);

        if (db.insert(TABLE_NAME, null, values) == -1){
            return false;
        } else {
            return true;
        }
    }

    /*
    Retrieves all the data from the locale storage
     */
    public Cursor getExpirationList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public int delete(long dateAdded){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = String.valueOf(dateAdded);
        int deletedRows = db.delete(TABLE_NAME, COL4 + "=" + selection, null);

        return deletedRows;
    }
}
