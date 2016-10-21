package com.example.iem.tubbourg_en_bresse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MapView mapView;
    KmlLayer layer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

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
//                LatLng sydney = new LatLng(-33.867, 151.206);
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//                map.addMarker(new MarkerOptions()
//                        .title("Sydney")
//                        .snippet("The most populous city in Australia.")
//                        .position(sydney));

            }
        });
mapView.onResume();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
