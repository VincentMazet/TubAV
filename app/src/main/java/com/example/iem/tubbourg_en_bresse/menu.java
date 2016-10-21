package com.example.iem.tubbourg_en_bresse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }
    public void goToMenuTrajet(View v){
        Intent intent = new Intent(this,menuTrajet.class);
        startActivity(intent);
    }
    public void goToLigne(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
