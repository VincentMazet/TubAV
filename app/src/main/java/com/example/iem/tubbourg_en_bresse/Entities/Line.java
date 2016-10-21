package com.example.iem.tubbourg_en_bresse.Entities;

import java.util.List;

/**
 * Created by iem on 18/10/2016.
 */

public class Line {

    private int id;
    private String name;
    private String description;
    private List<Stop> stops;

    public Line(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}
