package com.example.propertymaintenance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HousingCooperativeListAdapter extends ArrayAdapter<HousingCooperativeObject> {

    private Context mContext;
    private ArrayList<HousingCooperativeObject> housingList;

    public HousingCooperativeListAdapter(@NonNull Context context, @NonNull ArrayList<HousingCooperativeObject> objects) {
        super(context, 0, objects);

        mContext = context;
        housingList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_housing_cooperative_list,parent,false);

        HousingCooperativeObject currentWorkout = housingList.get(position);

        // NAME
        TextView textView1 = listItem.findViewById(R.id.textViewHousingListName);
        textView1.setText(currentWorkout.getName());

        // ADDRESS
        TextView textView2 = listItem.findViewById(R.id.textViewHousingListAddress);
        textView2.setText(currentWorkout.getAddress());

        return listItem;
    }
}
