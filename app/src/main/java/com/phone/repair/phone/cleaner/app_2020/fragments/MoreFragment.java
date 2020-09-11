package com.phone.repair.phone.cleaner.app_2020.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phone.repair.phone.cleaner.app_2020.R;

public class MoreFragment extends BaseFrag {

    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance() {
        return new MoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        adView(view.findViewById(R.id.adView1));
        String manufacture = Build.MANUFACTURER;
        view.findViewById(R.id.moreBatteryUsage_cl).setOnClickListener(v -> {

            if (manufacture.toLowerCase().matches("huawei"))
            {Intent intent = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
                if (resolveInfo != null) {
                    startActivity(intent);
                }
            }
            else
            { Intent intent = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
                if (resolveInfo != null) {
                    startActivity(intent);
                }
            }

        });
        view.findViewById(R.id.moreDisplaySettings_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
             if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreSaveBattery_cl).setOnClickListener(v -> {
            Intent intent = null;


            if (Build.MANUFACTURER.toLowerCase().matches("samsung")) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    intent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
                    ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
                    if (resolveInfo != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "your device doesn't support this power saver", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "your device doesn't support this power saver", Toast.LENGTH_SHORT).show();
                }
            } else {

                if (Build.MANUFACTURER.toLowerCase().matches("huawei"))
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        Toast.makeText(mContext, "Device does not support this feature", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        startActivity(new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS));
                    }
                }


            }



    });

        view.findViewById(R.id.moreStorageSettings_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
             if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreAboutPhone_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
             if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreBluetoothSettings_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
             if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreManageNetworkUsage).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_MANAGE_NETWORK_USAGE);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
             if (resolveInfo != null) {
                startActivity(intent);
            }
        });view.findViewById(R.id.moreNetworkOperators_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
             if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreWirelessSettings_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreAllWifiNetworks_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreWifiAdvanceSettings_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_WIFI_IP_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null) {
                startActivity(intent);
            }
        });
        view.findViewById(R.id.moreDataRoamingSettings_cl).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null) {
                startActivity(intent);
            }
        });

        view.findViewById(R.id.moreNfsAndPayment_cl).setOnClickListener(v -> {
            if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC))
            {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(intent, 0);
                if (resolveInfo != null) {
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(mContext, "Device doesn't support nfc feature", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }
}