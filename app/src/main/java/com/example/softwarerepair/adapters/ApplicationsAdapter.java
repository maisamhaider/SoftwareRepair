package com.example.softwarerepair.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.softwarerepair.R;
import com.example.softwarerepair.activities.EachAppInfo;
import com.example.softwarerepair.annotations.StringsAnnotations;
import com.example.softwarerepair.utils.ApplicationUtility;

import java.util.ArrayList;
import java.util.List;

import bot.box.appusage.handler.Monitor;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationsHolder>   {

    private List<String> appsList;
    private List<String> fullList;
    private Context context;
    private ApplicationUtility applicationUtility;


    @SuppressLint("NewApi")
    public ApplicationsAdapter(Context context) {

        this.context = context;
        applicationUtility = new ApplicationUtility( context );
        appsList = new ArrayList<>(  );
        fullList = new ArrayList<>(  );

     }

     public void setList(List<String> apps){
        this.appsList.clear();
         this.fullList.clear();
        this.appsList = apps;
         this.fullList.addAll( apps );
     }

    @NonNull
    @Override
    public ApplicationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.applications_recyclerview_lo, parent, false );
        return new ApplicationsHolder( view );
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ApplicationsHolder holder, final int position) {
        applicationUtility = new ApplicationUtility(context);
         String appName = applicationUtility.getAppName( appsList.get( position )) ;
        final String appPackage = appsList.get( position );
         holder.allAppName_Tv.setText( appName );

        Glide.with(context).load((Drawable) applicationUtility.getAppInformation(appPackage, StringsAnnotations.APP_ICON)).placeholder(R.mipmap.ic_launcher).into(holder.allAppImage_Iv);


        if (Monitor.hasUsagePermission())


        holder.applicationRvLoMain_CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = context.getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage( appPackage );
                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        });

         holder.unInstallApp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (applicationUtility.isSystemApp( appPackage )) {
                    Toast.makeText( context, "Can not Uninstall system's application", Toast.LENGTH_SHORT ).show();
                    return;
                }
                if (!applicationUtility.isAppLoaded( appPackage )) {
                    Intent intent = new Intent( Intent.ACTION_DELETE );
                    intent.setData( Uri.parse( "package:" + appPackage ) );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity( intent );
//                    if (context instanceof ManageAppsActivity)
//                    ((ManageAppsActivity)context).loadApplications();
                }
            }
        });

         holder.detailApp_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, EachAppInfo.class);
                 intent.putExtra("pkg",appPackage);
                 context.startActivity(intent);
             }
         });


    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }


   public class ApplicationsHolder extends RecyclerView.ViewHolder {


       Button unInstallApp_btn,detailApp_btn;
       TextView allAppName_Tv;
       ImageView allAppImage_Iv;
       ConstraintLayout applicationRvLoMain_CL;
        public ApplicationsHolder(@NonNull View itemView) {
            super( itemView );

            unInstallApp_btn = itemView.findViewById(R.id.unInstallApp_btn);
            detailApp_btn = itemView.findViewById(R.id.detailApp_btn);
            allAppName_Tv = itemView.findViewById(R.id.allAppName_Tv);
            allAppImage_Iv = itemView.findViewById(R.id.allAppImage_Iv);
            applicationRvLoMain_CL = itemView.findViewById(R.id.applicationRvLoMain_CL);
        }
    }
}
