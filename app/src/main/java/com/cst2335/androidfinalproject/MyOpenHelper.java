package com.cst2335.androidfinalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final String COL_DRINK_ID = "drink_id";
    public static final String COL_PICTURE = "picture";
    public static final String COL_INSTRUCTIONS = "instructions";
    public static final String COL_INGREDIENT1 = "ingredient1";
    public static final String COL_INGREDIENT2 = "ingredient2";
    public static final String COL_INGREDIENT3 = "ingredient3";


    /**
     * build the database
     *
     * @param ctx
     */
    public MyOpenHelper(Context ctx) {

        super(ctx, FILENAME, null, VERSION_NUM);
    }

    /**
     * creates the cocktail database
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * creating the table for the database with 6 columns
         */
        Log.i(ACTIVITY_NAME, "OnCreate");

        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DRINK_ID + " TEXT NOT NULL,"
                + COL_PICTURE + " TEXT,"
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

    public boolean queryCock(String idDrink) {
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " where " + COL_DRINK_ID + " = ?", new String[]{idDrink});
        boolean isHas = false;
        if (cursor != null && cursor.getCount() > 0){
            isHas = true;
            cursor.close();
        }
        return isHas;
    }

    public void insertCock(String idDrink,
                           String picture,
                           String instructions,
                           String ingredient1,
                           String ingredient2,
                           String ingredient3) {
        String sql = "insert into " + TABLE_NAME + " (" + COL_DRINK_ID + "," + COL_PICTURE + "," + COL_INSTRUCTIONS + "," + COL_INGREDIENT1 + "," + COL_INGREDIENT2 + "," + COL_INGREDIENT3 + ") values (?, ?, ?, ?, ?, ?)";
        getWritableDatabase().execSQL(sql, new Object[]{idDrink, picture, instructions, ingredient1, ingredient2, ingredient3});
    }

    public void deleteCock(String idDrink) {
        getWritableDatabase().execSQL("delete from " + TABLE_NAME + " where " + COL_DRINK_ID + " = ?", new Object[]{idDrink});
    }

    @SuppressLint("Range")
    public List<Map<String, Object>> queryAll() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME, null);
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put(COL_ID, cursor.getLong(cursor.getColumnIndex(COL_ID)));
                map.put(COL_DRINK_ID, cursor.getString(cursor.getColumnIndex(COL_DRINK_ID)));
                map.put(COL_PICTURE, cursor.getString(cursor.getColumnIndex(COL_PICTURE)));
                map.put(COL_INSTRUCTIONS, cursor.getString(cursor.getColumnIndex(COL_INSTRUCTIONS)));
                map.put(COL_INGREDIENT1, cursor.getString(cursor.getColumnIndex(COL_INGREDIENT1)));
                map.put(COL_INGREDIENT2, cursor.getString(cursor.getColumnIndex(COL_INGREDIENT2)));
                map.put(COL_INGREDIENT3, cursor.getString(cursor.getColumnIndex(COL_INGREDIENT3)));
                mapList.add(map);
            }
            cursor.close();
        }
        db.close();
        return mapList;
    }
}