package com.phone.repair.phone.cleaner.app_2020.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.phone.repair.phone.cleaner.app_2020.R;
import com.phone.repair.phone.cleaner.app_2020.annotations.StringsAnnotations;

import java.util.ArrayList;

public class FeaturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        LinearLayout features_LL = findViewById(R.id.features_LL);
        isWifiDirectSupported(this,features_LL);
    }

    public void addViewToLayout(String featureName, LinearLayout linearLayout, String s) {
        View view = LayoutInflater.from(this).inflate(R.layout.features_items_layout, null);
        TextView featureItem_tv = view.findViewById(R.id.featureItem_tv);
        TextView featureItemName_tv = view.findViewById(R.id.featureItemName_tv);
        featureItem_tv.setText(s);
        featureItemName_tv.setText(featureName);
        linearLayout.addView(view);
    }

    private void isWifiDirectSupported(Context ctx, LinearLayout features_LL) {
        int i = 0;
        PackageManager pm = ctx.getPackageManager();
        FeatureInfo[] features = pm.getSystemAvailableFeatures();

        ArrayList<String> fArrayList  = new ArrayList<>();

        for (FeatureInfo info : features) {
            fArrayList.add(info.name );
        }

        if (fArrayList.contains("android.hardware.wifi.direct")) {
            addViewToLayout("WiFi Direct",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("WiFi Direct",features_LL, StringsAnnotations.NOT_AVAILABLE);

        }
        if (fArrayList.contains("android.hardware.bluetooth")) {
             addViewToLayout("Bluetooth",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Bluetooth",features_LL, StringsAnnotations.NOT_AVAILABLE);

        }
        if (fArrayList.contains("android.hardware.microphone")) {
             addViewToLayout("Microphone",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Microphone",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.camera.flash")) {
             addViewToLayout("Camera Flash",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Camera Flash",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.camera.front")) {
             addViewToLayout("Camera Front",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Camera Front",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.touchscreen.multitouch.distinct")) {
             addViewToLayout("Multitouch Distinct",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Multitouch Distinct",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.nfc")) {
             addViewToLayout("NFC",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("NFC",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.bluetooth_le")) {
             addViewToLayout("Bluetooth LE",features_LL, StringsAnnotations.AVAILABLE);
        } else {
            addViewToLayout("Bluetooth LE",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.software.print")) {
             addViewToLayout("Printing",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Printing",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.telephony.gsm")) {
             addViewToLayout("GSM",features_LL, StringsAnnotations.AVAILABLE);
        } else {
            addViewToLayout("gsm",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.fingerprint")) {
             addViewToLayout("Fingerprint",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Fingerprint",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.hardware.consumerir")) {
             addViewToLayout("Consumer Ip",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("Consumer Ip",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.software.app_widgets")) {
             addViewToLayout("App Widgets",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("App Widgets",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.software.sip")) {
             addViewToLayout("SIP",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("SIP",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
        if (fArrayList.contains("android.software.sip.voip")) {
             addViewToLayout("SIP VOIP",features_LL, StringsAnnotations.AVAILABLE);

        } else {
            addViewToLayout("SIP.VOIP",features_LL, StringsAnnotations.NOT_AVAILABLE);
        }
    }
}