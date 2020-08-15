package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;

public class DisplayInfoActivity extends AppCompatActivity {
    TextView tvSize,tvPhysicalSize,tvScreenWidth,tvScreenHeight,tvRefreshRate,tvName,tvXDpi,tvYDpi,tvLogicalDensity,tvScaleDensity,tvUsableWidth,tvUsableHeight;
    Button bDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        tvSize = findViewById(R.id.tv_size);
        tvPhysicalSize = findViewById(R.id.tv_physical_size);
        tvScreenWidth = findViewById(R.id.tv_screen_width);
        tvScreenHeight = findViewById(R.id.tv_screen_height);
        tvRefreshRate = findViewById(R.id.tv_refresh_rate);
        tvName = findViewById(R.id.tv_name);
        tvXDpi = findViewById(R.id.tv_xdpi);
        tvYDpi = findViewById(R.id.tv_ydpi);
        tvLogicalDensity = findViewById(R.id.tv_logical_density);
        tvScaleDensity = findViewById(R.id.tv_scale_density);
        tvUsableWidth = findViewById(R.id.tv_usable_width);
        tvUsableHeight = findViewById(R.id.tv_usable_height);
        bDisplay = findViewById(R.id.b_display_setting);


        String toastMsg;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                break;
            default:
                toastMsg = "Medium Screen size";
        }

        tvSize.setText(toastMsg);

        WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
//        display.getP
        String displayName = display.getName();
        tvName.setText(displayName);
        String displayHeight = display.getHeight() +"px";
        tvScreenHeight.setText(displayHeight);
        String displayWidth = display.getWidth() +"px";
        tvScreenWidth.setText(displayWidth);
        int id = display.getDisplayId();

        float refreshRate = display.getRefreshRate();
        tvRefreshRate.setText(String.valueOf(refreshRate));
        float scaleDensity =  metrics.scaledDensity;
        tvScaleDensity.setText(String.valueOf(scaleDensity));
        float logicalDensity =  metrics.density;
        tvLogicalDensity.setText(String.valueOf(logicalDensity));
        String  xdpi = metrics.xdpi +"dpi";
        tvXDpi.setText(xdpi);
        String  ydpi = metrics.ydpi +"dpi";
        tvYDpi.setText(ydpi);
        String width = metrics.widthPixels +"px";
        tvUsableWidth.setText(width);
        String height = metrics.heightPixels +"px";
        tvUsableHeight.setText(height);



        // since SDK_INT = 1;
      int  mWidthPixels = metrics.widthPixels;
      int  mHeightPixels = metrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
        {
            try
            {
                mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            }
            catch (Exception ignored)
            {
            }
        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
        {
            try
            {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                mWidthPixels = realSize.x;
                mHeightPixels = realSize.y;
            }
            catch (Exception ignored)
            {
            }
    }

        double x = Math.pow(mWidthPixels/metrics.xdpi,2);
        double y = Math.pow(mHeightPixels/metrics.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        Log.d("debug","Screen inches : " + screenInches);

        bDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, 0);
                if (resolveInfo != null) {
                    startActivity(intent);
                }
            }
        });

    }
}