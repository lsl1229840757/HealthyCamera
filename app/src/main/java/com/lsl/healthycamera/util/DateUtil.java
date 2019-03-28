package com.lsl.healthycamera.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static long formatDate(Date now){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String nowStr = sdf.format(now);
        try {
            now = sdf.parse(nowStr);
            return now.getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return 0;
    }
    public static long formatNextDay(Date now){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String nowStr = sdf.format(now);
        try {
            now = sdf.parse(nowStr);
            return now.getTime()+24*60*60*1000;
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return 0;
    }
}
