package com.phone.repair.phone.cleaner.app_2020.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.activities.EachAppInfo;
import com.phone.repair.phone.cleaner.app_2020.annotations.StringsAnnotations;
import com.phone.repair.phone.cleaner.app_2020.fragments.BaseFrag;
import com.phone.repair.phone.cleaner.app_2020.utils.ApplicationUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bot.box.appusage.handler.Monitor;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationsHolder> implements Filterable {

    private List<String> appsList;
    private List<String> fullList;
    private Context context;
    private ApplicationUtility applicationUtility;
    boolean isSystemApp = false;
    BaseFrag baseFrag;

    @SuppressLint("NewApi")
    public ApplicationsAdapter(Context context) {

        this.context = context;
        applicationUtility = new ApplicationUtility(context);
        appsList = new ArrayList<>();
        fullList = new ArrayList<>();

    }

    public void setList(List<String> apps, boolean isSystemApp) {
        this.appsList.clear();
        this.fullList.clear();
        this.appsList = apps;
        this.fullList.addAll(apps);
        this.isSystemApp = isSystemApp;
    }

    @NonNull
    @Override
    public ApplicationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applications_recyclerview_lo, parent, false);
        return new ApplicationsHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ApplicationsHolder holder, final int position) {
        applicationUtility = new ApplicationUtility(context);
        String appName = applicationUtility.getAppName(appsList.get(position));
        final String appPackage = appsList.get(position);
        holder.allAppName_Tv.setText(appName);
        holder.allAppPackageName.setText(appPackage);
        Glide.with(context).load((Drawable) applicationUtility.getAppInformation(appPackage, StringsAnnotations.APP_ICON)).placeholder(R.mipmap.ic_launcher).into(holder.allAppImage_Iv);

        if (isSystemApp) {
            holder.unInstallApp_cl.setVisibility(View.GONE);
        } else {
            holder.unInstallApp_cl.setVisibility(View.VISIBLE);
        }

        if (Monitor.hasUsagePermission())


            holder.applicationRvLoMain_CL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PackageManager pm = context.getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage(appPackage);
                    if (intent != null) {
                        context.startActivity(intent);
                    }
                }
            });

        holder.unInstallApp_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (applicationUtility.isSystemApp(appPackage)) {
                    Toast.makeText(context, "Can not Uninstall system's application", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!applicationUtility.isAppLoaded(appPackage)) {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + appPackage));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                 }
            }
        });

        holder.detailApp_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EachAppInfo.class);
                intent.putExtra("pkg", appPackage);
                context.startActivity(intent);

//                if (context instanceof BaseFrag)
//                {
//                    baseFrag = (BaseFrag) context;
//                    baseFrag.newActivityAds(new EachAppInfo(),"pkg",appPackage);
//                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }



    public class ApplicationsHolder extends RecyclerView.ViewHolder {


        ConstraintLayout unInstallApp_cl, detailApp_cl, detailInfo_cl;
        TextView allAppName_Tv,allAppPackageName;
        ImageView allAppImage_Iv;
        ConstraintLayout applicationRvLoMain_CL;

        public ApplicationsHolder(@NonNull View itemView) {
            super(itemView);

            detailInfo_cl = itemView.findViewById(R.id.detailInfo_cl);
            unInstallApp_cl = itemView.findViewById(R.id.unInstallApp_cl);
            detailApp_cl = itemView.findViewById(R.id.detailApp_cl);
            allAppName_Tv = itemView.findViewById(R.id.allAppName_Tv);
            allAppPackageName = itemView.findViewById(R.id.allAppPackageName);
            allAppImage_Iv = itemView.findViewById(R.id.allAppImage_Iv);
            applicationRvLoMain_CL = itemView.findViewById(R.id.applicationRvLoMain_CL);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String string = constraint.toString().toLowerCase();
                for (int i=0; i<fullList.size(); i++)
                {
                    String app = applicationUtility.getAppInformation(fullList.get(i), StringsAnnotations.APP_NAME);
                    if (app.toLowerCase().startsWith(string)) {
                        filteredList.add(fullList.get(i));
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            appsList.clear();
            appsList.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };
}
