package com.phone.repair.phone.cleaner.app_2020.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.annotations.StringsAnnotations;
import com.phone.repair.phone.cleaner.app_2020.utils.ApplicationUtility;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class BoosterRamActivity extends AppCompatActivity {
    ApplicationUtility applicationUtility;
     ArrayList<Drawable> iconList;
      ImageView iv_icon;
     Handler handler;
     GifImageView gifImageView;
    TextView tv_wait;
    ArrayList<String> apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booster_ram);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        applicationUtility = new ApplicationUtility(this);
        iv_icon = findViewById(R.id.iv_icon);
        gifImageView = findViewById(R.id.gifImageView);
        tv_wait = findViewById(R.id.tv_wait);

        apps = new ArrayList<>();
        apps.addAll(applicationUtility.getAllActiveApps());
        iconList = new ArrayList<>();


        int size = apps.size() > 100 ?  100 : apps.size();
    for (int i=0; i<size; i++ )
        {
            iconList.add(applicationUtility.getAppInformation(apps.get(i), StringsAnnotations.APP_ICON));
        }
    handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;
            public void run() {
                iv_icon.setImageDrawable(iconList.get(i));
                i++;
                if (i > iconList.size() - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(runnable, 100);
        handler.postDelayed(() -> new BoosterRam().execute(),10000);

    }

    class BoosterRam extends AsyncTask<Void, Integer, String> {
         ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        View view;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             view = findViewById(R.id.v_finish);

        }

        @Override
        protected String doInBackground(Void... voids) {

            for (int i = 0; i < apps.size(); i++) {
                am.killBackgroundProcesses(apps.get(i));
                publishProgress(i);
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            view.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);

            tv_wait.setVisibility(View.GONE);
            gifImageView.setVisibility(View.GONE);
            iv_icon.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     }
}