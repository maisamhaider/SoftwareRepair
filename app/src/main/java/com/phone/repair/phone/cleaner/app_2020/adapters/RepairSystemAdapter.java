package com.phone.repair.phone.cleaner.app_2020.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.models.AppsModel;
import com.phone.repair.phone.cleaner.app_2020.utils.ApplicationUtility;

import java.util.ArrayList;


public class RepairSystemAdapter extends RecyclerView.Adapter<RepairSystemAdapter.appHolder> {

    private Context context;
    private ApplicationUtility applicationUtility;
     String appName;
    String installTime;
    Drawable icon;
    private ArrayList<AppsModel> list;



    public RepairSystemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        applicationUtility = new ApplicationUtility(context);

    }

    public void setScanningData(String appName, String installTime, Drawable icon) {
        this.appName = appName;
        this.installTime = installTime;
        this.icon = icon;
        list.add(0,new AppsModel(appName,installTime,icon));
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public appHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_system_item_xml, parent, false);
        return new appHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull appHolder holder, int position) {
         String appName = list.get(position).getAppName();
         String time = list.get(position).getInstallTime();
        Drawable icon = list.get(position).getImage();
        holder.app_iv.setImageDrawable(icon);
         holder.appName_tv.setText(appName);
        holder.app_installedTime_tv.setText(time);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class appHolder extends RecyclerView.ViewHolder {
        ImageView app_iv;
         TextView appName_tv,app_installedTime_tv  ;
         public appHolder(@NonNull View itemView) {
            super(itemView);
             app_iv = itemView.findViewById(R.id.app_iv);
            appName_tv = itemView.findViewById(R.id.appName_tv);
             app_installedTime_tv = itemView.findViewById(R.id.app_installedTime_tv);

        }
    }


}
