package com.example.softwarerepair.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.softwarerepair.R;
import com.example.softwarerepair.activities.BatteryInfoActivity;
import com.example.softwarerepair.activities.BluetoothInfoActivity;
import com.example.softwarerepair.activities.DisplayInfoActivity;
import com.example.softwarerepair.activities.FeaturesActivity;
import com.example.softwarerepair.activities.HardwareInfoActivity;
import com.example.softwarerepair.activities.InfoStorageAndRamActivity;
import com.example.softwarerepair.activities.ProcessorInfoActivity;
import com.example.softwarerepair.activities.SensorInfoActivity;

public class InfoFragment extends Fragment {

    public InfoFragment() {
        // Required empty public constructor
    }


    public static InfoFragment newInstance(  ) {
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_info, container, false);
       view.findViewById(R.id.infoBluetooth_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), BluetoothInfoActivity.class) ;
           getActivity().startActivity(intent);
       });
       view.findViewById(R.id.infoProcessor_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), ProcessorInfoActivity.class) ;
           getActivity().startActivity(intent);
       });
       view.findViewById(R.id.infoSensor_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), SensorInfoActivity.class) ;
           getActivity().startActivity(intent);
       });
       view.findViewById(R.id.infoFeatures_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), FeaturesActivity.class) ;
           getActivity().startActivity(intent);
       });
       view.findViewById(R.id.infoMobile_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), HardwareInfoActivity.class) ;
           getActivity().startActivity(intent);
       });
       view.findViewById(R.id.infoDisplay_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), DisplayInfoActivity.class) ;
           getActivity().startActivity(intent);
       });

       view.findViewById(R.id.infoBattery_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), BatteryInfoActivity.class) ;
           getActivity().startActivity(intent);
       });
       view.findViewById(R.id.infoStorageAndRam_cl).setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), InfoStorageAndRamActivity.class) ;
           getActivity().startActivity(intent);
       });
       view.findViewById(R.id.infoCamera_cl).setOnClickListener(v -> {
//           Intent intent = new Intent(getContext(), DisplayInfoActivity.class) ;
//           getActivity().startActivity(intent);
       });
        return view;
    }
}