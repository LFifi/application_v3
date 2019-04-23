package com.example.ledzi.application_v3.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity implements Const {

    private TextView tvDate, tvTime, tvDistance, tvSpeed, tvCalories, tvDurationTime, tvTop;
    private ImageButton ib;
    private ImageView iv;
    private int id;
    private static MyData myData;
    private PhysicalActivity pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Szczegóły");

        iv = (ImageView) findViewById(R.id.imageView);
        ib = (ImageButton) findViewById(R.id.ImageButtonDelete);
        tvDate = (TextView) findViewById(R.id.textViewData);
        tvTime = (TextView) findViewById(R.id.textViewGodz);
        tvDistance = (TextView) findViewById(R.id.textViewDystans);
        tvSpeed = (TextView) findViewById(R.id.textViewPredkosc);
        tvCalories = (TextView) findViewById(R.id.textViewKcal);
        tvDurationTime = (TextView) findViewById(R.id.textViewCzas);
        tvTop = (TextView) findViewById(R.id.textViewTop);
        Intent mIntent = getIntent();
        id = mIntent.getIntExtra(ID_ACTIVITY, 0);
        myData = new MyData(this);
        pa = new PhysicalActivity();
        pa = myData.getWhereId(id);
        DecimalFormat formatter = new DecimalFormat("00");
        tvDate.setText(formatter.format(pa.getDateD())+"-"+formatter.format(pa.getDateM())+"-"+ pa.getDateR());
        tvTime.setText(formatter.format(pa.getTimeH())+":"+formatter.format(pa.getTimeM()));
        tvDistance.setText(String.format("%.3f km", pa.getDistanse()/1000));
        tvSpeed.setText(String.format("%.2f KM/H", pa.getSpeed()));
        tvCalories.setText(String.format("%.1f", pa.getKacl()));
        tvDurationTime.setText(pa.getDuration());
        tvTop.setText(""+ myData.rankingTOP(pa.getTypeActivity(), pa.getIdInData()));
        switch (pa.getTypeActivity()) {
            case 1:
            {iv.setImageResource(R.drawable.run_white);
                break;}

            case 2:
            {iv.setImageResource(R.drawable.bike_white);
                break;}

            case 3:
            {iv.setImageResource(R.drawable.rollers_white);
                break;}
            default:
            {Toast.makeText(getApplicationContext(),
                        "brak ikonki glownej",
                        Toast.LENGTH_SHORT).show();}
        }
    }

    public void btnDelete(View view) {


        Intent intent = new Intent(DetailsActivity.this, ListaActivity.class);
        intent.putExtra(TYPE_AKT,pa.getTypeActivity());
        myData.delete(pa.getIdInData());
        startActivity(intent);
        finish();

    }
}
