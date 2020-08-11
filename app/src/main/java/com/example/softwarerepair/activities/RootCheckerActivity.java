package com.example.softwarerepair.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.utils.DeviceRootedClass;

public class RootCheckerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_checker);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        TextView rootCheck_tv = findViewById(R.id.rootCheck_tv);

        if (DeviceRootedClass.isRooted()) {
            rootCheck_tv.setText("Your device is rooted");
         } else {
            rootCheck_tv.setText("Your device isn't rooted");
         }
    }
}