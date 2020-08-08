package com.example.softwarerepair.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
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
    TextView tvAppName,tvVersion,tvSize,tvInstallTime,tvUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_app_info);

        tvAppName = findViewById(R.id.app_name_tv);
        tvVersion = findViewById(R.id.version_tv);
        tvSize = findViewById(R.id.tv_size);
        tvInstallTime = findViewById(R.id.install_time_tv);
        tvUpdateTime = findViewById(R.id.update_time_tv);

        applicationUtility = new ApplicationUtility(this);
        timeUtil = new TimeUtil();
        mathCalculationsUtil = new MathCalculationsUtil();
        String pkgName = getIntent().getStringExtra("pkg");

        String appVersion = applicationUtility.getAppInformation(pkgName, StringsAnnotations.APP_VERSION);
        String appName = applicationUtility.getAppName(pkgName);
        String appInstalledTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName,false));
        String appLastUpdateTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName,true));

        tvAppName.setText(appName);
        tvVersion.setText(appVersion);
        tvInstallTime.setText(appInstalledTime);
        tvUpdateTime.setText(appLastUpdateTime);

        try {
            String appSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix((float)applicationUtility.getAppSize(pkgName));
            tvSize.setText(appSize);
            Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }
}