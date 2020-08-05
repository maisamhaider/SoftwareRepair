package com.example.softwarerepair.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.softwarerepair.R;
import com.example.softwarerepair.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] tabIconsSelect = new int[]{R.drawable.ic_selctor_home,
                R.drawable.ic_selctor_tools, R.drawable.ic_selctor_info,
                R.drawable.ic_selctor_more};
         ViewPager main_viewPager = findViewById(R.id.main_viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        main_viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(main_viewPager);
        tabLayout.getTabAt(0).setIcon(tabIconsSelect[0]);
        tabLayout.getTabAt(1).setIcon(tabIconsSelect[1]);
        tabLayout.getTabAt(2).setIcon(tabIconsSelect[2]);
        tabLayout.getTabAt(3).setIcon(tabIconsSelect[3]);
    }

}