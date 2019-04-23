package com.example.ledzi.application_v3.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingRunActivity extends AppCompatActivity implements Const {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_run);
        setTitle("");
    }

    public void BtnRun(View view){
        Intent intent = new Intent(SettingRunActivity.this, MapsActivity.class);
        intent.putExtra(TYPE_AKT,1);
        startActivity(intent);
    }
    public void BtnBike(View view){
        Intent intent = new Intent(SettingRunActivity.this, MapsActivity.class);
        intent.putExtra(TYPE_AKT,2);
        startActivity(intent);
    }
    public void BtnRollers(View view){
        Intent intent = new Intent(SettingRunActivity.this, MapsActivity.class);
        intent.putExtra(TYPE_AKT,3);
        startActivity(intent);
    }

    public void BtnAdd(View view){
        Intent intent = new Intent(SettingRunActivity.this, AddManuallyActivity.class);
        startActivity(intent);
    }
}
