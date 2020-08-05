package com.example.softwarerepair.activities;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.utils.StorageUtility;

import java.io.File;

public class EmptyFoldersActivity extends AppCompatActivity {
    int result = 0;
    TextView emptyFoldersAmount_tv;
    Button deleteEmptyFolder_btn;
    StorageUtility storageUtility;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_folders);
        storageUtility = new StorageUtility();
        emptyFoldersAmount_tv = findViewById(R.id.emptyFoldersAmount_tv);
        deleteEmptyFolder_btn = findViewById(R.id.deleteEmptyFolder_btn);

        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path);

        emptyFoldersAmount_tv.setText(String.valueOf(storageUtility.getAllEmptyFolders(file)));

        deleteEmptyFolder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!deleteEmptyFolder_btn.getText().equals("0"))
                {
                    storageUtility.deleteAllEmptyFolder(path);
                }
            }
        });

     }

}