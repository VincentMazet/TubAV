package com.example.iem.tubbourg_en_bresse.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.iem.tubbourg_en_bresse.Entities.Line;
import com.example.iem.tubbourg_en_bresse.Entities.Stop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iem on 19/10/2016.
 */

public class LineRepositoryImpl implements LineRepository {

    //Inject
    private StopRepository stopRepository;

    private static final String TABLE_NAME = "line";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME ="name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_STOPS = "stops";



    public static final String CREATE_TABLE_LINE =
            String.format("CREATE TABLE %s ( %s INTEGER primary key, %s TEXT, %s TEXT, %s TEXT );",
                    TABLE_NAME,
                    KEY_ID,
                    KEY_NAME,
                    KEY_DESCRIPTION,
                    KEY_STOPS);

    private MySQLite dbSQLite;
    private SQLiteDatabase db;

    public LineRepositoryImpl(Context context)
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
    public long addLine(Line line) {

        ContentValues values = new ContentValues();

        values.put(KEY_ID, line.getId());
        values.put(KEY_NAME, line.getName());
        values.put(KEY_DESCRIPTION, line.getDescription());

        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        try {
            for(Stop stop : line.getStops()){
                jo.put("idStop", stop.getId());
                ja.put(jo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("stops", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        values.put(KEY_STOPS, mainObj.toString());


        return db.insert(TABLE_NAME, null, values);
    }

    @Override
    public int modLine(Line line) {

        ContentValues values = new ContentValues();

        values.put(KEY_ID, line.getId());
        values.put(KEY_NAME, line.getName());
        values.put(KEY_DESCRIPTION, line.getDescription());

        ObjectMapper objMapper = new ObjectMapper();
        List<Integer> idStops = new ArrayList<>();
        for(Stop stop : line.getStops()){
            idStops.add(stop.getId());
        }
        String jsonString = "";
        try {
            jsonString = objMapper.writeValueAsString(idStops);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        values.put(KEY_STOPS, jsonString);

        String where = KEY_ID + " = ?";
        String[] whereArgs = { line.getId() + "" };

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    @Override
    public int supLine(Line line) {

        String where = KEY_ID + " = ?";
        String[] whereArgs = { line.getId() + "" };

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    @Override
    public Line getLine(int id) {

        Line line = new Line();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s",TABLE_NAME, KEY_ID, id), null);

        if (cursor.moveToFirst()) {

            line.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            line.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            line.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));

            ObjectMapper mapper = new ObjectMapper();
            List<Integer> results = new ArrayList<>();
            try {
                results = mapper.readValue(cursor.getString(cursor.getColumnIndex(KEY_STOPS)), new TypeReference<List<Integer>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Stop> stops = new ArrayList<>();

            for(Integer integer: results){
                stops.add(stopRepository.getStop(integer));
            }

            line.setStops(stops);


            cursor.close();
        }

        return line;
    }

    @Override
    public Cursor getAllLine() {

        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
