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
        int[] tabIcons = new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
        ViewPager main_viewPager = findViewById(R.id.main_viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
        main_viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(main_viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);


//        BottomNavigationView main_bottomNavigationView = findViewById(R.id.main_bottomNavigationView);
//        HomeFragment homeFragment = new HomeFragment();
//        loadFragment(homeFragment);
//        main_bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                        HomeFragment homeFragment = new HomeFragment();
//                        loadFragment(homeFragment);
//                        break;
//                    case R.id.tools:
//                        Toast.makeText(MainActivity.this, "Tools", Toast.LENGTH_SHORT).show();
//                        ToolsFragment toolsFragment = new ToolsFragment();
//                        loadFragment(toolsFragment);
//                        break;
//                    case R.id.info:
//                        Toast.makeText(MainActivity.this, "infomation", Toast.LENGTH_SHORT).show();
//                        InfoFragment infoFragment = new InfoFragment();
//                        loadFragment(infoFragment);
//                        break;
////                    case R.id.more:
////                        Toast.makeText(MainActivity.this, "More", Toast.LENGTH_SHORT).show();
////                        MoreFragment moreFragment = new MoreFragment();
////                        loadFragment(moreFragment);
////                        break;
//                }
//                return false;
//            }
//        });
    }

//    public void loadFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment);
//        fragmentTransaction.commit();
//    }

}