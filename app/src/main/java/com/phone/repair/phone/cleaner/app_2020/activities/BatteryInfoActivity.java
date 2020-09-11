package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.utils.MathCalculationsUtil;
import com.skydoves.progressview.ProgressView;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;

public class BatteryInfoActivity extends AppCompatActivity {

    TextView powerSource_tv, temperature_tv, bVoltage_tv, bStatus_tv, bHealth_tv;
    MathCalculationsUtil mathCalculationsUtil;
    CircularProgressBar circularProgressBar;
    ProgressView progressView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mathCalculationsUtil = new MathCalculationsUtil();
        bHealth_tv = findViewById(R.id.bHealth_tv);
        TextView batteryType_tv = findViewById(R.id.batteryType_tv);
        powerSource_tv = findViewById(R.id.powerSource_tv);
        temperature_tv = findViewById(R.id.temperature_tv);
        bVoltage_tv = findViewById(R.id.bVoltage_tv);
        bStatus_tv = findViewById(R.id.bStatus_tv);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        progressView1 = findViewById(R.id.progressView1);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            progressView1.setOrientation(ProgressViewOrientation.VERTICAL);
//        } else {
//            progressView1.setOrientation(ProgressViewOrientation.HORIZONTAL);
//        }
        circularProgressBar.setProgressMax(100f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);
        circularProgressBar.setProgressBarWidth(8f); // in DP
        circularProgressBar.setBackgroundProgressBarWidth(8f); // in DP
        progressView1.setAnimating(true);
        progressView1.setMax(100);


        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(this);
        if (easyBatteryMod.isBatteryPresent()) {
            bHealth_tv.setText(String.valueOf(easyBatteryMod.getBatteryHealth()));
            batteryType_tv.setText(String.valueOf(easyBatteryMod.getBatteryTechnology()));
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            int batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int bHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int bSource = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int bVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int bLevel = (int) (((float) level / (float) scale) * 100.0f);

            circularProgressBar.setProgressWithAnimation(bLevel, (long) 1000);
            progressView1.setProgress(bLevel);
            progressView1.setLabelText(bLevel + "%");
            if (bLevel == 100) {
                progressView1.setLabelText("Full");


            } else if (bLevel < 15) {
                progressView1.setLabelText("Low battery");
            }

            {
                if (batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
                    bStatus_tv.setText("Charging");
                } else if (batteryStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                    bStatus_tv.setText("Not Charging");
                } else if (batteryStatus == BatteryManager.BATTERY_STATUS_FULL) {
                    bStatus_tv.setText("Battery is full");
                } else if (batteryStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                    bStatus_tv.setText("Unknown");
                } else if (batteryStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    bStatus_tv.setText("Discharging");
                }

                if (bHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                    bHealth_tv.setText("Cold");
                } else if (bHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                    bHealth_tv.setText("Dead");
                } else if (bHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                    bHealth_tv.setText("Good");
                } else if (bHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                    bHealth_tv.setText("Over Voltage");
                } else if (bHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                    bHealth_tv.setText("Unknown");
                } else if (bHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                    bHealth_tv.setText("Unspecified Failure");
                }


                int temp = 0;
                if (intent != null) {
                    temp = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10);
                }
                temperature_tv.setText(temp + "°C" + "-" + mathCalculationsUtil.getCelsiusToFahrenheit(temp) + "°F");

                if (bSource == BatteryManager.BATTERY_PLUGGED_AC) {
                    powerSource_tv.setText("AC");
                } else if (bSource == BatteryManager.BATTERY_PLUGGED_USB) {
                    powerSource_tv.setText("USB");
                } else if (bSource == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                    powerSource_tv.setText("WIRELESS");
                } else
                    powerSource_tv.setText("Unplugged");
            }

            bVoltage_tv.setText(bVoltage + "mV");
        }
    };
}