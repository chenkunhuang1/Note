package com.example.chen.hellonotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.ColorMatrix;

/**
 * Created by chen on 2017/5/3.
 */

public class NotesDB extends SQLiteOpenHelper {
    //创建数据库需要的名称
    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String ID = "_id";
    public static final String PATH = "path";
    public static final String VIDEO = "video";
    public static final String TIME = "time";

    public NotesDB(Context context) {
        super(context, "notes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
                + " TEXT NOT NULL,"
                + PATH + " TEXT NOT NULL," + VIDEO + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL)"
        );
       /* db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
                + " TEXT NOT NULL,"  + TIME + " TEXT NOT NULL)");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
