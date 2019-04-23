package com.example.ledzi.application_v3.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends MyFunction implements Const {

    private TextView distance, Calories, time;
    private MyData myData;
    private double countDistanse, countCalories;
    private long countTime;
    public List<PhysicalActivity> physicalActivityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("Historia");

        distance = (TextView) findViewById(R.id.textViewDistance);
        Calories = (TextView) findViewById(R.id.textViewKacl);
        time = (TextView) findViewById(R.id.textViewTime);


        myData = new MyData(this);
        physicalActivityList = new ArrayList<>();
        physicalActivityList = myData.getAllAsList(0,false,false,false);
        int size = physicalActivityList.size();
        countDistanse=0.0;
        countCalories =0.0;
        countTime=0;
        for(int i =0; i<size; i++)
        {
            countDistanse+= physicalActivityList.get(i).getDistanse();
            countCalories += physicalActivityList.get(i).getKacl();
            countTime+=timeStringtoInt(physicalActivityList.get(i).getDuration());
        }
        distance.setText(String.format("%.2f",countDistanse/1000)+"Km");
        Calories.setText(String.format("%.0f", countCalories));
        time.setText(timeLongtoString(countTime));
    }

    public void btnRunList(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, ListaActivity.class);
        intent.putExtra(TYPE_AKT,1);
        startActivity(intent);
    }
    public void btnBikeList(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, ListaActivity.class);
        intent.putExtra(TYPE_AKT,2);
        startActivity(intent);
    }
    public void btnRollersList(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, ListaActivity.class);
        intent.putExtra(TYPE_AKT,3);
        startActivity(intent);
    }
    public void btnAllList(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, ListaActivity.class);
        intent.putExtra(TYPE_AKT,0);
        startActivity(intent);
    }
    public void btnChartActivity(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, ChartBarActivity.class);
        intent.putExtra(TYPE_CHART,0);
        startActivity(intent);
    }
    public void btnChartSteps(View view)
    {
        Intent intent = new Intent(HistoryActivity.this, ChartBarActivity.class);
        intent.putExtra(TYPE_CHART,1);
        startActivity(intent);
    }
}
