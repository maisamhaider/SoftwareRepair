package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;

public class SplashActivity extends AppCompatActivity {
    boolean isTermAccepted = false;
    View splash, termAndCondition;
    private Button decline_btn;
    private Button accept_btn;
    private CheckBox termAndCondition_Cb;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        preferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        splash = findViewById(R.id.splash);
        termAndCondition = findViewById(R.id.termAndCondition);
        decline_btn = findViewById(R.id.decline_btn);
        accept_btn = findViewById(R.id.accept_btn);
        termAndCondition_Cb = findViewById(R.id.termAndCondition_Cb);

        isTermAccepted = preferences.getBoolean("isTermCondition", false);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isTermAccepted) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    if (!isTermAccepted) {
                        accept_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (termAndCondition_Cb.isChecked()) {
                                    editor.putBoolean("isTermCondition",true);
                                    editor.commit();
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(SplashActivity.this, "Please check the box first", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        decline_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }

                    splash.setVisibility(View.GONE);
                    termAndCondition.setVisibility(View.VISIBLE);
                }
            }
        },2000);
    }
}