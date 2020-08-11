package com.example.softwarerepair.models;

import android.graphics.drawable.Drawable;

public class AppsModel {
       private String appName;
      private String installTime;
      private Drawable Image;


    public AppsModel(String appName, String installTime, Drawable image) {
        this.appName = appName;
        this.installTime = installTime;
        Image = image;
    }


    public String getAppName() {
        return appName;
    }

    public String getInstallTime() {
        return installTime;
    }

    public Drawable getImage() {
        return Image;
    }
}
