package com.example.iem.tubbourg_en_bresse.Entities;

import java.util.Date;

/**
 * Created by iem on 18/10/2016.
 */

public class Hour {

    private int id;
    private Stop stop;
    private Line line;
    private Date hour;

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

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }
}
