package com.cst2335.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String FILENAME = "MyDatabase";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "MyData";
    public static final String COL_ID = "_id";
    public static final String COL_MESSAGE = "Message";
    public static final String COL_SEND_RECEIVE = "SendOrReceive";

    public MyOpenHelper(Context ctx) {
        super(ctx, FILENAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("Create table %s(%s integer primary key autoincrement, %s text, %s INTEGER);"
                ,TABLE_NAME, COL_ID, COL_MESSAGE, COL_SEND_RECEIVE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TABLE_NAME);
        this.onCreate(db);
    }
}