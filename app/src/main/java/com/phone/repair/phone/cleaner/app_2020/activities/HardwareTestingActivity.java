package com.phone.repair.phone.cleaner.app_2020.activities;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.phone.repair.phone.cleaner.app_2020.R;

import java.util.Locale;

public class HardwareTestingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView simCrdTstVal_textView, isHeadphone_textView, bluetoothState_textView;
    TextToSpeech textToSpeech;
    Handler handler;
    int anInt = 0;
    boolean isHeadphoneConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_testing);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        handler = new Handler();

        ConstraintLayout vibration_linearLayout, simCard_linearLayout, displayTest_linearLayout, touchSensor_linearLayout,
                speakerTest_linearLayout, checkHeadPhone_linearLayout, checkBluetooth_linearLayout;
        simCrdTstVal_textView = findViewById( R.id.simCrdTstVal_textView);
        isHeadphone_textView = findViewById( R.id.isHeadphone_textView);
        bluetoothState_textView = findViewById( R.id.bluetoothState_textView);

        vibration_linearLayout = findViewById( R.id.vibration_linearLayout);
        simCard_linearLayout = findViewById( R.id.simCard_linearLayout);
        displayTest_linearLayout = findViewById( R.id.displayTest_linearLayout);
        touchSensor_linearLayout = findViewById( R.id.touchSensor_linearLayout);
        speakerTest_linearLayout = findViewById( R.id.speakerTest_linearLayout);
        checkHeadPhone_linearLayout = findViewById( R.id.checkHeadPhone_linearLayout);
        checkBluetooth_linearLayout = findViewById( R.id.checkBluetooth_linearLayout);

        vibration_linearLayout.setOnClickListener( this );
        simCard_linearLayout.setOnClickListener( this );
        displayTest_linearLayout.setOnClickListener( this );
        speakerTest_linearLayout.setOnClickListener( this );
        touchSensor_linearLayout.setOnClickListener( this );
        checkHeadPhone_linearLayout.setOnClickListener( this );
        checkBluetooth_linearLayout.setOnClickListener( this );

        textToSpeech = new TextToSpeech( getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage( Locale.US );
            }
        });


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vibration_linearLayout:
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vb.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vb.vibrate(500);}
                break;
            case R.id.simCard_linearLayout:
                TelephonyManager telMgr = (TelephonyManager) getSystemService( Context.TELEPHONY_SERVICE );
                int simState = telMgr.getSimState();
                switch (simState) {
                    case TelephonyManager.SIM_STATE_ABSENT:
                        simCrdTstVal_textView.setVisibility( View.VISIBLE );
                        simCrdTstVal_textView.setText( "Sim card is absent" );
                        handler.postDelayed( () -> simCrdTstVal_textView.setVisibility( View.GONE ), 2000 );
                        break;
                    case TelephonyManager.SIM_STATE_READY:
                        simCrdTstVal_textView.setVisibility( View.VISIBLE );
                        simCrdTstVal_textView.setText( "sim card is inserted" );
                        handler.postDelayed( () -> simCrdTstVal_textView.setVisibility( View.GONE ), 2000 );
                        break;
                    case TelephonyManager.SIM_STATE_UNKNOWN:
                        simCrdTstVal_textView.setVisibility( View.VISIBLE );
                        simCrdTstVal_textView.setText( "sim card unknown" );
                        handler.postDelayed( () -> simCrdTstVal_textView.setVisibility( View.GONE ), 2000 );
                        break;
                }

                break;
            case R.id.displayTest_linearLayout:
                Dialog dDialog = new Dialog( this, android.R.style.Theme_Light );
                dDialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
                View view = LayoutInflater.from( this ).inflate( R.layout.test_dialog_layout, null );
                dDialog.setContentView( view );
                ConstraintLayout cL = view.findViewById( R.id.testDisplayDialog_CL);
                cL.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (anInt == 0) {
                            cL.setBackgroundColor( Color.parseColor( "#7CFC00" ) );
                        } else if (anInt == 1) {
                            cL.setBackgroundColor( Color.parseColor( "#ff0000" ) );

                        } else if (anInt == 2) {
                            cL.setBackgroundColor( Color.parseColor( "#3346FF" ) );

                        }
                        if (anInt >= 3) {
                            anInt = 0;
                            dDialog.dismiss();
                        }
                        anInt++;
                    }
                } );
                dDialog.show();
                break;

            case R.id.touchSensor_linearLayout:
                Intent intent = new Intent( HardwareTestingActivity.this, DrawingSimpleAct.class );
                startActivity( intent );

                break;
            case R.id.checkHeadPhone_linearLayout:

                IntentFilter iFilter = new IntentFilter( Intent.ACTION_HEADSET_PLUG );
                getApplicationContext().registerReceiver( receiver, iFilter );

                if (isHeadphoneConnected) {
                    isHeadphone_textView.setVisibility( View.VISIBLE );
                    isHeadphone_textView.setText( "Headphone is plugged" );
                    handler.postDelayed( () -> isHeadphone_textView.setVisibility( View.GONE ), 2000 );
                } else {
                    isHeadphone_textView.setVisibility( View.VISIBLE );
                    isHeadphone_textView.setText( "Headphone is unplugged" );
                    handler.postDelayed( () -> isHeadphone_textView.setVisibility( View.GONE ), 2000 );
                }

                break;
            case R.id.speakerTest_linearLayout:
                textToSpeech.speak( "speaker is working.", TextToSpeech.QUEUE_FLUSH, null );
                break;
            case R.id.checkBluetooth_linearLayout:
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null) {
                    bluetoothState_textView.setVisibility( View.VISIBLE );
                    bluetoothState_textView.setText( "device does not have bluetooth" );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            bluetoothState_textView.setVisibility( View.GONE );
                        }
                    }, 2000 );
                } else if (bluetoothAdapter.isEnabled()) {
                    bluetoothState_textView.setVisibility( View.VISIBLE );
                    bluetoothState_textView.setText( "Bluetooth is on" );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            bluetoothState_textView.setVisibility( View.GONE );
                        }
                    }, 2000 );
                } else {
                    bluetoothState_textView.setVisibility( View.VISIBLE );
                    bluetoothState_textView.setText( "Bluetooth is off" );
                    handler.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            bluetoothState_textView.setVisibility( View.GONE );
                        }
                    }, 2000 );
                }
                break;


        }

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int isConnected = intent.getIntExtra( "state", -1 );
            if (isConnected == 0)
            {
                isHeadphoneConnected = false;
            }
            else if (isConnected ==1)
            {
                isHeadphoneConnected = true;
            }
            else
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        textToSpeech.stop();
    }
}