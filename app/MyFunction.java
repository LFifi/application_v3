package com.example.ledzi.application_v3.app;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Roxi on 26.06.2018.
 */

public class MyFunction extends AppCompatActivity{
    public int timeStringtoInt(String str)
    {
        int hh=(int)str.charAt(0)-'0';
        int h=(int)str.charAt(1)-'0';
        int mm=(int)str.charAt(3)-'0';
        int m=(int)str.charAt(4)-'0';
        int ss=(int)str.charAt(6)-'0';
        int s= (int)str.charAt(7)-'0';
        int time=s+10*ss+60*m+600*mm+h*3600+hh*36000;
        return time;
    }

    public String timeLongtoString(long timeLong)
    {
        int hours = (int) timeLong / 3600;
        int remainder = (int) timeLong - hours * 3600;
        int mins = remainder / 60;
        String time=hours+"h "+mins+"min";
        return time;

    }
}
