package com.phone.repair.phone.cleaner.app_2020.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.activities.BatteryInfoActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.BluetoothInfoActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.DisplayInfoActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.FeaturesActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.HardwareInfoActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.InfoStorageAndRamActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.ProcessorInfoActivity;
import com.phone.repair.phone.cleaner.app_2020.activities.SensorInfoActivity;

public class InfoFragment extends BaseFrag {

    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance() {
        return new InfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        adView(view.findViewById(R.id.adView));
        view.findViewById(R.id.infoBluetooth_cl).setOnClickListener(v -> {
            newActivityAds(new BluetoothInfoActivity());
        });
        view.findViewById(R.id.infoProcessor_cl).setOnClickListener(v -> {
            newActivityAds(new ProcessorInfoActivity());

        });
        view.findViewById(R.id.infoSensor_cl).setOnClickListener(v -> {

            newActivityAds(new SensorInfoActivity());

        });
        view.findViewById(R.id.infoFeatures_cl).setOnClickListener(v -> {
            newActivityAds(new FeaturesActivity());

        });
        view.findViewById(R.id.infoMobile_cl).setOnClickListener(v -> {
            newActivityAds(new HardwareInfoActivity());

        });
        view.findViewById(R.id.infoDisplay_cl).setOnClickListener(v -> {
            newActivityAds(new DisplayInfoActivity());

        });

        view.findViewById(R.id.infoBattery_cl).setOnClickListener(v -> {
            newActivityAds(new BatteryInfoActivity());

        });
        view.findViewById(R.id.infoStorageAndRam_cl).setOnClickListener(v -> {

            newActivityAds(new InfoStorageAndRamActivity());

        });

        return view;
    }
}