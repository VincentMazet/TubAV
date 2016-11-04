package com.example.iem.tubbourg_en_bresse.Entities;

import java.util.Date;
import java.util.List;

import static android.R.id.list;

/**
 * Created by iem on 18/10/2016.
 */

public class Hour {

    private int id;
    private Stop stop;
    private Line line;
    private String hour;

    public Hour(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
