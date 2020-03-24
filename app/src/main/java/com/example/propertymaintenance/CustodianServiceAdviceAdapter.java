package com.example.propertymaintenance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustodianServiceAdviceAdapter extends BaseAdapter {

    private Context context;
    private final String[] titles;
    private final String[] housingCooperatives;

    public CustodianServiceAdviceAdapter(Context context, String[] titles, String[] messages) {
        this.context = context;
        this.titles = titles;
        this.housingCooperatives = messages;
    }

    @Override
    public int getCount() {
        return titles.length;
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
        View listView;

        if (convertView == null) {
            listView = new View(context);
            listView = inflater.inflate(R.layout.custodian_service_advice_list, null);

            TextView tvTitle = listView.findViewById(R.id.tvCustodianServiceAdviceMessageTitle);
            tvTitle.setText(titles[position]);

            TextView tvMessage = listView.findViewById(R.id.tvCustodianServiceAdviceMessageBody);
            tvMessage.setText(housingCooperatives[position]);
        }

        else {
            listView = convertView;
        }

        return listView;
    }
}

