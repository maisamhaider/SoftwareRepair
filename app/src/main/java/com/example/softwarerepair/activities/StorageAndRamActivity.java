package com.example.softwarerepair.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.utils.MathCalculationsUtil;
import com.example.softwarerepair.utils.StorageUtility;

public class StorageAndRamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_and_ram);


    }

    private void ramAndStorageFun() {

        StorageUtility storageUtility = new StorageUtility();
        MathCalculationsUtil mathCalculationsUtil = new MathCalculationsUtil();


//for Ram
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        float totalMemory = mi.totalMem;
        float freeMemory = mi.availMem;
        float usedMemory = totalMemory - freeMemory;

        //for Storage
        float totalBytes, availableBytes, usedBytes;

        totalBytes = storageUtility.getTotalStorage();
        availableBytes = storageUtility.getAvailableStorage();

        usedBytes = totalBytes - availableBytes;

        String storageTotalPrefix  = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalBytes);
        String storageAvailablePrefix  = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(availableBytes);
        String storageUsedPrefix  = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(usedBytes);
        String storageUsagePercentage = mathCalculationsUtil.getPercentageFloat(totalBytes,usedBytes)+"%";


    }
}