package com.example.iem.tubbourg_en_bresse.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.iem.tubbourg_en_bresse.Entities.Hour;
import com.example.iem.tubbourg_en_bresse.Entities.Stop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iem on 19/10/2016.
 */

public class HourRepositoryImpl implements HourRepository {

    //Inject
    private StopRepository stopRepository;
    private LineRepository lineRepository;


    private static final String TABLE_NAME = "hour";
    private static final String KEY_ID = "id";
    private static final String KEY_STOP ="id_stop";
    private static final String KEY_LINE = "id_line";
    private static final String KEY_HOUR = "hour";



    public static final String CREATE_TABLE_HOUR =
            String.format("CREATE TABLE %s ( %s INTEGER primary key, %s INTEGER, %s INTEGER, %s DATE );",
                    TABLE_NAME,
                    KEY_ID,
                    KEY_STOP,
                    KEY_LINE,
                    KEY_HOUR);

    private MySQLite dbSQLite;
    private SQLiteDatabase db;

    public HourRepositoryImpl(Context context)
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
    public long addHour(Hour hour) {

        ContentValues values = new ContentValues();

        values.put(KEY_ID, hour.getId());
        values.put(KEY_STOP, hour.getStop().getId());
        values.put(KEY_LINE, hour.getLine().getId());
        values.put(KEY_HOUR, hour.getHour().toString());

        return db.insert(TABLE_NAME,null,values);
    }

    @Override
    public int modHour(Hour hour) {

        ContentValues values = new ContentValues();

        values.put(KEY_ID, hour.getId());
        values.put(KEY_STOP, hour.getStop().getId());
        values.put(KEY_LINE, hour.getLine().getId());
        ObjectMapper mapper = new ObjectMapper();

        String json = "";
        try {
            json = mapper.writeValueAsString(hour.getHour());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        values.put(KEY_HOUR, json);

        String where = KEY_ID + " = ?";
        String[] whereArgs = { hour.getId() + "" };

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    @Override
    public int supHour(Hour hour) {

        String where = KEY_ID + " = ?";
        String[] whereArgs = { hour.getId() + "" };

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    @Override
    public Hour getHour(int id) {

        Hour hour = new Hour();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",TABLE_NAME, KEY_ID, id), null);

        if (cursor.moveToFirst()) {

            hour.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            hour.setStop(stopRepository.getStop(cursor.getInt(cursor.getColumnIndex(KEY_STOP))));
            hour.setLine(lineRepository.getLine(cursor.getInt(cursor.getColumnIndex(KEY_LINE))));
            hour.setHour(cursor.getString(cursor.getColumnIndex(KEY_HOUR)));

            cursor.close();
        }

        return hour;
    }

    @Override
    public Cursor getAllHour() {

        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
