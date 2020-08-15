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

import androidx.recyclerview.widget.RecyclerView;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.adapters.ApplicationsAdapter;
import com.phone.repair.phone.cleaner.app_2020.asynctasks.ApplicationsTask;


public class InstalledFragment extends BaseFrag {
    private ApplicationsAdapter applicationsAdapter;
    private RecyclerView installedApps_rv;
    private EditText searchInstalled_ET;
    private InputMethodManager inputMethodManager;

    public InstalledFragment() {
        // Required empty public constructor
    }

    public static InstalledFragment newInstance(String param1, String param2) {
        InstalledFragment fragment = new InstalledFragment();
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
        View view = inflater.inflate(R.layout.fragment_installed, container, false);

        installedApps_rv = view.findViewById(R.id.installedApps_rv);
        searchInstalled_ET = view.findViewById(R.id.searchInstalled_ET);

        applicationsAdapter = new ApplicationsAdapter(getContext());


        inputMethodManager = (InputMethodManager) getContext().getSystemService( Context.INPUT_METHOD_SERVICE );
        searchInstalled_ET.setImeOptions( EditorInfo.IME_ACTION_SEARCH );

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchInstalled_ET.setOnClickListener(v -> {
            inputMethodManager.showSoftInput( searchInstalled_ET, InputMethodManager.SHOW_FORCED );
            searchInstalled_ET.setImeOptions( EditorInfo.IME_ACTION_SEARCH );
            searchInstalled_ET.setSingleLine();
        });

        searchInstalled_ET.setOnEditorActionListener((v, actionId, event) -> {

            if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENDCALL) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                inputMethodManager.hideSoftInputFromWindow(searchInstalled_ET.getWindowToken(), 0 );
            }
            return false;
        });

        searchInstalled_ET.addTextChangedListener(new TextWatcher() {
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
        ApplicationsTask allAppsTsk = new ApplicationsTask(getContext(), applicationsAdapter, installedApps_rv, false);
        allAppsTsk.execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        inputMethodManager.hideSoftInputFromWindow( searchInstalled_ET.getWindowToken(), 0 );
    }
}