package com.example.propertymaintenance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.ArrayList;


public class BulletinBoardAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titles;
    private ArrayList<String> messages;

    public BulletinBoardAdapter(Context context, ArrayList titles, ArrayList messages) {
        this.context = context;
        this.titles = titles;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return titles.size();
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
        holder.textView1.setText(titles.get(position));
        holder.textView2.setText(messages.get(position));
        return view;
    }

    private class ViewHolder {
        public TextView textView1;
        public TextView textView2;
    }
}
