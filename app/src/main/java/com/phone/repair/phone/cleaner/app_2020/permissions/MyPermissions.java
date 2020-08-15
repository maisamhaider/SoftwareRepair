package com.phone.repair.phone.cleaner.app_2020.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyPermissions {
    private static final int REQ = 1111;
    private Context context;

    public MyPermissions(Context context) {
        this.context = context;
    }

    public boolean permission() {

        int readStoragePer = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStoragePer = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (readStoragePer == PackageManager.PERMISSION_GRANTED
                && writeStoragePer == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ);

        return false;
    }


}
