package com.example.iem.tubbourg_en_bresse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class menuTrajet extends AppCompatActivity {
    static final int STOP_REQUEST = 0;
    static final int STOP_REQUEST_END = 1;
    Button btn5;
    Button btn6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_trajet);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
    }
    public void goToSelectStops(View v){
        Intent myIntent = new Intent(this, selectStops.class);
                startActivityForResult(myIntent, STOP_REQUEST);
    }
    public void goToSelectStopsEnd(View v){
        Intent myIntent = new Intent(this, selectStops.class);
        myIntent.putExtra("EndStops",true);
        startActivityForResult(myIntent, STOP_REQUEST_END);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == STOP_REQUEST && resultCode == RESULT_OK)
        {
            btn5.setText("Départ : "+data.getStringExtra("result"));
        }
        if (requestCode == STOP_REQUEST_END && resultCode == RESULT_OK)
        {
            btn6.setText("Arrivé : "+data.getStringExtra("result"));
        }
    }
}
