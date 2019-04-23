package com.example.ledzi.application_v3.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<PhysicalActivity> {
    private Activity context;
    private MyData myData;
    private List<PhysicalActivity> physicalActivityList;
    private ImageView iv;

    public ListViewAdapter(Activity context, int resource, List<PhysicalActivity> objects, MyData helper) {
        super(context, resource, objects);
        this.context = context;
        this.myData = helper;
        this.physicalActivityList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView =  layoutInflater.inflate(R.layout.custom_list_item, null, true);
        iv = (ImageView) rowView.findViewById(R.id.imageView3);
        TextView tvData = (TextView) rowView.findViewById(R.id.textViewData);
        TextView tvGodz = (TextView) rowView.findViewById(R.id.textViewGodz);
        TextView tvDystans = (TextView) rowView.findViewById(R.id.textViewDystans);
        TextView tvCzas = (TextView) rowView.findViewById(R.id.textViewCzas);
        DecimalFormat formatter = new DecimalFormat("00");
        tvData.setText(""+formatter.format(getItem(position).getDateD())+"-"+formatter.format(getItem(position).getDateM())
                +"-"+getItem(position).getDateR() );
        tvGodz.setText(formatter.format(getItem(position).getTimeH())+":"+formatter.format(getItem(position).getTimeM()));
        double dystans = getItem(position).getDistanse();
        tvDystans.setText(String.format("%.2f km", dystans/1000));
        tvCzas.setText(getItem(position).getDuration());

        switch (getItem(position).getTypeActivity()) {
            case 1:
            {iv.setImageResource(R.drawable.run_black);
            break;}
            case 2:
            {iv.setImageResource(R.drawable.bike_black);
                break;}
            case 3:
            {iv.setImageResource(R.drawable.rollers_black);
                break;  }
            default:
            {}
        }
        return rowView;
    }
}
