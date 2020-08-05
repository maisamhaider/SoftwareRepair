package com.example.softwarerepair.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.softwarerepair.R;
import com.example.softwarerepair.activities.BackupAppsActivity;
import com.example.softwarerepair.activities.HardwareTestingActivity;
import com.example.softwarerepair.activities.PhoneCoolerActivity;
import com.example.softwarerepair.activities.RootCheckerActivity;

public class ToolsFragment extends Fragment {


    public ToolsFragment() {
        // Required empty public constructor
    }

    public static ToolsFragment newInstance() {
        return new ToolsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        view.findViewById(R.id.backupApp_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), BackupAppsActivity.class);
            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.phoneCooler_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PhoneCoolerActivity.class);
            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.hardwareTesting_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HardwareTestingActivity.class);
            getActivity().startActivity(intent);
        });
        view.findViewById(R.id.rootChecker_cl).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RootCheckerActivity.class);
            getActivity().startActivity(intent);
        });


        return view;
    }
}