package com.phone.repair.phone.cleaner.app_2020.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.phone.repair.phone.cleaner.app_2020.BuildConfig;
import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.adapters.ViewPagerAdapter;
import com.phone.repair.phone.cleaner.app_2020.permissions.MyPermissions;

public class MainActivity extends AppCompatActivity {
    ImageView homeMenu_iv;

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
        homeMenu_iv = findViewById(R.id.homeMenu_iv);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        main_viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(main_viewPager);
        tabLayout.getTabAt(0).setIcon(tabIconsSelect[0]);
        tabLayout.getTabAt(1).setIcon(tabIconsSelect[1]);
        tabLayout.getTabAt(2).setIcon(tabIconsSelect[2]);
        homeMenu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindowObj = popupWindowDisplay();
                popupWindowObj.showAsDropDown(homeMenu_iv);
            }
        });
    }
    private PopupWindow popupWindowDisplay() {
        final PopupWindow popupWindow = new PopupWindow(MainActivity.this);
        // inflate your layout or dynamically add view
        final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.home_menu_xml, null);

        LinearLayout share_ll = view.findViewById(R.id.share_ll);
        LinearLayout rateUs_ll = view.findViewById(R.id.rateUs_ll);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        share_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareUs();
            }
        });
        rateUs_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +getPackageName())));
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

        return popupWindow;
    }
    public void shareUs() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
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
            Button rateUs_btn = dialogView.findViewById(R.id.rateUs_btn);
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
            rateUs_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            });

        } catch (Exception a) {
            a.printStackTrace();
        }
    }


}