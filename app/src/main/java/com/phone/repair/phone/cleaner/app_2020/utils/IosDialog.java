package com.phone.repair.phone.cleaner.app_2020.utils;

import android.content.Context;
import android.view.Gravity;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.gmail.samehadar.iosdialog.IOSDialog;

public class IosDialog {

    private IOSDialog iosDialog;

    public boolean show() {
        iosDialog.show();
        return true;
    }

    public boolean dismiss() {
        iosDialog.dismiss();
        return false;
    }

    public IosDialog(Context context) {
        iosDialog = new IOSDialog.Builder(context)
                .setCancelable(false)
                .setSpinnerClockwise(false)
                .setMessageContentGravity(Gravity.END)
                .setBackgroundColor(context.getResources().getColor(R.color.colorPrimary))
                .setMessageColor(context.getResources().getColor(R.color.standard_white))
                .setMessageContent("Loading Data")
                .build();
    }
    public IosDialog(Context context, String msg) {
        iosDialog = new IOSDialog.Builder(context)
                .setCancelable(false)
                .setSpinnerClockwise(false)
                .setMessageContentGravity(Gravity.END)
                .setMessageContent(msg)
                .build();
    }


}