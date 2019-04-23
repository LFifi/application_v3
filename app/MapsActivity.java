package com.example.ledzi.application_v3.app;



import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Calendar;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Const {



    private GoogleMap googleMap2;
    private double longitude = 0;
    private double latitude = 0;
    private ArrayList<Location> route;
    private SupportMapFragment supportMapFragment;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Boolean isProviderEnabled = false;
    private boolean isActivityOpen = false;
    private boolean isStart = false;
    private LatLng  posTozoom;
    private int day, month, year, minute, hour;
    private TextView tvTime, tvDistanse, tvKcal;
    private double Time;
    private Chronometer mChronometer;
    private Location lastLocation=null, lastLastLocation =null;
    private double distanse=0.0;
    private double avgSpeed=0.0;
    private double kcal=0.0;
    private int typeActivity;
    private double weight=0.0;

    private PhysicalActivity af;
    private MyData myData;
    private SharedPreferences sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent mIntent = getIntent();
        typeActivity = mIntent.getIntExtra(TYPE_AKT, 0);
        switch (typeActivity){
            case 1:
                setTitle("Bieganie");
                break;
            case 2:
                setTitle("Jazda na rowerze");
                break;
            case 3:
                setTitle("Jazda na rolkach");
                break;
        }
        sharedPref = getSharedPreferences(SHARED_PREF_TABLE_NAME, Activity.MODE_PRIVATE);
        weight=sharedPref.getInt(SHARED_PREF_WEIGHT, 70);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer.setFormat("00:%s");
        tvTime = (TextView) findViewById(R.id.textTempo);
        tvDistanse = (TextView) findViewById(R.id.textDystans);
        tvKcal = (TextView) findViewById(R.id.textKcal);
        isActivityOpen = true;
        longitude = getIntent().getDoubleExtra("longitude", 21.020 );
        latitude = getIntent().getDoubleExtra("latitude", 52.259);
        route = new ArrayList<Location>();
        myData = new MyData(this);




        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("NO PERMISSION!!!");
            Toast.makeText(this, "no permision", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (isStart == true) {
                    posTozoom = new LatLng(location.getLatitude(),location.getLongitude());
                    route.add(location);
                    if(lastLocation!=null & lastLastLocation!=null)
                    drawPrimaryLinePath();
                    googleMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(posTozoom, 15));

                if(lastLocation==null ) {
                    lastLocation=location;
                }else if(lastLocation!=location) {
                    distanse += location.distanceTo(lastLocation);
                    tvDistanse.setText(String.format("%.3f Km", distanse/1000));
                    lastLastLocation =lastLocation;
                    lastLocation=location;

                }
                Time = (int) (SystemClock.elapsedRealtime() - mChronometer.getBase());
                avgSpeed= distanse/Time*1000*3.6;
                tvTime.setText(String.format("%.2f KM/H", avgSpeed));
                    switch (typeActivity){
                        case 1:
                            kcal=(0.0215*avgSpeed*avgSpeed*avgSpeed-0.1765*avgSpeed*avgSpeed+0.871*avgSpeed+1.4577)*weight*(Time/3600000);
                            break;
                        case 2:
                            kcal=(0.6345*avgSpeed*avgSpeed+0.7563*avgSpeed+36.724)*weight*(Time/60000);
                            break;
                        case 3:
                            if(avgSpeed>5)
                                kcal=((avgSpeed-10)*0.64+5)*weight*(Time/3600000);
                            else
                                kcal=1.8*weight*(Time/3600000);
                            break;
                    }
                tvKcal.setText(String.format("%.1f ", kcal));


                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                isProviderEnabled = true;
                Toast.makeText(MapsActivity.this, "GPS is ON.", Toast.LENGTH_LONG).show();
                Log.d(" isProviderEnabled: ", isProviderEnabled.toString());
            }

            @Override
            public void onProviderDisabled(String provider) {
                isProviderEnabled = false;
                Toast.makeText(MapsActivity.this, "GPS is OFF.", Toast.LENGTH_LONG).show();
                Log.d(" isProviderEnabled: ", isProviderEnabled.toString());
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (isActivityOpen == true) {
            googleMap2 = googleMap;

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
           googleMap2.setMyLocationEnabled(true);
            Time = (int) (SystemClock.elapsedRealtime() - mChronometer.getBase());

        }
    }

    private void drawPrimaryLinePath() {
        if (googleMap2 == null) {
            return;
        }


            PolylineOptions options = new PolylineOptions();
            options.color(Color.parseColor("#FFFF0000"));
            options.width(10);
            options.visible(true);
            options.add(new LatLng(lastLastLocation.getLatitude(), lastLastLocation.getLongitude()));
            options.add(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));

                googleMap2.addPolyline(options);


    }

    public void onClickStart(View view) {
        mChronometer.setBase(SystemClock.elapsedRealtime());

        mChronometer.start();

        route = new ArrayList<Location>();
        distanse=0.0;
        avgSpeed=0.0;
        isStart=true;
        Calendar calander = Calendar.getInstance();
        day = calander.get(Calendar.DAY_OF_MONTH);
        month = calander.get(Calendar.MONTH) + 1;
        year = calander.get(Calendar.YEAR);
        hour = calander.get(Calendar.HOUR_OF_DAY);
        minute = calander.get(Calendar.MINUTE);


    }

    public void onClickStop(View view) {
        mChronometer.stop();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Czy chcesz zakończyć aktywnosść?");
            LinearLayout layout = new LinearLayout(this);
            layout.setPadding(10, 10, 10, 10);
            layout.setOrientation(LinearLayout.VERTICAL);
            alertDialog.setView(layout);
            alertDialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isStart=false;
                    String s=mChronometer.getText().toString();

                    af = new PhysicalActivity(typeActivity,distanse,avgSpeed,kcal,s, day, month, year, minute, hour);
                    Toast.makeText(getApplicationContext(),
                            "Zapisano aktywność",
                            Toast.LENGTH_SHORT).show();
                    myData.addActivity(af);


                }
            });

            alertDialog.setNegativeButton("Nie", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mChronometer.start();
                }
            });

            alertDialog.show();

    }


}

