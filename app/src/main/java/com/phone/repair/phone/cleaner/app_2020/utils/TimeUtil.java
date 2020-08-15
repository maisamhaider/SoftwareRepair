package com.phone.repair.phone.cleaner.app_2020.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {


    public String appInstalledTime(long milliSec)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSec);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy h:mm;ss a");
        return simpleDateFormat.format(calendar.getTime());
    }

    
}
