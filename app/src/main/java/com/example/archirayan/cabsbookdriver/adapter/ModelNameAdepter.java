package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.archirayan.cabsbookdriver.R;
import com.example.archirayan.cabsbookdriver.activity.VehicleInformation;
import com.example.archirayan.cabsbookdriver.model.MakeModelId;
import com.example.archirayan.cabsbookdriver.model.ModelName;

import java.util.ArrayList;

/**
 * Created by archirayan on 3/1/18.
 */

public class ModelNameAdepter extends BaseAdapter{

    Context context;
    private ArrayList<ModelName> modelNames;

    public ModelNameAdepter(VehicleInformation vehicleInformation, ArrayList<ModelName> modelNames) {
        this.context = vehicleInformation;
        this.modelNames = modelNames;
    }

    @Override
    public int getCount() {
        return modelNames.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.spinner_vehical_type, null);
        /*ImageView icon = (ImageView) view.findViewById(R.id.imageView);*/
        TextView names = (TextView) convertView.findViewById(R.id.textView);
        names.setText(modelNames.get(position).getName());
        return convertView;
    }
}
