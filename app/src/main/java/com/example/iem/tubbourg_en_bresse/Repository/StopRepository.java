package com.example.iem.tubbourg_en_bresse.Repository;

import android.database.Cursor;

import com.example.iem.tubbourg_en_bresse.Entities.Stop;

/**
 * Created by iem on 19/10/2016.
 */

public interface StopRepository {

    long addStop(Stop stop);

    int modStop(Stop stop);

    int supStop(Stop stop);

    Stop getStop(int id);

    Cursor getAllStop();
}
