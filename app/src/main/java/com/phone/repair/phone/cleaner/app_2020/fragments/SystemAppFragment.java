package com.phone.repair.phone.cleaner.app_2020.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.adapters.ApplicationsAdapter;
import com.phone.repair.phone.cleaner.app_2020.asynctasks.ApplicationsTask;
import com.phone.repair.phone.cleaner.app_2020.interfaces.IsTrueOrFalse;

public class SystemAppFragment extends BaseFrag implements IsTrueOrFalse {

    private ApplicationsAdapter applicationsAdapter;
    private RecyclerView systemApps_rv;
    private EditText searchSystemApp_ET;
    private InputMethodManager inputMethodManager;
    private TextView noAppFound_tv;
    public SystemAppFragment() {
        // Required empty public constructor
    }

    public static SystemAppFragment newInstance() {
        SystemAppFragment fragment = new SystemAppFragment();
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_system_app, container, false);


        systemApps_rv = view.findViewById(R.id.systemApps_rv);
        searchSystemApp_ET = view.findViewById(R.id.searchSystemApp_ET);
        noAppFound_tv = view.findViewById(R.id.noAppFound_tv);

        applicationsAdapter = new ApplicationsAdapter(getContext() );
        applicationsAdapter.initIsTrueInterface(this);


        inputMethodManager = (InputMethodManager) getContext().getSystemService( Context.INPUT_METHOD_SERVICE );
        searchSystemApp_ET.setImeOptions( EditorInfo.IME_ACTION_SEARCH );

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchSystemApp_ET.setOnClickListener(v -> {
            inputMethodManager.showSoftInput( searchSystemApp_ET, InputMethodManager.SHOW_FORCED );
            searchSystemApp_ET.setImeOptions( EditorInfo.IME_ACTION_SEARCH );
            searchSystemApp_ET.setSingleLine();
        });

        searchSystemApp_ET.setOnEditorActionListener((v, actionId, event) -> {

            if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENDCALL) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                inputMethodManager.hideSoftInputFromWindow(searchSystemApp_ET.getWindowToken(), 0 );
            }
            return false;
        });

        searchSystemApp_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                applicationsAdapter.getFilter().filter(s);
            }
        });
        loadApplications();

        return view;
    }
    public void loadApplications() {
        ApplicationsTask allAppsTsk = new ApplicationsTask(getContext(), applicationsAdapter, systemApps_rv,true);
        allAppsTsk.execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        inputMethodManager.hideSoftInputFromWindow( searchSystemApp_ET.getWindowToken(), 0 );
    }

    @Override
    public void isTrue(boolean isT) {
        if (isT)
        {
            noAppFound_tv.setVisibility(View.VISIBLE);
        }
        else
        {
            noAppFound_tv.setVisibility(View.GONE);
        }
    }
}