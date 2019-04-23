package com.example.ledzi.application_v3.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AddManuallyActivity extends AppCompatActivity {
    private int year,month,day,hour,minute, durationM,durationH, rodzajAktywnosci;
    private String date, time, duration;
    private TextView tvdData, tvTime, tvDuration;
    private ImageButton btnDataEdit, btnTimeEdit, btnDurationEdit, btnRun, btnBike, btnRollers;
    private Button btnSave;
    private EditText eTDistance;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private TimePickerDialog.OnTimeSetListener mDurationSetListener;
    private double distance;
    public static MyData myData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manually);
        setTitle("");
        btnDataEdit = (ImageButton) findViewById(R.id.imageButtonEditDate);
        btnTimeEdit = (ImageButton) findViewById(R.id.imageButtonEditTime);
        btnDurationEdit = (ImageButton) findViewById(R.id.imageButtonEditDuration);
        tvdData = (TextView) findViewById(R.id.textViewDate);
        tvTime = (TextView) findViewById(R.id.textViewTime);
        tvDuration = (TextView) findViewById(R.id.textViewDuration);
        eTDistance = (EditText) findViewById(R.id.editTextDistance);
        btnRun = (ImageButton) findViewById(R.id.imageButtonRun);
        btnBike = (ImageButton) findViewById(R.id.imageButtonBike);
        btnRollers = (ImageButton) findViewById(R.id.imageButtonRollers);
        btnSave = (Button) findViewById(R.id.buttonSave);
        final  DecimalFormat formatter = new DecimalFormat("00");

        rodzajAktywnosci=0;
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month  = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        durationM=0;
        durationH=0;
        date = formatter.format(day)+"-"+formatter.format(month+1)+"-"+year;
        time = formatter.format(hour)+":"+formatter.format(minute);
        tvdData.setText(date);
        tvTime.setText(time);

        btnDataEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(
                        AddManuallyActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearr, int monthh, int dayy) {
                day=dayy;
                month=monthh;
                year=yearr;
                String date = formatter.format(day)+"-"+formatter.format(month+1)+"-"+year;
                tvdData.setText(date);



            }
        };

        btnTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timeDialog = new TimePickerDialog(
                        AddManuallyActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mTimeSetListener,
                        hour,minute,true);
                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();

            }
        });
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourr, int minutee) {
                hour=hourr;
                minute=minutee;
                String text = formatter.format(hour)+":"+formatter.format(minute);
                tvTime.setText(text);
            }
        };


        btnDurationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timeDialog = new TimePickerDialog(
                        AddManuallyActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDurationSetListener,
                        durationH,durationM,true);
                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();

            }
        });
        mDurationSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int h, int m) {
                durationH=h;
                durationM=m;
                String text1 = formatter.format(durationH)+":"+formatter.format(durationM);
                tvDuration.setText(text1);
                duration=text1+":00";

            }
        };
    }


    public void PhotoClick(View view) {

        switch (view.getId()) {
            case R.id.imageButtonRun:
                btnRun.setColorFilter(0xaaaa0000);
                btnBike.setColorFilter(0x00000000);
                btnRollers.setColorFilter(0x00000000);
                rodzajAktywnosci = 1;
                break;
            case R.id.imageButtonBike:
                btnRun.setColorFilter(0x00000000);
                btnBike.setColorFilter(0xaaaa0000);
                btnRollers.setColorFilter(0x00000000);
                rodzajAktywnosci = 2;

                break;
            case R.id.imageButtonRollers:

                btnRun.setColorFilter(0x00000000);
                btnBike.setColorFilter(0x00000000);
                btnRollers.setColorFilter(0xaaaa0000);
                rodzajAktywnosci = 3;
                break;
        }
    }

    public void SaveActivity(View view) {

        String text =eTDistance.getText().toString();
        if(!text.isEmpty())
            try
            {
                distance= Double.parseDouble(text);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        if(rodzajAktywnosci==0)
        {
            Toast.makeText(getApplicationContext(),
                    "Nie wybrano rodzaju aktywności",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(distance==0)
        {
            Toast.makeText(getApplicationContext(),
                    "Zła wartość dystansu",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(durationH==0 && durationM==0)
        {
            Toast.makeText(getApplicationContext(),
                    "Zła wartość trwania aktywnosci",
                    Toast.LENGTH_SHORT).show();
    return;
        }
        double temp=durationM/60.0;
        double predkosc=distance/(durationH+temp);
        myData = new MyData(this);
        PhysicalActivity af = new PhysicalActivity(rodzajAktywnosci,distance*1000,predkosc,60*distance,duration, day,month+1,year,minute,hour);
        myData.addActivity(af);
        Intent intent = new Intent(AddManuallyActivity.this, MainActivity.class);

        startActivity(intent);
        finish();

    }


}
