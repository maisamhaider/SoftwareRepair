package com.example.softwarerepair.activities;

import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.utils.StorageUtility;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class EmptyFoldersActivity extends AppCompatActivity {
    int result = 0;
    TextView emptyFolders_tv, cleanSuccessfully_tv;
    StorageUtility storageUtility;
    CircularProgressIndicator deleteEmptyFolders_cpb;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_folders);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );


        storageUtility = new StorageUtility();
        emptyFolders_tv = findViewById(R.id.emptyFolders_tv);
        cleanSuccessfully_tv = findViewById(R.id.cleanSuccessfully_tv);
        deleteEmptyFolders_cpb = findViewById(R.id.deleteEmptyFolders_cpb);

        path = Environment.getExternalStorageDirectory().getAbsolutePath();


        deleteEmptyFolders_cpb.setMaxProgress( 100);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setDuration(6000);
        animator.addUpdateListener(animation -> {
            float value = Float.parseFloat(animation.getAnimatedValue().toString());
            deleteEmptyFolders_cpb.setProgress(value, 100);
            if (value == 30)
            {
                storageUtility.deleteAllEmptyFolder(path);
            }else if (value == 40)
            {
                storageUtility.deleteAllEmptyFolder(path);

            }else if (value == 60)
            {
                storageUtility.deleteAllEmptyFolder(path);

            }
            if (value==100)
            {
                cleanSuccessfully_tv.setVisibility(View.VISIBLE);
                deleteEmptyFolders_cpb.setVisibility(View.GONE);
                emptyFolders_tv.setVisibility(View.GONE);
            }

        });
        animator.start();
        deleteEmptyFolders_cpb.setProgress(100,100);


    }

}