package com.example.softwarerepair.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarerepair.R;
import com.example.softwarerepair.adapters.BackUpAppsAdapter;
import com.example.softwarerepair.asynctasks.BackUpAppsTask;

public class BackupAppsActivity extends AppCompatActivity {

    private BackUpAppsAdapter backUpAppsAdapter;
    private RecyclerView backupApp_RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_apps);

        backupApp_RV = findViewById(R.id.backupApp_RV);

        backUpAppsAdapter = new BackUpAppsAdapter(this);
        loadApplications();
    }
    public void loadApplications() {
        BackUpAppsTask backUpAppsTask = new BackUpAppsTask(this, backUpAppsAdapter, backupApp_RV);
        backUpAppsTask.execute();
    }
}