package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.annotations.StringsAnnotations;
import com.phone.repair.phone.cleaner.app_2020.utils.ApplicationUtility;
import com.phone.repair.phone.cleaner.app_2020.utils.MathCalculationsUtil;
import com.phone.repair.phone.cleaner.app_2020.utils.TimeUtil;

public class EachAppInfo extends AppCompatActivity {

    private String pkgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_app_info);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ImageView ivAppIcon = findViewById(R.id.iv_icon);
        TextView tvAppName = findViewById(R.id.app_name_tv);
        TextView textView47 = findViewById(R.id.textView47);
        TextView tvVersion = findViewById(R.id.version_tv);
        TextView tvSize = findViewById(R.id.tv_size);
        TextView tvInstallTime = findViewById(R.id.install_time_tv);
        TextView tvUpdateTime = findViewById(R.id.update_time_tv);
        LinearLayout llOpenApp = findViewById(R.id.open_app_ll);
        LinearLayout llCheckOnStore = findViewById(R.id.check_store_ll);
        LinearLayout llOpenSetting = findViewById(R.id.open_setting_ll);

        ApplicationUtility applicationUtility = new ApplicationUtility(this);
        TimeUtil timeUtil = new TimeUtil();
        MathCalculationsUtil mathCalculationsUtil = new MathCalculationsUtil();
        pkgName = getIntent().getStringExtra("pkg");

        String appVersion = applicationUtility.getAppInformation(pkgName, StringsAnnotations.APP_VERSION);
        String appName = applicationUtility.getAppName(pkgName);
        String appInstalledTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName, false));
        String appLastUpdateTime = timeUtil.appInstalledTime(applicationUtility.getAppInstallTime(pkgName, true));
        Drawable iconDrawable = applicationUtility.getAppInformation(pkgName, StringsAnnotations.APP_ICON);

        tvAppName.setText(appName);
        textView47.setText(appName);
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
        llOpenApp.setOnClickListener(view -> {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pkgName);
            startActivity( launchIntent );
        });
        llCheckOnStore.setOnClickListener(view -> startActivity(new

                        Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pkgName))));
        llOpenSetting.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:"+pkgName));
            startActivity(intent);

        });


    }
}