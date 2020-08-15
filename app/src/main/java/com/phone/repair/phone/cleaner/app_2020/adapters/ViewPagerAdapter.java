package com.phone.repair.phone.cleaner.app_2020.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.phone.repair.phone.cleaner.app_2020.annotations.StringsAnnotations;
import com.phone.repair.phone.cleaner.app_2020.fragments.HomeFragment;
import com.phone.repair.phone.cleaner.app_2020.fragments.InfoFragment;
import com.phone.repair.phone.cleaner.app_2020.fragments.MoreFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[] { StringsAnnotations.HOME,StringsAnnotations.INFO,StringsAnnotations.MORE };

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
                return InfoFragment.newInstance( );
            case 2:
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
        return 3;
    }
}
