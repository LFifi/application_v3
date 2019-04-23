package com.example.ledzi.application_v3.app;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity implements Const{

    public static MyData myData;
    public List<PhysicalActivity> physicalActivityList;
    private ListView listView;
    private int typeAkt;
Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        listView = (ListView) findViewById(R.id.listView);
        Intent mIntent = getIntent();
        typeAkt = mIntent.getIntExtra(TYPE_AKT, 0);
        switch (typeAkt){
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
        myData = new MyData(this);
        physicalActivityList = new ArrayList<>();
        showList(typeAkt);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Toast.makeText(getApplicationContext(),
                        "to jet to",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ListaActivity.this, DetailsActivity.class);
                i.putExtra(ID_ACTIVITY, physicalActivityList.get(pos).getIdInData());



                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_lista,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Latest:
                physicalActivityList = myData.getAllAsList(typeAkt,true,false,true);
                break;
            case R.id.Oldest:
                physicalActivityList = myData.getAllAsList(typeAkt,true,false,false);

                break;
            case R.id.Tshortest:
                physicalActivityList = myData.getAllAsList(typeAkt,false,true,false);

                break;
            case R.id.Tlongest:

                physicalActivityList = myData.getAllAsList(typeAkt,false,true,true);
                break;
        }



        ListViewAdapter adapter= new ListViewAdapter(this,R.layout.custom_list_item, physicalActivityList, myData);
        listView.setAdapter(adapter);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());

    }

    void showList(int rodzajAkt)
    {

        physicalActivityList = myData.getAllAsList(rodzajAkt,false,false,false);
        ListViewAdapter adapter= new ListViewAdapter(this,R.layout.custom_list_item, physicalActivityList, myData);
        listView.setAdapter(adapter);
    }
}
