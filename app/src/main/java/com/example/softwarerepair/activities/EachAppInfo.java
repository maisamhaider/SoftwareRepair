package com.example.softwarerepair.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.annotations.StringsAnnotations;
import com.example.softwarerepair.utils.ApplicationUtility;
import com.example.softwarerepair.utils.MathCalculationsUtil;
import com.example.softwarerepair.utils.TimeUtil;

public class EachAppInfo extends AppCompatActivity {

    private ApplicationUtility applicationUtility;
    private TimeUtil timeUtil;
    private MathCalculationsUtil mathCalculationsUtil;
    private TextView tvAppName, tvVersion, tvSize, tvInstallTime, tvUpdateTime;
    private ImageView ivAppIcon;
    private LinearLayout llOpenApp, llOpenSetting, llCheckOnStore;
    private String pkgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_app_info);

        ivAppIcon = findViewById(R.id.iv_icon);
        tvAppName = findViewById(R.id.app_name_tv);
        tvVersion = findViewById(R.id.version_tv);
        tvSize = findViewById(R.id.tv_size);
        tvInstallTime = findViewById(R.id.install_time_tv);
        tvUpdateTime = findViewById(R.id.update_time_tv);
        llOpenApp = findViewById(R.id.open_app_ll);
        llCheckOnStore = findViewById(R.id.check_store_ll);
        llOpenSetting = findViewById(R.id.open_setting_ll);

        applicationUtility = new ApplicationUtility(this);
        timeUtil = new TimeUtil();
        mathCalculationsUtil = new MathCalculationsUtil();
        pkgName = getIntent().getStringExtra("pkg");

        String appVersion = applicationUtility.getAppInformation(pkgName, StringsAnnotations.APP_VERSION);
        String appName = applicationUtility.getAppName(pkgName);
        String appInstalledTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName, false));
        String appLastUpdateTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName, true));
        Drawable iconDrawable = applicationUtility.getAppInformation(pkgName, StringsAnnotations.APP_ICON);

        tvAppName.setText(appName);
        tvVersion.setText(appVersion);
        tvInstallTime.setText(appInstalledTime);
        tvUpdateTime.setText(appLastUpdateTime);
        ivAppIcon.setImageDrawable(iconDrawable);

        try {
            String appSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix((float) applicationUtility.getAppSize(pkgName));
            tvSize.setText(appSize);
            Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        llOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pkgName);
                startActivity( launchIntent );
            }
        });
        llCheckOnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=" + pkgName);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        llOpenSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}