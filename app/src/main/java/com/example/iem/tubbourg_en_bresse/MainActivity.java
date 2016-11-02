package com.example.iem.tubbourg_en_bresse;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.iem.tubbourg_en_bresse.Entities.Stop;
import com.example.iem.tubbourg_en_bresse.Repository.StopRepository;
import com.example.iem.tubbourg_en_bresse.Repository.StopRepositoryImpl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MapView mapView;
    KmlLayer layer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        StopRepositoryImpl stopRepo = new StopRepositoryImpl(this);
        // ouverture de la table en lecture/écriture
        stopRepo.open();
        // insertion. L'id sera attribué automatiquement par incrément
        stopRepo.addStop(new Stop("Test","ceci est un stop de test",new LatLng(46.207337, 5.227646)));


        // Listing des enregistrements de la table
        Cursor c = stopRepo.getAllStop();
        if (c.moveToFirst())
        {
            do {
                ArrayList<String> mArrayList = new ArrayList<String>();
                Cursor cursor = stopRepo.getAllStop();
                while(cursor.moveToNext()) {
                    mArrayList.add(cursor.getString(cursor.getColumnIndex("name"))); //add the item
                }

                Log.d("VALEUR",""+ mArrayList.get(0));
            }
            while (c.moveToNext());
        }
        c.close();
        stopRepo.close();


        // Gets to GoogleMap from the MapView and does initialization stuff

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                try {
                    layer = new KmlLayer(map, R.raw.monparcours, getApplicationContext());
                    layer.addLayerToMap();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
mapView.onResume();

    }
}
