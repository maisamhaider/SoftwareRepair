package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.adapters.BackUpAppsAdapter;
import com.phone.repair.phone.cleaner.app_2020.asynctasks.BackUpAppsTask;

public class BackupAppsActivity extends AppCompatActivity {

    private BackUpAppsAdapter backUpAppsAdapter;
    private RecyclerView backupApp_RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_apps);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        backupApp_RV = findViewById(R.id.backupApp_RV);

        backUpAppsAdapter = new BackUpAppsAdapter(this);
        loadApplications();
    }
    public void loadApplications() {
        BackUpAppsTask backUpAppsTask = new BackUpAppsTask(this, backUpAppsAdapter, backupApp_RV);
        backUpAppsTask.execute();
    }
}