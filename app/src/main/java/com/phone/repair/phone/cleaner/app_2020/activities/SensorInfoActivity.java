package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;

import java.util.List;

public class SensorInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencor_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LinearLayout sensor_LL = findViewById(R.id.sensor_LL);

        // Get the SensorManager
        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // List of Sensors Available
        List<Sensor> mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        // Print each Sensor available using sSensList as the String to be printed
         Sensor tmp;
        int  i;
        for (i = 0; i < mSensorList.size(); i++) {

            tmp = mSensorList.get(i);
//            sSensList = " " + sSensList + tmp.getName() + "\n"; // Add the sensor name to the string of sensors available
//            viewArrayList = new ArrayList<>(mSensorList.size());
            View view = LayoutInflater.from(this).inflate(R.layout.sensor_item_layout, null);
            TextView title_textView = view.findViewById(R.id.title_textView);
            title_textView.setText(tmp.getName());
            sensor_LL.addView(view);

        }
    }
}