package com.example.softwarerepair.activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.models.FileModel;
import com.example.softwarerepair.utils.StorageUtility;

public class CacheCleanActivity extends AppCompatActivity {

    StorageUtility storageUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clean_activty);
        storageUtility = new StorageUtility();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        for(FileModel item : storageUtility.getTempFile(path))
        {
            Log.i("CacheCleanActivity",item.getFileName());
        }
    }
}