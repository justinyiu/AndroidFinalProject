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
     * This class *should* be completed
     */


    /**
     * Variables needed for the database
     */

    private static final String ACTIVITY_NAME = "MyOpenHelper";
    public static final String FILENAME = "MyDatabase";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Cocktails";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_PICTURE = "picture";
    public static final String COL_INSTRUCTIONS = "instructions";
    public static final String COL_INGREDIENT1 = "ingredient1";
    public static final String COL_INGREDIENT2 = "ingredient2";
    public static final String COL_INGREDIENT3 = "ingredient3";


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

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT,"
                + COL_INSTRUCTIONS + " TEXT,"
                + COL_INGREDIENT1 + " TEXT,"
                + COL_INGREDIENT2 + " TEXT,"
                + COL_INGREDIENT3 + " TEXT);");
        /**
         * if the above code does not create the table successfully, then try the code below
         */
       // db.execSQL(String.format("Create table %s(%s integer primary key autoincrement, %s text, %s INTEGER);"
       //         ,TABLE_NAME, COL_ID, COL_PICTURE, COL_INSTRUCTIONS, COL_INGREDIENTS));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("MyOpenHelper", "oldVersion=" + oldVersion + "newVersion=" + newVersion);
        db.execSQL("Drop table if exists " + TABLE_NAME);
        this.onCreate(db);
    }

    /**
    public boolean insertCocktailData(String name,String picture, String instructions, String ing1, String ing2, String ing3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("pictures", picture);
        contentValues.put("instructions", instructions);
        contentValues.put("ing1", ing1);
        contentValues.put("ing2", ing2);
        contentValues.put("ing3", ing3);
        long result = db.insert("Cocktails",null,contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
     */

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cocktails", null);
        return cursor;
    }
}