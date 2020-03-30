package com.example.propertymaintenance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustodianServiceAdviceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titles;
    private ArrayList<String> housingCooperatives;

    public CustodianServiceAdviceAdapter(Context context, ArrayList titles, ArrayList housingCooperatives) {
        this.context = context;
        this.titles = titles;
        this.housingCooperatives = housingCooperatives;
    }

    @Override
    public int getCount() {
        return titles.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custodian_service_advice_list, null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvTitle = convertView.findViewById(R.id.tvCustodianServiceAdviceMessageTitle);
            holder.tvHousingCooperativeName = convertView.findViewById(R.id.tvCustodianServiceAdviceHousingCooperativeName);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(titles.get(position));
        holder.tvHousingCooperativeName.setText(housingCooperatives.get(position));
        return convertView;
    }

    private class ViewHolder {
        public TextView tvTitle;
        public TextView tvHousingCooperativeName;
    }
}

