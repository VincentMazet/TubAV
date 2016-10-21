package com.example.iem.tubbourg_en_bresse.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.iem.tubbourg_en_bresse.Entities.Stop;

/**
 * Created by iem on 18/10/2016.
 */

public class StopRepositoryImpl implements StopRepository {

    private static final String TABLE_NAME = "stop";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME ="name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    public static final String CREATE_TABLE_STOP =
            String.format("CREATE TABLE %s ( %s INTEGER primary key, %s TEXT, %s TEXT, %s TEXT, %s TEXT );",
                    TABLE_NAME,
                    KEY_ID,
                    KEY_DESCRIPTION,
                    KEY_NAME,
                    KEY_LATITUDE,
                    KEY_LONGITUDE);

    private MySQLite dbSQLite;
    private SQLiteDatabase db;

    public StopRepositoryImpl(Context context)
    {
        dbSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        db = dbSQLite.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    @Override
    public long addStop(Stop stop) {

        ContentValues values = new ContentValues();

        values.put(KEY_ID, stop.getId());
        values.put(KEY_NAME, stop.getName());
        values.put(KEY_DESCRIPTION, stop.getDescription());
        values.put(KEY_LATITUDE, stop.getGpsCoord().latitude);
        values.put(KEY_LONGITUDE, stop.getGpsCoord().longitude);

        return db.insert(TABLE_NAME,null,values);
    }

    @Override
    public int modStop(Stop stop) {

        ContentValues values = new ContentValues();

        values.put(KEY_ID, stop.getId());
        values.put(KEY_NAME, stop.getName());
        values.put(KEY_DESCRIPTION, stop.getDescription());
        values.put(KEY_LATITUDE, stop.getGpsCoord().latitude);
        values.put(KEY_LONGITUDE, stop.getGpsCoord().longitude);

        String where = KEY_ID + " = ?";
        String[] whereArgs = { stop.getId() + "" };

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    @Override
    public int supStop(Stop stop) {

        String where = KEY_ID + " = ?";
        String[] whereArgs = { stop.getId() + "" };

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    @Override
    public Stop getStop(int id) {

        Stop stop = new Stop();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",TABLE_NAME, KEY_ID, id), null);

        if (cursor.moveToFirst()) {

            stop.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            stop.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            stop.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
            stop.setGpsCoord(
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_NAME))),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_NAME)))
                    );

            cursor.close();
        }

        return stop;
    }

    @Override
    public Cursor getAllStop() {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

}
