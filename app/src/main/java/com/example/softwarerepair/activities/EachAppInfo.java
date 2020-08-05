package com.example.softwarerepair.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.annotations.StringsAnnotations;
import com.example.softwarerepair.utils.ApplicationUtility;
import com.example.softwarerepair.utils.MathCalculationsUtil;
import com.example.softwarerepair.utils.TimeUtil;

public class EachAppInfo extends AppCompatActivity {

    ApplicationUtility applicationUtility;
    TimeUtil timeUtil ;
    MathCalculationsUtil mathCalculationsUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_app_info);
        applicationUtility = new ApplicationUtility(this);
        timeUtil = new TimeUtil();
        mathCalculationsUtil = new MathCalculationsUtil();
        String pkgName = getIntent().getStringExtra("pkg");

        String appVersion = applicationUtility.getAppInformation(pkgName, StringsAnnotations.APP_VERSION);
        String appName = applicationUtility.getAppName(pkgName);
        String appInstalledTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName,false));
        String appLastUpdateTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName,true));
        try {
            String appSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix((float)applicationUtility.getAppSize(pkgName));
            Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}