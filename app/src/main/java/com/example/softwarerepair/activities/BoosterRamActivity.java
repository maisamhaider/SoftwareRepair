package com.example.softwarerepair.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.utils.ApplicationUtility;

import java.util.List;

public class BoosterRamActivity extends AppCompatActivity {
    ApplicationUtility applicationUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booster_ram);
        applicationUtility = new ApplicationUtility(this);


        class BoosterRam extends AsyncTask<Void, Integer, String> {
            List<String> packageList;
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                packageList = applicationUtility.getSysActiveApps(true);

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
            }
        }

    }
}