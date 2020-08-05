package com.example.softwarerepair.utils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class DrawingSimpleAct extends AppCompatActivity {
    DrawingUtil drawingUtil ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawingUtil = new DrawingUtil( this,null );
        setContentView( drawingUtil );

    }
}
