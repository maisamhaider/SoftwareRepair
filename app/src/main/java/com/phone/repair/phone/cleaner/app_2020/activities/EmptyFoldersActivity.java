package com.phone.repair.phone.cleaner.app_2020.activities;

import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.utils.IosDialog;
import com.phone.repair.phone.cleaner.app_2020.utils.StorageUtility;

import java.io.File;

public class EmptyFoldersActivity extends AppCompatActivity {
    int result = 0;
    TextView emptyFolders_tv, cleanSuccessfully_tv,counting_tv;
    StorageUtility storageUtility;
    CircularProgressBar deleteEmptyFolders_cpb;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_folders);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );


        storageUtility = new StorageUtility();
        emptyFolders_tv = findViewById(R.id.emptyFolders_tv);
        counting_tv = findViewById(R.id.counting_tv);
        cleanSuccessfully_tv = findViewById(R.id.cleanSuccessfully_tv);
        deleteEmptyFolders_cpb = findViewById(R.id.deleteEmptyFolders_cpb);

        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        new SearchEmptyFolders().execute();
    }

    class SearchEmptyFolders extends AsyncTask<Void, Integer, String> {
        IosDialog iosDialog = new IosDialog(EmptyFoldersActivity.this, "Searching");

        boolean isEmpty = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            emptyFolders_tv.setVisibility(View.GONE);
            iosDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            isEmpty = storageUtility.isEmptyFolder(new File(path));
             return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            iosDialog.dismiss();
            if (isEmpty)
            {
                emptyFolders_tv.setVisibility(View.VISIBLE);
                storageUtility.deleteAllEmptyFolder(path);
                deleteEmptyFolders_cpb.setProgressMax( 100);
                ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
                animator.setDuration(6000);
                animator.addUpdateListener(animation -> {
                    float value = Float.parseFloat(animation.getAnimatedValue().toString());
                    counting_tv.setText("");
                    counting_tv.setText(String.valueOf((int) value));
                    deleteEmptyFolders_cpb.setProgress(value);
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
                        counting_tv.setVisibility(View.GONE);
                        emptyFolders_tv.setVisibility(View.GONE);
                    }

                });
                animator.start();
                deleteEmptyFolders_cpb.setProgress(100);
            }
            else {
                counting_tv.setText("No Empty folder found");
                emptyFolders_tv.setVisibility(View.GONE);
            }

        }
    }

}