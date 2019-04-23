package com.example.ledzi.application_v3.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Roxi on 24.06.2018.
 */

public class MySensorService extends Service implements SensorEventListener , Const{
    private Context context;
    private MyData myData;
    private int day=0,month=0,year=0,count=0;
    private TextView tvStep;

    public MySensorService(Context context)  {
        this.context = context;
        myData = new MyData(context);

        SensorManager manager = (SensorManager)context.getSystemService( Context.SENSOR_SERVICE );
        Sensor countSensor = manager.getDefaultSensor( Sensor.TYPE_STEP_COUNTER );
        if(countSensor != null)
        {
            manager.registerListener( this, countSensor, SensorManager.SENSOR_DELAY_GAME );
        }else
        {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


            int  lastMeterStep=0, difrent=0, nowMeterStep=0;
            count=0;
            Calendar calander = Calendar.getInstance();
            day = calander.get(Calendar.DAY_OF_MONTH);
            month = calander.get(Calendar.MONTH) + 1;
            year = calander.get(Calendar.YEAR);
            int idDay;
            idDay= myData.isDay(day, month, year);
            if(idDay<0)
            {
                myData.addDayStep((int)event.values[0],day,month,year);
            } else
            {
                Cursor kursor= myData.getDayWhereid(idDay);
                if(kursor.moveToFirst()) {
                    count = kursor.getInt(1);
                    lastMeterStep = kursor.getInt(2);
                    nowMeterStep=(int)event.values[0];
                    if(nowMeterStep>lastMeterStep) {
                        difrent = nowMeterStep - lastMeterStep;
                        count += difrent;
                    }
                    myData.updateDay(idDay, count, nowMeterStep);
                }
            }
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}