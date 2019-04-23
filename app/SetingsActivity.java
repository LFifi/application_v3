package com.example.ledzi.application_v3.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SetingsActivity extends AppCompatActivity implements Const {

    private EditText edName, edAge, edTall, edWeight;

    private int weight, age, tall;
    private String name;
    private SharedPreferences sharedPref;
    private ToggleButton toggle;
    private boolean whetherMan=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings);
        setTitle("Ustawienia");

        sharedPref = getSharedPreferences(SHARED_PREF_TABLE_NAME, Activity.MODE_PRIVATE);

        edName = (EditText) findViewById(R.id.editTextName);
        edAge = (EditText) findViewById(R.id.editTextAge);
        edTall = (EditText) findViewById(R.id.editTextTall);
        edWeight = (EditText) findViewById(R.id.editTextWeight);
        toggle = (ToggleButton) findViewById(R.id.toggleButton);


        if(sharedPref.getString(SHARED_PREF_NAME,null)!=null)
        {
            edName.setText(sharedPref.getString(SHARED_PREF_NAME,null));
            edAge.setText(sharedPref.getInt(SHARED_PREF_AGE,0)+"");
            edTall.setText(sharedPref.getInt(SHARED_PREF_TALL,0)+"");
            edWeight.setText(sharedPref.getInt(SHARED_PREF_WEIGHT,0)+"");
            toggle.setChecked(!(sharedPref.getBoolean(SHARED_PREF_WHETHER_MAN, false)));
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    whetherMan=false;
                }
                else
                {
                    whetherMan=true;
                }
            }
        });

    }

    public void SaveActivity(View view) {

        name = edName.getText().toString();
        String stringWeight = edWeight.getText().toString();
        String stringAge = edAge.getText().toString();
        String stringTall = edTall.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Podaj imie",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!stringWeight.isEmpty())
            try {
                weight = Integer.parseInt(stringWeight);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        if (!stringTall.isEmpty())
            try {
                tall = Integer.parseInt(stringTall);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        if (!stringAge.isEmpty())
            try {
                age = Integer.parseInt(stringAge);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        if (age == 0) {
            Toast.makeText(getApplicationContext(),
                    "Zły wiek",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (weight == 0) {
            Toast.makeText(getApplicationContext(),
                    "Zła waga",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (tall == 0) {
            Toast.makeText(getApplicationContext(),
                    "Zły wzrost",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHARED_PREF_NAME, name);
        editor.putInt(SHARED_PREF_AGE, age);
        editor.putInt(SHARED_PREF_TALL, tall);
        editor.putInt(SHARED_PREF_WEIGHT, weight);
        editor.putBoolean(SHARED_PREF_WHETHER_MAN, whetherMan );
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);


        startActivity(intent);
        finish();


    }
}
