package com.phone.repair.phone.cleaner.app_2020.activities;

import android.app.ProgressDialog;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.phone.repair.phone.cleaner.app_2020.R;

public class SplashActivity extends AppCompatActivity {
    boolean isTermAccepted = false;
    View splash, termAndCondition;
    private Button decline_btn;
    private Button accept_btn;
    private CheckBox termAndCondition_Cb;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    InterstitialAd mInterstitialAd;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        reqNewInterstitial();
        preferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        splash = findViewById(R.id.splash);
        termAndCondition = findViewById(R.id.termAndCondition);
        decline_btn = findViewById(R.id.decline_btn);
        accept_btn = findViewById(R.id.accept_btn);
        termAndCondition_Cb = findViewById(R.id.termAndCondition_Cb);

        isTermAccepted = preferences.getBoolean("isTermCondition", false);
        handler = new Handler();

        Load_withAds(SplashActivity.this);

    }

    public void reqNewInterstitial() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public void fun()
    {
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

    public  void Load_withAds(final Context context) {
        try {
            ProgressDialog showDialog = ProgressDialog.show(context,getString(R.string.app_name),"Please wait for few seconds",true);
            new Handler().postDelayed(() -> {
                showDialog.dismiss();
                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                   fun();
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        reqNewInterstitial();
                        fun();
                     }
                });
            },2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}