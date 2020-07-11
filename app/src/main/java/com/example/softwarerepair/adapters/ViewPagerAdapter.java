package com.example.softwarerepair.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.softwarerepair.annotations.StringsAnnotations;
import com.example.softwarerepair.fragments.HomeFragment;
import com.example.softwarerepair.fragments.InfoFragment;
import com.example.softwarerepair.fragments.MoreFragment;
import com.example.softwarerepair.fragments.ToolsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[] { StringsAnnotations.HOME,StringsAnnotations.TOOLS,StringsAnnotations.INFO,StringsAnnotations.MORE };

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return ToolsFragment.newInstance();
            case 2:
                return InfoFragment.newInstance( );
            case 3:
                return MoreFragment.newInstance();

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }



    @Override
    public int getCount() {
        return 4;
    }
}
