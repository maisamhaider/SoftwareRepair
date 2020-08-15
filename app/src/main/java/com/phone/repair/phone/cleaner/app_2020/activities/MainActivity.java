package com.phone.repair.phone.cleaner.app_2020.activities;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.adapters.ViewPagerAdapter;
import com.phone.repair.phone.cleaner.app_2020.permissions.MyPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        MyPermissions permissions = new MyPermissions(this);
        permissions.permission();
         int[] tabIconsSelect = new int[]{R.drawable.ic_selctor_home, R.drawable.ic_selctor_info, R.drawable.ic_selctor_more};
         ViewPager main_viewPager = findViewById(R.id.main_viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        main_viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(main_viewPager);
        tabLayout.getTabAt(0).setIcon(tabIconsSelect[0]);
        tabLayout.getTabAt(1).setIcon(tabIconsSelect[1]);
        tabLayout.getTabAt(2).setIcon(tabIconsSelect[2]);
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            exitFun();
        }

    }

    public void exitFun() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = getLayoutInflater();
            @SuppressLint("InflateParams") final View dialogView = layoutInflater.inflate(R.layout.dialog_exit_xml, null);
            Button yesBtn = dialogView.findViewById(R.id.yes);
            Button noBtn = dialogView.findViewById(R.id.no);
             builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
            yesBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {
                    moveTaskToBack(true);
                    alertDialog.cancel();
                    finishAffinity();
                }
            });
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

        } catch (Exception a) {
            a.printStackTrace();
        }
    }


}