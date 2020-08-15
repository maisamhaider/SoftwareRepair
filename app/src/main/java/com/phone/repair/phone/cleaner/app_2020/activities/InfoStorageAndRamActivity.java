package com.phone.repair.phone.cleaner.app_2020.activities;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.utils.MathCalculationsUtil;
import com.phone.repair.phone.cleaner.app_2020.utils.StorageUtility;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class InfoStorageAndRamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_storage_and_ram);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ramAndStorageFun();
    }

    private void ramAndStorageFun() {
        MathCalculationsUtil mathCalculationsUtil = new MathCalculationsUtil();
        StorageUtility storageUtility = new StorageUtility();


//for Ram
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        float totalMemory = mi.totalMem;
        float freeMemory = mi.availMem;
        float usedMemory = totalMemory - freeMemory;

        @SuppressLint("DefaultLocale") String ramUsagePercentage = String.format("%.1f", mathCalculationsUtil.getPercentageFloat(totalMemory, usedMemory)) + "%";
        TextView infoSystemAndAppsSize_tv = findViewById(R.id.infoSystemAndAppsSize_tv);
        TextView infoAvailableRamSize_tv = findViewById(R.id.infoAvailableRamSize_tv);
        TextView infoTotalRamSize_tv = findViewById(R.id.infoTotalRamSize_tv);
        TextView infoRamPercentage_tv = findViewById(R.id.infoRamPercentage_tv);
        CircularProgressBar infoRam_cpb = findViewById(R.id.infoRam_cpb);

        infoSystemAndAppsSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(usedMemory));
        infoAvailableRamSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(freeMemory));
        infoTotalRamSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalMemory));
        infoRamPercentage_tv.setText(ramUsagePercentage);

        infoRam_cpb.setProgressMax(totalMemory);
        ValueAnimator animator1 = ValueAnimator.ofFloat(0, usedMemory);
        animator1.setDuration(1500);
        animator1.addUpdateListener(animation -> infoRam_cpb.setProgress(Float.parseFloat(animation.getAnimatedValue().toString())));
        animator1.start();
        infoRam_cpb.setProgress(usedMemory);

        //for Storage
        float totalBytes, availableBytes, usedBytes;

        totalBytes = storageUtility.getTotalStorage();
        availableBytes = storageUtility.getAvailableStorage();

        usedBytes = totalBytes - availableBytes;

        String storageTotalPrefix = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalBytes);
        String storageAvailablePrefix = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(availableBytes);
        String storageUsedPrefix = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(usedBytes);

        @SuppressLint("DefaultLocale") String storageUsagePercentage = String.format("%.1f", mathCalculationsUtil.getPercentageFloat(totalBytes, usedBytes)) + "%";

        TextView infoStoragePercentage_tv = findViewById(R.id.infoStoragePercentage_tv);
        TextView infoStorageTotalSize_tv = findViewById(R.id.infoStorageTotalSize_tv);
        TextView infoStorageFreeSize_tv = findViewById(R.id.infoStorageFreeSize_tv);
        TextView infoStorageUsedSize_tv = findViewById(R.id.infoStorageUsedSize_tv);
        CircularProgressBar infoStorage_cpb = findViewById(R.id.infoStorage_cpb);

        infoStoragePercentage_tv.setText(storageUsagePercentage);
        infoStorageTotalSize_tv.setText(storageTotalPrefix);
        infoStorageFreeSize_tv.setText(storageAvailablePrefix);
        infoStorageUsedSize_tv.setText(storageUsedPrefix);


        infoStorage_cpb.setProgressMax(totalBytes);
        ValueAnimator animator = ValueAnimator.ofFloat(0, usedBytes);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> infoStorage_cpb.setProgress(Float.parseFloat(animation.getAnimatedValue().toString())));
        animator.start();
        infoStorage_cpb.setProgress(usedBytes);


    }
}