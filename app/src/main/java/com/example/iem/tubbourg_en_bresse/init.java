package com.example.iem.tubbourg_en_bresse;

/**
 * Created by iem on 02/11/2016.
 */
public class init {
    private static init ourInstance = new init();

    public static init getInstance() {
        return ourInstance;
    }

    private init() {
    }
}
