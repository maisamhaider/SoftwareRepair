package com.phone.repair.phone.cleaner.app_2020.activities;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.adapters.RepairSystemAdapter;
import com.phone.repair.phone.cleaner.app_2020.annotations.StringsAnnotations;
import com.phone.repair.phone.cleaner.app_2020.models.FileModel;
import com.phone.repair.phone.cleaner.app_2020.utils.ApplicationUtility;
import com.phone.repair.phone.cleaner.app_2020.utils.StorageUtility;
import com.phone.repair.phone.cleaner.app_2020.utils.TimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepairSystemActivity extends AppCompatActivity {
    ProgressBar repairSystem_pb;
    ApplicationUtility applicationUtility ;
    List<String> apps = new ArrayList<>();
    TimeUtil timeUtil;
    Handler handler;
    String pkg;
    RecyclerView repairSystem_rv;
    LinearLayoutManager layoutManager;
    RepairSystemAdapter repairSystemAdapter;
    View repairFinish_view;
    TextView textView18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_system);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        repairSystem_pb = findViewById(R.id.repairSystem_pb);
        applicationUtility = new ApplicationUtility(this);
        timeUtil = new TimeUtil();
        handler = new Handler();
        repairSystemAdapter = new RepairSystemAdapter(this);

        repairSystem_rv = findViewById(R.id.repairSystem_rv);
        repairFinish_view = findViewById(R.id.repairFinish_view);
        textView18 = findViewById(R.id.textView18);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        repairSystem_rv.setLayoutManager(layoutManager);
        apps = applicationUtility.getAllActiveApps();


        new RepairSystem().execute();
    }

    class RepairSystem extends AsyncTask<Void, Integer, String> {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        StorageUtility storageUtility = new StorageUtility();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            repairSystem_pb.setMax(apps.size());
            startAnimation();
        }

        @Override
        protected String doInBackground(Void... voids) {
             for (int i = 0; i < apps.size(); i++) {
                 activityManager.killBackgroundProcesses(apps.get(i));
                 publishProgress(i);
             }
            for (FileModel item : storageUtility.getAllUnUsableFile(path)) {
                File file = new File(item.getFilePath());
                if (file.exists()) {
                    file.delete();
                }
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

    public void repairAppsAdapter(String appName, String installedTime, Drawable icon) {
        repairSystem_rv.setAdapter(repairSystemAdapter);
        repairSystemAdapter.setScanningData(appName, installedTime, icon);
    }

    private void startAnimation() {


         ValueAnimator animator = ValueAnimator.ofInt(0, apps.size()-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setStartDelay(0);
        animator.setDuration(30_000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                repairSystem_pb.setProgress(value);
                pkg = apps.get(value);

                Drawable icon =  applicationUtility.getAppInformation(pkg, StringsAnnotations.APP_ICON);
                String name =  applicationUtility.getAppInformation(pkg, StringsAnnotations.APP_NAME);
                String time = timeUtil.appInstalledTime(applicationUtility.getAppTime(pkg,StringsAnnotations.INSTALLATION));
                repairAppsAdapter(name,time,icon);
                 if ((apps.size()-1)==value){
                  //TODO
                     repairSystem_pb.setVisibility(View.GONE);
                     repairSystem_rv.setVisibility(View.GONE);
                     textView18.setVisibility(View.GONE);
                     repairFinish_view.setVisibility(View.VISIBLE);
                }

            }
        });

        animator.start();
    }
}