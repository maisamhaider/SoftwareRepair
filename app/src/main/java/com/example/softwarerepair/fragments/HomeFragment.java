package com.example.softwarerepair.fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.softwarerepair.R;
import com.example.softwarerepair.activities.BatteryInfoActivity;
import com.example.softwarerepair.activities.BoosterRamActivity;
import com.example.softwarerepair.activities.CacheCleanActivity;
import com.example.softwarerepair.activities.EmptyFoldersActivity;
import com.example.softwarerepair.activities.ManageAppsActivity;
import com.example.softwarerepair.utils.MathCalculationsUtil;
import com.example.softwarerepair.utils.StorageUtility;

public class HomeFragment extends Fragment {
    private View view;
     StorageUtility storageUtility;
    MathCalculationsUtil mathCalculationsUtil;
    RoundCornerProgressBar batteryRoundCorner_pb, storageRoundCorner_pb;
    TextView batteryProgressBarPercent_tv;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        storageUtility = new StorageUtility();
        mathCalculationsUtil = new MathCalculationsUtil();
        batteryRoundCorner_pb = view.findViewById(R.id.batteryRoundCorner_pb);
        storageRoundCorner_pb = view.findViewById(R.id.storageRoundCorner_pb);
        batteryProgressBarPercent_tv = view.findViewById(R.id.batteryProgressBarPercent_tv);

        batteryRoundCorner_pb.setMax((float) 100.0);

        view.findViewById(R.id.homeBatteryIntent_view).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BatteryInfoActivity.class);
                 startActivity(intent);
        });
        view.findViewById(R.id.homeStorageIntent_view).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.manageApps_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ManageAppsActivity.class);
            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.cleanCache_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CacheCleanActivity.class);
            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.boosterRam_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BoosterRamActivity.class);
            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.repairSystem_cl).setOnClickListener(v -> {
//              Intent intent = new Intent(getContext(), Repa.class);
//            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.emptyFolder_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EmptyFoldersActivity.class);
            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.batterySaver_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null) {
                startActivity(intent);
            }
        });

        ramAndStorageFun();

        return view;
    }

    private void ramAndStorageFun() {


//for Ram
        ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        float totalMemory = mi.totalMem;
        float freeMemory = mi.availMem;
        float usedMemory = totalMemory - freeMemory;
        TextView systemAndAppsSize_tv = view.findViewById(R.id.systemAndAppsSize_tv);
        TextView availableRamSize_tv = view.findViewById(R.id.availableRamSize_tv);
        TextView totalRamSize_tv = view.findViewById(R.id.totalRamSize_tv);

        systemAndAppsSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(usedMemory));
        availableRamSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(freeMemory));
        totalRamSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalMemory));

        //for Storage
        float totalBytes, availableBytes, usedBytes;

        totalBytes = storageUtility.getTotalStorage();
        availableBytes = storageUtility.getAvailableStorage();

        usedBytes = totalBytes - availableBytes;

        String storageTotalPrefix = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalBytes);
        String storageAvailablePrefix = mathCalculationsUtil.getCalculatedDataSizeWithPrefix(availableBytes);
        @SuppressLint("DefaultLocale") String storageUsagePercentage = String.format("%.1f", mathCalculationsUtil.getPercentageFloat(totalBytes, usedBytes)) + "%";

        TextView storageProgressBarPercent_tv = view.findViewById(R.id.storageProgressBarPercent_tv);
        TextView homeTotalStorageSize_tv = view.findViewById(R.id.homeTotalStorageSize_tv);
        TextView homeStorageFreeSize_tv = view.findViewById(R.id.homeStorageFreeSize_tv);

        storageProgressBarPercent_tv.setText(storageUsagePercentage);
        homeTotalStorageSize_tv.setText(storageTotalPrefix);
        homeStorageFreeSize_tv.setText(storageAvailablePrefix);


        storageRoundCorner_pb.setMax(totalBytes);
        ValueAnimator animator = ValueAnimator.ofFloat(0, usedBytes);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> storageRoundCorner_pb.setProgress(Float.parseFloat(animation.getAnimatedValue().toString())));
        animator.start();
        storageRoundCorner_pb.setProgress(usedBytes);
        getActivity().registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int bLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryProgressBarPercent_tv.setText( mathCalculationsUtil.getPercentageFloat((float) 100,(float)bLevel)+"%");
            batteryRoundCorner_pb.setProgress((float) bLevel);
        }
    };
}