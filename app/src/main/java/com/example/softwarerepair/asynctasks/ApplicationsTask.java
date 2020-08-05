package com.example.softwarerepair.asynctasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.example.softwarerepair.adapters.ApplicationsAdapter;
import com.example.softwarerepair.utils.ApplicationUtility;

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

    public ApplicationsTask(Context context, ApplicationsAdapter applicationsAdapter,
                            RecyclerView recyclerView) {
        this.context = context;
        this.applicationsAdapter = applicationsAdapter;
        this.recyclerView = recyclerView;
        this.appList = new ArrayList<>();
        applicationUtility = new ApplicationUtility(context);
     }

    @Override
    protected void onPreExecute() {
        linearLayoutManager = new LinearLayoutManager(context);

    }

    @Override
    protected String doInBackground(Void... voids) {
        appList = applicationUtility.getSysOrInstalledAppsList(true,true);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        applicationsAdapter.setList(appList);
        recyclerView.setAdapter(applicationsAdapter);
        applicationsAdapter.notifyDataSetChanged();
    }

}
