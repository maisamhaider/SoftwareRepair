package com.phone.repair.phone.cleaner.app_2020.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.utils.ApplicationUtility;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class PhoneOptimizerActivity extends AppCompatActivity {
    ApplicationUtility applicationUtility;
    TextView textView59,operation_tv,textViewSuccess_tv;
    GifImageView phoneCooler_gv2,phoneCooler_gv1;
    ImageView phoneCoolSuccess_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_cooler);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        applicationUtility = new ApplicationUtility(this);

        phoneCooler_gv1 = findViewById(R.id.phoneCooler_gv1);
        phoneCooler_gv2 = findViewById(R.id.phoneCooler_gv2);
        textView59 = findViewById(R.id.textView59);

        phoneCoolSuccess_iv = findViewById(R.id.phoneCoolSuccess_iv);
        operation_tv = findViewById(R.id.operation_tv);
        textViewSuccess_tv = findViewById(R.id.textViewSuccess_tv);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              new PhoneCooler().execute();
            }
        },8000);


    }
   private class PhoneCooler extends AsyncTask<Void, Integer, String> {
        List<String> packageList;
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            packageList = applicationUtility.getSysActiveApps(false);

        }

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i < packageList.size(); i++) {
                am.killBackgroundProcesses(packageList.get(i));
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

            phoneCooler_gv1.setVisibility(View.GONE);
            phoneCooler_gv2.setVisibility(View.GONE);
            textView59.setVisibility(View.GONE);

            phoneCoolSuccess_iv.setVisibility(View.VISIBLE);
            operation_tv.setVisibility(View.VISIBLE);
            textViewSuccess_tv.setVisibility(View.VISIBLE);
        }
    }
}