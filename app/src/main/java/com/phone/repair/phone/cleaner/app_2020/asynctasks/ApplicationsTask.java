package com.phone.repair.phone.cleaner.app_2020.asynctasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.phone.repair.phone.cleaner.app_2020.adapters.ApplicationsAdapter;
import com.phone.repair.phone.cleaner.app_2020.utils.ApplicationUtility;

import java.util.ArrayList;
import java.util.List;


public class ApplicationsTask extends AsyncTask<Void, Integer, String> {

    @SuppressLint("StaticFieldLeak")
    Context context;
    private ApplicationsAdapter applicationsAdapter;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<String> appList;
    private ApplicationUtility applicationUtility;
    private boolean isSystemApp =false;

    public ApplicationsTask(Context context, ApplicationsAdapter applicationsAdapter,
                            RecyclerView recyclerView,boolean isSystemApp) {
        this.context = context;
        this.applicationsAdapter = applicationsAdapter;
        this.recyclerView = recyclerView;
        this.appList = new ArrayList<>();
        applicationUtility = new ApplicationUtility(context);
        this.isSystemApp = isSystemApp;
     }

    @Override
    protected void onPreExecute() {
        linearLayoutManager = new LinearLayoutManager(context);

    }

    @Override
    protected String doInBackground(Void... voids) {
        if (isSystemApp)
        {
            appList = applicationUtility.getSysOrInstalledAppsList(true,false);

        }
        else
        {
            appList = applicationUtility.getSysOrInstalledAppsList(false,false);

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        applicationsAdapter.setList(appList,isSystemApp);
        recyclerView.setAdapter(applicationsAdapter);
        applicationsAdapter.notifyDataSetChanged();
    }

}
