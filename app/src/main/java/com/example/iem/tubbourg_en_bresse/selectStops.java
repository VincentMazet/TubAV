package com.example.iem.tubbourg_en_bresse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iem.tubbourg_en_bresse.Entities.Line;
import com.example.iem.tubbourg_en_bresse.Entities.Stop;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectStops extends AppCompatActivity {
    MapView mapView;
    Button btn8;
    String arret;
    Stop gare;
    Stop vicaire;
    Stop chariteUniversitaire;
    Line ligne5;
    List<Stop> ls;
    KmlLayer layer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stops);
        gare = new Stop();
        vicaire = new Stop();
        chariteUniversitaire = new Stop();
        ligne5 = new Line();
        ligne5.setId(5);
        ligne5.setName("Ligne 5");
        ligne5.setDescription("La description de la ligne 5");

        gare.setId(1);gare.setName("Gare");gare.setDescription("Gare des bus");gare.setGpsCoord(46.207337, 5.227646);
        vicaire.setId(2);vicaire.setName("Vicaire");vicaire.setDescription("Arrêt Vicaire");vicaire.setGpsCoord(46.207929, 5.224553);
        chariteUniversitaire.setId(3);chariteUniversitaire.setName("Charité Universitaire");chariteUniversitaire.setDescription("Arrêt Charité Universitaire");chariteUniversitaire.setGpsCoord(46.207634, 5.219873);
        ls = new ArrayList<>();
        ls.add(gare);
        ls.add(vicaire);
        ls.add(chariteUniversitaire);

        mapView = (MapView) findViewById(R.id.mapView2);
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
                map.addMarker(new MarkerOptions()
                        .title(vicaire.getName())
                        .position(vicaire.getGpsCoord()));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(gare.getGpsCoord(), 13));

                map.addMarker(new MarkerOptions()
                        .title(gare.getName())
                        .snippet(gare.getDescription())
                        .position(gare.getGpsCoord()));
                map.addMarker(new MarkerOptions()
                        .title(chariteUniversitaire.getName())
                        .position(chariteUniversitaire.getGpsCoord()));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        arret = marker.getTitle();
                        btn8.setText("Arrêt : "+arret);
                        return false;
                    }
                });

            }
        });
        mapView.onResume();
        btn8 = (Button) findViewById(R.id.button8);


    }

    public void arretIsSelect(View v) {

            if (arret != null) {
                finish();
            } else {
                Toast.makeText(this, "Pas d'arrêt sélectionné", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void finish(){
        if (getIntent().getBooleanExtra("EndStops",false))
        {
            Intent intentB = new Intent();
            intentB.putExtra("result", arret);
            setResult(RESULT_OK, intentB);
        }
        else {
            Intent intentB = new Intent();
            intentB.putExtra("result", arret);
            setResult(RESULT_OK, intentB);
        }
        super.finish();
    }
}


