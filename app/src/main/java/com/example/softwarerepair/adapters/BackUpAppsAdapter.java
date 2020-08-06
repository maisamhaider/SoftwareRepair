package com.example.softwarerepair.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import com.example.softwarerepair.annotations.StringsAnnotations;
import com.example.softwarerepair.utils.ApplicationUtility;
import com.example.softwarerepair.utils.StorageUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BackUpAppsAdapter extends RecyclerView.Adapter<BackUpAppsAdapter.ApplicationsHolder>   {

    private List<String> appsList;
     private Context context;
    private ApplicationUtility applicationUtility;

    StorageUtility storageUtility;

    @SuppressLint("NewApi")
    public BackUpAppsAdapter(Context context) {

        this.context = context;
        applicationUtility = new ApplicationUtility( context );
        appsList = new ArrayList<>(  );
     }

     public void setList(List<String> apps){
        this.appsList.clear();
         this.appsList = apps;
      }

    @NonNull
    @Override
    public ApplicationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.backup_apps_recyclerview_lo, parent, false );
        return new ApplicationsHolder( view );
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ApplicationsHolder holder, final int position) {
        applicationUtility = new ApplicationUtility(context);
        storageUtility =new  StorageUtility();
         String appName = applicationUtility.getAppName( appsList.get( position )) ;
        final String appPackage = appsList.get( position );
         holder.backupAppsName_Tv.setText( appName );

        Glide.with(context).load((Drawable) applicationUtility.getAppInformation(appPackage, StringsAnnotations.APP_ICON)).placeholder(R.mipmap.ic_launcher).into(holder.backupAppsImage_Iv);


        holder.backupAppsRvLoMain_CL.setOnClickListener(v -> {

        });

         holder.backupAppsExtract_btn.setOnClickListener(v -> {
                String packageName = appsList.get(position);
             PackageManager pm = context.getPackageManager();
             PackageInfo p = null;
             try {
                 p = pm.getPackageInfo(packageName, 0);
                 ApplicationInfo a = p.applicationInfo;
                 if (!((a.flags & ApplicationInfo.FLAG_SYSTEM) != 0)) {
                     File apk = new File(a.publicSourceDir);
                     storageUtility.copyFileOrDirectory(apk.getAbsolutePath());

                     Toast.makeText(context, "Apk generated", Toast.LENGTH_SHORT).show();
                 }


             } catch (PackageManager.NameNotFoundException e) {
                 e.printStackTrace();
             }


        });

         holder.backupAppsShare_btn.setOnClickListener(v -> {

             String packageName = appsList.get(position);
             PackageManager pm = context.getPackageManager();
             PackageInfo p = null;
             try {
                 p = pm.getPackageInfo(packageName, 0);
                 ApplicationInfo a = p.applicationInfo;
                 if (!((a.flags & ApplicationInfo.FLAG_SYSTEM) != 0)) {
                     File apk = new File(a.publicSourceDir);
                     Intent intent = new Intent();
                     intent.setAction(Intent.ACTION_SEND);

//                     Uri apkURI = apk.createNewFile()
////                      Uri apkURI = FilePr ovider.getUriForFile(context, context.getPackageName() + ".provider", apk.getAbsoluteFile());
//                     intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                     intent.setType("application/*");
//                     intent.putExtra(Intent.EXTRA_STREAM, apkURI );
//                     context.startActivity(Intent.createChooser(intent, "Share via"));




                 }


             } catch (PackageManager.NameNotFoundException e) {
                 e.printStackTrace();
             }
         });


    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }


   public class ApplicationsHolder extends RecyclerView.ViewHolder {


       Button backupAppsShare_btn,backupAppsExtract_btn;
       TextView backupAppsName_Tv;
       ImageView backupAppsImage_Iv;
       ConstraintLayout backupAppsRvLoMain_CL;
        public ApplicationsHolder(@NonNull View itemView) {
            super( itemView );

            backupAppsShare_btn = itemView.findViewById(R.id.backupAppsShare_btn);
            backupAppsExtract_btn = itemView.findViewById(R.id.backupAppsExtract_btn);
            backupAppsName_Tv = itemView.findViewById(R.id.backupAppsName_Tv);
            backupAppsImage_Iv = itemView.findViewById(R.id.backupAppsImage_Iv);
            backupAppsRvLoMain_CL = itemView.findViewById(R.id.backupAppsRvLoMain_CL);
        }
    }
}
