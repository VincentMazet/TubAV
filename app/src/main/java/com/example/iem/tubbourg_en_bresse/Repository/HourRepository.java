package com.example.iem.tubbourg_en_bresse.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.iem.tubbourg_en_bresse.Entities.Hour;
import com.example.iem.tubbourg_en_bresse.Entities.Stop;

import java.util.Date;

/**
 * Created by iem on 19/10/2016.
 */

public interface HourRepository {

    long addHour(Hour hour);

    int modHour(Hour hour);

    int supHour(Hour hour);

    Hour getHour(int id);

    Cursor getAllHour();
}
