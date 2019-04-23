package com.example.ledzi.application_v3.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartBarActivity extends AppCompatActivity {

    private BarChart barChart;
    private MyData myData;
    private double Trip;
    private int year, month, day, typeChart;
    private List<PhysicalActivity> physicalActivityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_bar);
        setTitle("");
        barChart = (BarChart) findViewById(R.id.barchart);
        Intent mIntent = getIntent();
        typeChart=mIntent.getIntExtra("typeChart", 2);

        myData = new MyData(this);
        physicalActivityList = new ArrayList<>();

        rysuj(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if(typeChart==0){
            setTitle("Wszystkie");
        getMenuInflater().inflate(R.menu.menu_chart,menu);}
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Run:
                setTitle("Biegi");
                rysuj(1);
                break;
            case R.id.Bike:
                setTitle("Jazda na rowerze");
                rysuj(2);

                break;
            case R.id.Rollers:
                setTitle("Jazda na rolkach");
                rysuj(3);

                break;
            case R.id.All:
                setTitle("Wszystkie");
                rysuj(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void rysuj(int rodzaj){
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month  = (cal.get(Calendar.MONTH)+1);
        day = cal.get(Calendar.DAY_OF_MONTH);
        DecimalFormat formatter = new DecimalFormat("00");



    ArrayList<String> labels = new ArrayList<>();
    ArrayList<BarEntry> barEntries = new ArrayList<>();
    for(int i=0; i<30; i++){
        switch (typeChart) {
            case 0:
                Trip=0.0;
                physicalActivityList = new ArrayList<>();
                physicalActivityList = myData.getAllSelectionDate(rodzaj, day, month, year);

                if (physicalActivityList.size() > 0) {
                    for (int j = 0; j < physicalActivityList.size(); j++) {
                        Trip += physicalActivityList.get(j).getDistanse();
                    }
                }
                barEntries.add(new BarEntry((float) Trip/1000,i));

                labels.add(""+formatter.format(day)+"."+formatter.format(month));
                break;
            case 1:
                setTitle("Kroki");
                int count= myData.getcountfromday(day, month, year);
                barEntries.add(new BarEntry(count,i));

                labels.add(""+formatter.format(day)+"."+formatter.format(month));
                break;
                default:

                    break;

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


    BarDataSet barDataSet = new BarDataSet(barEntries,null);
    barDataSet.setColor(ColorTemplate.rgb("#00ff00"));
    BarData data = new BarData(labels,barDataSet);
    data.setDrawValues(false);

    barChart.setDrawBarShadow(false);
    barChart.setDragEnabled(true);
    barChart.setScaleEnabled(true);
    barChart.setDrawValueAboveBar(true);
    barChart.setMaxVisibleValueCount(50);
    barChart.setPinchZoom(false);
    barChart.getLegend().setEnabled(false);
    barChart.setTouchEnabled(false);
    barChart.setDrawGridBackground(false);
    barChart.setDescription("");
    XAxis xAxis = barChart.getXAxis();
    xAxis.setLabelsToSkip(1);
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setLabelRotationAngle(60);
    xAxis.setAxisLineColor(ColorTemplate.rgb("#ff0000"));
    xAxis.setAxisLineWidth(0.5f);
    YAxis ylAxis = barChart.getAxisLeft();
    ylAxis.setAxisMinValue(0f);
    YAxis yrAxis = barChart.getAxisRight();
    yrAxis.setAxisMinValue(0f);
    barChart.animateY(3000);
    barChart.setData(data);

}

}
