package com.example.scansmart.ui.history;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scansmart.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class HistoryList extends BaseAdapter {
    Context context;
    ArrayList<String> dateTime;
    ArrayList<String> store;
    ArrayList<Integer> item;
    LayoutInflater inflter;

    public HistoryList(Context applicationContext, ArrayList<String> dateTime, ArrayList<String> store, ArrayList<Integer> item) {
        this.context = context;
        this.dateTime = dateTime;
        this.store = store;
        this.item = item;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return dateTime.size();
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
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if (v == null) {
            v = inflter.inflate(R.layout.historylist, null);
            holder = new ViewHolder();
            holder.dateTime_tv = (TextView) v.findViewById(R.id.dateTime);
            holder.store_tv = (TextView) v.findViewById(R.id.store);
            holder.item_tv  = (TextView) v.findViewById(R.id.items);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        Log.v("dateTime1", String.valueOf(dateTime.get(position)));
        holder.dateTime_tv.setText(dateTime.get(position));
        holder.store_tv.setText(store.get(position));
        holder.item_tv.setText(Integer.toString(item.get(position)));

        return v;
    }
    static class ViewHolder {
        TextView dateTime_tv;
        TextView store_tv;
        TextView item_tv;
    }

}







