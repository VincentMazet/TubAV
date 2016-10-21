package com.example.iem.tubbourg_en_bresse.Repository;

import android.database.Cursor;

import com.example.iem.tubbourg_en_bresse.Entities.Line;

/**
 * Created by iem on 19/10/2016.
 */

public interface LineRepository {
    long addLine(Line line);

    int modLine(Line line);

    int supLine(Line line);

    Line getLine(int id);

    Cursor getAllLine();
}
