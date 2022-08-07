package com.cst2335.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Cocktail Database Helper class
 */
public class MyOpenHelper extends SQLiteOpenHelper {


    /**
     * Variables needed for the database
     */

    private static final String ACTIVITY_NAME = "MyOpenHelper";
    public static final String FILENAME = "MyDatabase";
    public static final int VERSION_NUM = 22;
    public static final String TABLE_NAME = "Cocktails";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_PICTURE = "PICTURE";
    public static final String COL_INSTRUCTIONS = "INSTRUCTIONS";
    public static final String COL_INGREDIENT1 = "INGREDIENT1";
    public static final String COL_INGREDIENT2 = "INGREDIENT2";
    public static final String COL_INGREDIENT3 = "INGREDIENT3";


    /**
     * build the database
     * @param ctx
     */
    public MyOpenHelper(Context ctx) {

        super(ctx, FILENAME, null, VERSION_NUM);
    }

    /**
     * creates the cocktail database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * creating the table for the database with 6 columns
         */
        Log.i(ACTIVITY_NAME, "OnCreate");

        //String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                //"NAME TEXT, " + " INSTRUCTIONS TEXT," + " INGREDIENT1 TEXT, " + " INGREDIENT2 TEXT, " + " INGREDIENT3 TEXT) ";

        db.execSQL("CREATE TABLE Cocktails(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, INSTRUCTIONS TEXT, INGREDIENT1 TEXT, INGREDIENT2 TEXT, INGREDIENT3 TEXT)");

        /**
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT,"
                + COL_INSTRUCTIONS + " TEXT,"
                + COL_INGREDIENT1 + " TEXT,"
                + COL_INGREDIENT2 + " TEXT,"
                + COL_INGREDIENT3 + " TEXT);");
      */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("MyOpenHelper", "oldVersion=" + oldVersion + "newVersion=" + newVersion);
        db.execSQL("Drop table if exists " + TABLE_NAME);
        this.onCreate(db);
    }



    public boolean addData (String drinkName,String drinkInstructions, String ingredient1, String ingredient2, String ingredient3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, drinkName);
        cv.put(COL_INSTRUCTIONS, drinkInstructions);
        cv.put(COL_INGREDIENT1, ingredient1);
        cv.put(COL_INGREDIENT2, ingredient2);
        cv.put(COL_INGREDIENT3, ingredient3);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            return false;
        }
        else  {
            return true;
        }
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}