package com.example.softwarerepair.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarerepair.R;
import com.example.softwarerepair.adapters.ApplicationsAdapter;
import com.example.softwarerepair.asynctasks.ApplicationsTask;

public class ManageAppsActivity extends AppCompatActivity {

    private ApplicationsAdapter applicationsAdapter;
    private RecyclerView application_RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_apps);


        application_RV = findViewById(R.id.application_RV);

        applicationsAdapter = new ApplicationsAdapter(this);
        loadApplications();
    }
    public void loadApplications() {
        ApplicationsTask allAppsTsk = new ApplicationsTask(this, applicationsAdapter, application_RV);
        allAppsTsk.execute();
      }
}