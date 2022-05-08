package com.dexterous.flutterlocalnotifications.utils;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.Cursor;


public class QuoteDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "quotes.db";
    /* Inner class that defines the table contents */
    public static final String COLUMN_ID = "id";
    public static final String TABLE_NAME = "quotes";
    public static final String COLUMN_NAME_TITLE = "text";
    public static final String COLUMN_NAME_AUTHOR = "author";
    public static final String COLUMN_NAME_FAVORITE = "isFavorite";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY," + COLUMN_NAME_TITLE + " TEXT," + COLUMN_NAME_AUTHOR + " TEXT,"
            + COLUMN_NAME_FAVORITE + " BOOLEAN)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public QuoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public long fetchQuotesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM " + QuoteDbHelper.TABLE_NAME;
        SQLiteStatement statement = db.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }
    public String readQuote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String rawSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + Integer.toString(id);
        Cursor cursor = db.rawQuery(rawSql, null);
        if(cursor.moveToNext()){
            String itemTitle = cursor.getString(cursor.getColumnIndexOrThrow(QuoteDbHelper.COLUMN_NAME_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(QuoteDbHelper.COLUMN_NAME_AUTHOR));
            cursor.close();
            return "\"" + itemTitle + "\"" + " _" + author;
        }
        cursor.close();
        db.close();
        return "quote is empty!!!" +  " _getQuoteOfTheDay (native android code)";
    }

    // public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // onUpgrade(db, oldVersion, newVersion);
    // }

}