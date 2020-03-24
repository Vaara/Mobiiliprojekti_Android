package com.example.propertymaintenance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class BulletinBoardAdapter extends BaseAdapter {

    final public String[] header = new String[] {
            "header1", "header2", "header3", "header4", "header5", "header6", "header7" };

    final public String[] message = new String[] {
            "message1...............................................................................",
            "message2...............................................................................",
            "message3...............................................................................",
            "message4...............................................................................",
            "message5...............................................................................",
            "message6...............................................................................",
            "message7..............................................................................." };

    private Context context;

    public BulletinBoardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return header.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = null;

        if (view == null) {
            view = inflater.inflate(R.layout.list_bulletin_board, null);
            holder = new ViewHolder();
            view.setTag(holder);

            // Bulletin board HEADER
            holder.textView1 = (TextView) view.findViewById(R.id.textViewBulletinHeader);

            // Bulletin board MESSAGE
            holder.textView2 = (TextView) view.findViewById(R.id.textViewBulletinMessage);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView1.setText(header[position]);
        holder.textView2.setText(message[position]);
        return view;
    }

    private class ViewHolder {
        public TextView textView1;
        public TextView textView2;
    }
}
