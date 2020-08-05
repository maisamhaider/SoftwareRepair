package com.example.softwarerepair.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;

import java.io.IOException;
import java.io.InputStream;

public class ProcessorInfoActivity extends AppCompatActivity {
     ProcessBuilder pB;
    String holder = "";
    String[] data = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process process ;
    byte[] byteArray;
    private int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processor_info);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        LinearLayout processor_LL = findViewById(R.id.processor_LL);

        byteArray = new byte[1024];
        try{
            pB = new ProcessBuilder(data);
            process = pB.start();
            inputStream = process.getInputStream();
            while(inputStream.read(byteArray) != -1){
                holder = holder + new String(byteArray);
            }
            inputStream.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        String[] infoList = holder.split("\n");
        for (int i = 0; i < infoList.length; i++) {
            if (infoList[i].contains("Hardware")){
                counter = i+1;
                break;
            }
        }


        for (int i = 0; i < infoList.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.processor_items_layout, null);
            TextView title_tv = view.findViewById(R.id.title_tv);
            TextView description_tv = view.findViewById(R.id.description_tv);

            try {
                String[] titleDetail = infoList[i].split(":");
                title_tv.setText(titleDetail[0]);
                description_tv.setText(titleDetail[1]);
                processor_LL.addView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}