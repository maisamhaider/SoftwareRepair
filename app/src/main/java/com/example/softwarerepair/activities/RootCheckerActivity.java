package com.example.softwarerepair.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.utils.DeviceRootedClass;

public class RootCheckerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_checker);

        if (DeviceRootedClass.isRooted()) {
            Toast.makeText(RootCheckerActivity.this, "Device is rooted ", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(RootCheckerActivity.this, "Device is not rooted", Toast.LENGTH_SHORT).show();
        }
    }
}