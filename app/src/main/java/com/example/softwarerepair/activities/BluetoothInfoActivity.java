package com.example.softwarerepair.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;

public class BluetoothInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView bluetoothOff_tv,bluetoothName_tv,bluetoothAddress_tv,bluetoothMode_tv,bluetoothDiscovery_tv;
        bluetoothOff_tv = findViewById(R.id.bluetoothOff_tv);
        bluetoothName_tv = findViewById(R.id.bluetoothName_tv);
        bluetoothAddress_tv = findViewById(R.id.bluetoothAddress_tv);
        bluetoothMode_tv = findViewById(R.id.bluetoothMode_tv);
        bluetoothDiscovery_tv = findViewById(R.id.bluetoothDiscovery_tv);


        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter==null)
        {
            Toast.makeText(this, "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        }
        else

        {

            if (bluetoothAdapter.getState()== BluetoothAdapter.STATE_OFF)
            {
                bluetoothOff_tv.setText("off");
            }
            else if (bluetoothAdapter.getState()==BluetoothAdapter.STATE_ON){

                bluetoothOff_tv.setText("on");
            }

            bluetoothName_tv.setText(bluetoothAdapter.getName());
            bluetoothAddress_tv.setText(bluetoothAdapter.getAddress());
            bluetoothMode_tv.setText(String.valueOf(bluetoothAdapter.getScanMode()));

            if (bluetoothAdapter.isDiscovering())
            {
                bluetoothDiscovery_tv.setText("on");
            }else {
                bluetoothDiscovery_tv.setText("off");
            }}
    }
}