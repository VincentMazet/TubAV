package com.example.iem.tubbourg_en_bresse.Repository;

/**
 * Created by iem on 18/10/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static MySQLite sInstance;

    public static synchronized MySQLite getInstance(Context context) {
        if (sInstance == null) { sInstance = new MySQLite(context); }
        return sInstance;
    }

    private MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(StopRepositoryImpl.CREATE_TABLE_STOP);
        sqLiteDatabase.execSQL(HourRepositoryImpl.CREATE_TABLE_HOUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        onCreate(sqLiteDatabase);
    }

}