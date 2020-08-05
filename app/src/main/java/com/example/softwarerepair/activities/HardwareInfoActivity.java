package com.example.softwarerepair.activities;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softwarerepair.R;
import com.example.softwarerepair.utils.TimeUtil;

import java.lang.reflect.Field;

public class HardwareInfoActivity extends AppCompatActivity {

    TimeUtil timeUtil = new TimeUtil();

    TextView androidVersion_Tv, manufacture_Tv, model_Tv, serial_Tv, id_Tv, brand_Tv, type_Tv,
            user_Tv, incremental_Tv, SDK_Tv, BOARD_Tv, HOST_Tv, FINGERPRINT_Tv,buildTime_tv,productInfo_tv,bootLoader_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_info);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        androidVersion_Tv = findViewById( R.id.androidVersion_Tv );
        manufacture_Tv =  findViewById( R.id.manufacture_Tv );
        model_Tv =  findViewById( R.id.model_Tv );
        serial_Tv = findViewById( R.id.serial_Tv );
        id_Tv =  findViewById( R.id.id_Tv );
        brand_Tv = findViewById( R.id.brand_Tv );
        type_Tv = findViewById( R.id.type_Tv );
        user_Tv =  findViewById( R.id.user_Tv );
        incremental_Tv =  findViewById( R.id.INCREMENTAL_Tv );
        SDK_Tv =  findViewById( R.id.SDK_Tv );
        BOARD_Tv =  findViewById( R.id.BOARD_Tv );
        HOST_Tv =  findViewById( R.id.HOST_Tv );
        FINGERPRINT_Tv =  findViewById( R.id.FINGERPRINT_Tv );
        buildTime_tv =  findViewById( R.id.buildTime_tv );
        productInfo_tv =  findViewById( R.id.productInfo_tv );
        bootLoader_tv =  findViewById( R.id.bootLoader_tv );

        //info
        StringBuilder builder = new StringBuilder();
        builder.append( Build.VERSION.RELEASE );

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt( new Object() );
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(fieldName);
            }
        }
        manufacture_Tv.setText( Build.MANUFACTURER );
        model_Tv.setText( Build.MODEL );
        androidVersion_Tv.setText( builder.toString());
        serial_Tv.setText( Build.SERIAL );
        id_Tv.setText( Build.ID );
        brand_Tv.setText( Build.BRAND );
        type_Tv.setText( Build.TYPE );
        user_Tv.setText( Build.USER );
        incremental_Tv.setText( Build.VERSION.INCREMENTAL );
        SDK_Tv.setText( Build.VERSION.SDK );
        BOARD_Tv.setText( Build.BOARD );
        HOST_Tv.setText( Build.HOST );
        FINGERPRINT_Tv.setText( Build.FINGERPRINT );
        buildTime_tv.setText( timeUtil.appInstalledTime(Build.TIME) );
        productInfo_tv.setText( Build.PRODUCT );
        bootLoader_tv.setText( Build.BOOTLOADER );




    }
}