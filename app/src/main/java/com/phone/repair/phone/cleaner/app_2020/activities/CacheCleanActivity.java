package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.models.FileModel;
import com.phone.repair.phone.cleaner.app_2020.permissions.MyPermissions;
import com.phone.repair.phone.cleaner.app_2020.utils.IosDialog;
import com.phone.repair.phone.cleaner.app_2020.utils.MathCalculationsUtil;
import com.phone.repair.phone.cleaner.app_2020.utils.StorageUtility;

import java.io.File;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class CacheCleanActivity extends AppCompatActivity {

    StorageUtility storageUtility;
    TextView unInstalledSize_tv, tempFiles_tv, logFiles_tv, cacheFile_tv, cleanCache_tv, totalCacheSize_tv1, cacheScanning_tv;
    MathCalculationsUtil mathCalculationsUtil;
    String path;
    MyPermissions permissions;
    CircularProgressIndicator cacheCircularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clean_activty);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        storageUtility = new StorageUtility();
        mathCalculationsUtil = new MathCalculationsUtil();
        permissions = new MyPermissions(this);

        permissions.permission();
        unInstalledSize_tv = findViewById(R.id.unInstalledSize_tv);
        tempFiles_tv = findViewById(R.id.tempFiles_tv);
        logFiles_tv = findViewById(R.id.logFiles_tv);
        cacheFile_tv = findViewById(R.id.cacheFile_tv);
        cleanCache_tv = findViewById(R.id.cleanCache_tv);
        totalCacheSize_tv1 = findViewById(R.id.totalCacheSize_tv1);
        cacheScanning_tv = findViewById(R.id.cacheScanning_tv);
        cacheCircularProgressBar = findViewById(R.id.cacheCircularProgressBar);


        path = Environment.getExternalStorageDirectory().getAbsolutePath();

        cleanCache_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.permission()) {
                    CacheClean cacheClean = new CacheClean();
                    cacheClean.execute();
                }
            }
        });
        cacheCircularProgressBar.setMaxProgress(storageUtility.getTotalStorage());
        new SearchCache().execute();
    }

    private void setScanningTvData(String text) {
        cacheScanning_tv.setText("");
        cacheScanning_tv.setText(text);
    }

    class CacheClean extends AsyncTask<Void, Integer, String> {

        IosDialog iosDialog = new IosDialog(CacheCleanActivity.this, "Cleaning");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            iosDialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {
            for (FileModel item : storageUtility.getAllUnUsableFile(path)) {
                File file = new File(item.getFilePath());
                if (file.exists()) {
                    file.delete();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            float totalSizeFloat = storageUtility.getLogFileSize(path)
                    + storageUtility.getTempFileSize(path) + storageUtility.getCachesSize(path) + storageUtility.getApkFileSize(path);
            String logFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getLogFileSize(path));
            String tempFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getTempFileSize(path));
            String cacheFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getCachesSize(path));
            String apkFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getApkFileSize(path));
            String totalSizeString = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalSizeFloat);

            totalCacheSize_tv1.setText(totalSizeString);
            unInstalledSize_tv.setText(apkFileSize);
            tempFiles_tv.setText(tempFileSize);
            logFiles_tv.setText(logFileSize);
            cacheFile_tv.setText(cacheFileSize);
            setScanningTvData(totalSizeString + " Cleaned");
            cacheCircularProgressBar.setProgress(totalSizeFloat, storageUtility.getTotalStorage());
            iosDialog.dismiss();
        }
    }

    class SearchCache extends AsyncTask<Void, Integer, String> {
        IosDialog  iosDialog = new IosDialog(CacheCleanActivity.this, "Cleaning");
        @Override
        protected String doInBackground(Void... voids) {
            getAllUnUsableFile(path);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            iosDialog.show();
            float totalSizeFloat = storageUtility.getLogFileSize(path)
                    + storageUtility.getTempFileSize(path)
                    + storageUtility.getCachesSize(path)
                    + storageUtility.getApkFileSize(path);

            String logFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getLogFileSize(path));
            String tempFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getTempFileSize(path));
            String cacheFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getCachesSize(path));
            String apkFileSize = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(storageUtility.getApkFileSize(path));
            String totalSizeString = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalSizeFloat);
            totalCacheSize_tv1.setText(totalSizeString);
            unInstalledSize_tv.setText(apkFileSize);
            tempFiles_tv.setText(tempFileSize);
            logFiles_tv.setText(logFileSize);
            cacheFile_tv.setText(cacheFileSize);
            setScanningTvData(totalSizeString + " Found");
            cacheCircularProgressBar.setProgress(totalSizeFloat, storageUtility.getTotalStorage());
            iosDialog.dismiss();
        }
    }

    public void getAllUnUsableFile(String path) {
        File fold = new File(path);
        File[] mlist = fold.listFiles();
        File[] mFilelist = fold.listFiles(new StorageUtility.AllUnUsableFilter());
        if (mlist != null) {
            for (File f : mlist) {
                if (f.isDirectory()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setScanningTvData(f.getAbsolutePath());

                        }
                    });
                    getAllUnUsableFile(f.getAbsolutePath());
                }
            }
        }
        if (mFilelist != null) {
            for (File f : mFilelist) {
                if (f.length() >= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setScanningTvData(f.getAbsolutePath());

                        }
                    });

                }
            }
        }
    }
}