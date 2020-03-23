package com.example.propertymaintenance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class BulletinBoardAdapter extends BaseAdapter {

    final public String[] header = new String[] {
      "header1", "header2", "header3" };

    final public String[] message = new String[] {
            "message1", "message2", "message3" };

    private Context context;

    public BulletinBoardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listView;

        if (view == null) {
            listView = new View(context);

            listView = inflater.inflate(R.layout.list_bulletin_board, null);

            // Bulletin board HEADER
            TextView textView1 = listView.findViewById(R.id.textViewBulletinHeader);
            textView1.setText(header[position]);

            // Bulletin board MESSAGE
            TextView textView2 = listView.findViewById(R.id.textViewBulletinMessage);
            textView2.setText(message[position]);

        }
        else {
            listView = (View) view;
        }
        return listView;
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


}
