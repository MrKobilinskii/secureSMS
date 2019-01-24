package com.develop.daniil.securesms.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_TEXT = "text";
    public static final String KEY_TIME = "time";
    public static final String KEY_TYPE = "type";


    public static final String TABLE_MESSAGES = "messages";

    public static final String MESSAGE_ID = "_id";
    public static final String MESSAGE_NUMBER = "number";
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_TIME = "time";
    public static final String MESSAGE_TYPE = "type";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key," + KEY_NUMBER + " text,"
                + KEY_TEXT + " text," + KEY_TIME + " text," + KEY_TYPE + " text" + ")");

        db.execSQL("create table " + TABLE_MESSAGES + "(" + MESSAGE_ID
                + " integer primary key," + MESSAGE_NUMBER + " text,"
                + MESSAGE_TEXT + " text," + MESSAGE_TIME + " text," + MESSAGE_TYPE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        db.execSQL("drop table if exists " + TABLE_MESSAGES);

        onCreate(db);
    }
}
