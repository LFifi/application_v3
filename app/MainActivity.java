package com.example.ledzi.application_v3.app;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity  extends  MyFunction implements Const
{

    private int day, month, year, tall, weight;
    private TextView tvStep, tvDistanse, tvCalories;
    private TextView tvDistanseActivityWeek, tvCaloriesActivityWeek, tvDistanseStepsWeek, tvCaloriesStepsWeek, tvTimeActivityWeek;
    private static MyData myData;

    private SharedPreferences sharedPref;
    private List<PhysicalActivity> physicalActivityList;


    @SuppressLint("Se3rviceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPref = getSharedPreferences(SHARED_PREF_TABLE_NAME, Activity.MODE_PRIVATE);
        if (sharedPref.getString(SHARED_PREF_NAME, null) == null) btnSetings();

        setTitle("Witaj "+sharedPref.getString(SHARED_PREF_NAME,""+"!"));
        tvStep = (TextView) findViewById(R.id.textCounterSteps);
        tvDistanse = (TextView) findViewById(R.id.textViewDistanseMain);
        tvCalories = (TextView) findViewById(R.id.textViewKcalMain);
        tvDistanseActivityWeek = (TextView) findViewById(R.id.textViewDistansActivityMain);
        tvCaloriesActivityWeek = (TextView) findViewById(R.id.textViewKcalActivityMain);
        tvDistanseStepsWeek = (TextView) findViewById(R.id.textViewDistansStepsMain);
        tvCaloriesStepsWeek = (TextView) findViewById(R.id.textViewKcalStepsMain);
        tvTimeActivityWeek = (TextView) findViewById(R.id.textViewTimeActivityMain);



        tall = sharedPref.getInt(SHARED_PREF_TALL, 170);
        weight = sharedPref.getInt(SHARED_PREF_WEIGHT, 70);

        myData = new MyData(this);
        MySensorService sensor = new MySensorService(this);
        displaySteps();
        displayWeek();


        Thread t = new Thread() {


            @Override
            public void run() {

                while (!isInterrupted()) {

                    try {
                        Thread.sleep(3000);  //1000ms = 1 sec

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                displaySteps();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPref.getString(SHARED_PREF_NAME, null) == null) btnSetings();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Ustawienia:
                btnSetings();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displaySteps() {
        Calendar calander = Calendar.getInstance();
        day = calander.get(Calendar.DAY_OF_MONTH);
        month = calander.get(Calendar.MONTH) + 1;
        year = calander.get(Calendar.YEAR);
        int steps = myData.getcountfromday(day, month, year);
        tvStep.setText(String.valueOf(steps));
        if (sharedPref.getBoolean(SHARED_PREF_WHETHER_MAN, true)) {
            tvDistanse.setText(String.format("%.2f km", steps * RATIO_MAN * tall / 100000));
            tvCalories.setText(String.format("%.1f", steps * RATIO_MAN * tall / 100000 * weight * RATIO_WALK));
        } else {
            tvDistanse.setText(String.format("%.2f km", steps * RATIO_WOMAN * tall / 100000));
            tvCalories.setText(String.format("%.1f", steps * RATIO_WOMAN * tall / 100000 * weight * RATIO_WALK));
        }
    }

    public void displayWeek() {
        Calendar calander = Calendar.getInstance();
        day = calander.get(Calendar.DAY_OF_MONTH);
        month = calander.get(Calendar.MONTH) + 1;
        year = calander.get(Calendar.YEAR);
        int dayOfWeek = calander.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) dayOfWeek = 8;
        double distansActivity = 0.0, kcalActivity = 0.0;
        int steps = 0;
        long timeActivity = 0;
        for (int i = 1; i < dayOfWeek; i++) {
            physicalActivityList = new ArrayList<>();
            physicalActivityList = myData.getAllSelectionDate(0, day, month, year);
            steps += myData.getcountfromday(day, month, year);
            for (int j = 0; j < physicalActivityList.size(); j++) {
                distansActivity += physicalActivityList.get(j).getDistanse();
                kcalActivity += physicalActivityList.get(j).getKacl();
                String str = physicalActivityList.get(j).getDuration();

                timeActivity += timeStringtoInt(str);
            }
            if (day == 1 && month == 0) {
                year -= 1;
                month = 12;
                day = 31;
            } else if (day == 1) {
                month -= 1;
                if (month == 2) {
                    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) day = 29;
                    else day = 28;
                } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                    day = 30;
                } else {
                    day = 31;
                }
            } else day -= 1;
        }

        tvDistanseActivityWeek.setText(String.format("%.2f km", distansActivity / 1000));
        tvCaloriesActivityWeek.setText(String.format("%.1f", kcalActivity));
        if (sharedPref.getBoolean(SHARED_PREF_WHETHER_MAN, true)) {
            tvDistanseStepsWeek.setText(String.format("%.2f km", steps * RATIO_MAN * tall / 100000));
            tvCaloriesStepsWeek.setText(String.format("%.1f", steps * RATIO_MAN * tall / 100000 * weight * RATIO_WALK));
        } else {
            tvDistanseStepsWeek.setText(String.format("%.2f km", steps * RATIO_WOMAN * tall / 100000));
            tvCaloriesStepsWeek.setText(String.format("%.1f", steps * RATIO_WOMAN * tall / 100000 * weight * RATIO_WALK));
        }

        tvTimeActivityWeek.setText(timeLongtoString(timeActivity));
    }

    public void btnTrening(View view) {
        Intent intent = new Intent(MainActivity.this, SettingRunActivity.class);
        startActivity(intent);
    }

    public void btnHistoria(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    public void btnSetings() {
        Intent intent = new Intent(MainActivity.this, SetingsActivity.class);
        startActivity(intent);
    }

}
