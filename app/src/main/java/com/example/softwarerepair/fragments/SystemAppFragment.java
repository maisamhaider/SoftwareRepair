package com.example.softwarerepair.fragments;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarerepair.R;
import com.example.softwarerepair.adapters.ApplicationsAdapter;
import com.example.softwarerepair.asynctasks.ApplicationsTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SystemAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SystemAppFragment extends Fragment {

    private ApplicationsAdapter applicationsAdapter;
    private RecyclerView systemApps_rv;
    private EditText searchSystemApp_ET;
    private InputMethodManager inputMethodManager;

    public SystemAppFragment() {
        // Required empty public constructor
    }

    public static SystemAppFragment newInstance(String param1, String param2) {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_system_app, container, false);


        systemApps_rv = view.findViewById(R.id.systemApps_rv);
        searchSystemApp_ET = view.findViewById(R.id.searchSystemApp_ET);

        applicationsAdapter = new ApplicationsAdapter(getContext() );


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
}