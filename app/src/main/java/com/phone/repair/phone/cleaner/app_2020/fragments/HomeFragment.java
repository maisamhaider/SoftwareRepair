package com.phone.repair.phone.cleaner.app_2020.fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.activities.BackupAppsActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.BatteryInfoActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.BoosterRamActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.CacheCleanActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.EmptyFoldersActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.HardwareTestingActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.ManageAppsActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.PhoneOptimizerActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.RepairSystemActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.RootCheckerActivity;
import com.phone.repair.phone.cleaner.app_2020.utils.MathCalculationsUtil;
import com.phone.repair.phone.cleaner.app_2020.utils.StorageUtility;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class HomeFragment extends BaseFrag {
    private View view;
    StorageUtility storageUtility;
    MathCalculationsUtil mathCalculationsUtil;
    CircularProgressIndicator batteryRoundCorner_pb, storageRoundCorner_pb, homeRam_cpb;
    TextView batteryProgressBarPercent_tv;
    SharedPreferences preferences;

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
        setmContext(getContext());
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        storageUtility = new StorageUtility();
        mathCalculationsUtil = new MathCalculationsUtil();
        batteryRoundCorner_pb = view.findViewById(R.id.batteryRoundCorner_pb);
        storageRoundCorner_pb = view.findViewById(R.id.storageRoundCorner_pb);
        batteryProgressBarPercent_tv = view.findViewById(R.id.batteryProgressBarPercent_tv);
        preferences = getContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);


        view.findViewById(R.id.homeBatteryIntent_view).setOnClickListener(v -> {
            newActivityAds(new BatteryInfoActivity());
        });
        view.findViewById(R.id.homeStorageIntent_view).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.manageApps_cl).setOnClickListener(v -> {

            newActivityAds(new ManageAppsActivity());
        });
        view.findViewById(R.id.cleanCache_cl).setOnClickListener(v -> {
            newActivityAds(new CacheCleanActivity());
        });
        view.findViewById(R.id.boosterRam_cl).setOnClickListener(v -> {
            newActivityAds(new BoosterRamActivity());

        });
        view.findViewById(R.id.repairSystem_cl).setOnClickListener(v -> {
            newActivityAds(new RepairSystemActivity());

        });
        view.findViewById(R.id.emptyFolder_cl).setOnClickListener(v -> {
            newActivityAds(new EmptyFoldersActivity());
        });
        view.findViewById(R.id.batterySaver_cl).setOnClickListener(v -> {

//            Intent intent = new  Intent();
            if (Build.MANUFACTURER.toLowerCase().matches("samsung")) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    Intent intent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
                    ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
                    if (resolveInfo != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "your device doesn't support this power saver", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "your device doesn't support this power saver", Toast.LENGTH_SHORT).show();
                }
            } else {

                if (Build.MANUFACTURER.toLowerCase().matches("huawei")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Toast.makeText(mContext, "Device does not support this feature", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
                    }
                }


            }


//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                Intent intent = null;
//                intent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);   //   Settings.ACTION_BATTERY_SAVER_SETTINGS
//                ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent,0 );
//                 if (resolveInfo != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), "your device doesn't support this power saver", Toast.LENGTH_SHORT).show();
//                }
//            }else {
//                Toast.makeText(getActivity(), "your device doesn't support this power saver", Toast.LENGTH_SHORT).show();
//            }
        });

        view.findViewById(R.id.backupApp_cl).setOnClickListener(v -> {
            newActivityAds(new BackupAppsActivity());

        });
        view.findViewById(R.id.phoneCooler_cl).setOnClickListener(v -> {
            newActivityAds(new PhoneOptimizerActivity());

        });
        view.findViewById(R.id.hardwareTesting_cl).setOnClickListener(v -> {
            newActivityAds(new HardwareTestingActivity());

        });
        view.findViewById(R.id.rootChecker_cl).setOnClickListener(v -> {
            newActivityAds(new RootCheckerActivity());

        });


        ramAndStorageFun();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int rq = requestCode;
        int rc = resultCode;
    }

    private void ramAndStorageFun() {
//for Ram
        ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        float totalMemory = mi.totalMem;
        float freeMemory = mi.availMem;
        float usedMemory = totalMemory - freeMemory;
        @SuppressLint("DefaultLocale") String ramUsagePercentage = String.format("%.1f", mathCalculationsUtil.getPercentageFloat(totalMemory, usedMemory)) + "%";

        TextView systemAndAppsSize_tv = view.findViewById(R.id.systemAndAppsSize_tv);
        TextView availableRamSize_tv = view.findViewById(R.id.availableRamSize_tv);
        TextView totalRamSize_tv = view.findViewById(R.id.totalRamSize_tv);
        TextView homeRamPercentage_tv = view.findViewById(R.id.homeRamPercentage_tv);
        homeRam_cpb = view.findViewById(R.id.homeRam_cpb);

        systemAndAppsSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(usedMemory));
        availableRamSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(freeMemory));
        totalRamSize_tv.setText(mathCalculationsUtil.getCalculatedDataSizeWithPrefix(totalMemory));
        homeRamPercentage_tv.setText(ramUsagePercentage);

        homeRam_cpb.setMaxProgress(totalMemory);
        ValueAnimator animator1 = ValueAnimator.ofFloat(0, usedMemory);
        animator1.setDuration(1500);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                homeRam_cpb.setProgress(Float.parseFloat(animation.getAnimatedValue().toString()), totalMemory);
            }
        });
        animator1.start();
        homeRam_cpb.setProgress(usedMemory, totalMemory);

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


        ValueAnimator animator = ValueAnimator.ofFloat(0, usedBytes);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> storageRoundCorner_pb.setProgress(Float.parseFloat(animation.getAnimatedValue().toString()), totalBytes));
        animator.start();
        storageRoundCorner_pb.setProgress(usedBytes, totalBytes);
        getActivity().registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int bLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryProgressBarPercent_tv.setText(mathCalculationsUtil.getPercentageFloat((float) 100, (float) bLevel) + "%");
            batteryRoundCorner_pb.setProgress(bLevel, 100);
        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }
}