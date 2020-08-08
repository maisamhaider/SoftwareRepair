package com.example.softwarerepair.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.softwarerepair.R;
import com.example.softwarerepair.fragments.InstalledFragment;
import com.example.softwarerepair.fragments.SystemAppFragment;

public class ManageAppsActivity extends AppCompatActivity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_apps);

        TextView installedApps_tv,systemApp_tv;
        View view4,view5;

        installedApps_tv = findViewById(R.id.installedApps_tv);
        systemApp_tv = findViewById(R.id.systemApp_tv);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);
         loadFragment(new InstalledFragment());
         installedApps_tv.setTextColor(Color.parseColor("#474747"));
         systemApp_tv.setTextColor(Color.parseColor("#979797"));
         view4.setVisibility(View.VISIBLE);
         view5.setVisibility(View.INVISIBLE);
        installedApps_tv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                loadFragment(new InstalledFragment());
                installedApps_tv.setTextColor(Color.parseColor("#474747"));
                systemApp_tv.setTextColor(Color.parseColor("#979797"));
                view4.setVisibility(View.VISIBLE);
                view5.setVisibility(View.INVISIBLE);
            }
        });
        systemApp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SystemAppFragment());
                installedApps_tv.setTextColor(Color.parseColor("#979797"));
                systemApp_tv.setTextColor(Color.parseColor("#474747"));
                view4.setVisibility(View.INVISIBLE);
                view5.setVisibility(View.VISIBLE);
            }
        });


    }

    public void loadFragment(Fragment fragment)
    {
//        Fragment fragment1 = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.manageAppFragmentContainer,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}