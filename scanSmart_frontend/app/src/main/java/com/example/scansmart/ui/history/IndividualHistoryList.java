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

public class IndividualHistoryList extends BaseAdapter {
    ArrayList<String> productName;
    ArrayList<Integer> price;
    ArrayList<Integer> quantity;
    LayoutInflater inflter;

    public IndividualHistoryList(Context applicationContext, ArrayList<String> productName, ArrayList<Integer> price, ArrayList<Integer> quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return productName.size();
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
            v = inflter.inflate(R.layout.individualhistorylist, null);
            holder = new ViewHolder();
            holder.productName_tv = (TextView) v.findViewById(R.id.productName_ind);
            holder.price_tv = (TextView) v.findViewById(R.id.price_ind);
            holder.quantity_tv  = (TextView) v.findViewById(R.id.quantity_ind);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.productName_tv.setText(productName.get(position));
        holder.price_tv.setText(Integer.toString(price.get(position)));
        holder.quantity_tv.setText(Integer.toString(quantity.get(position)));

        return v;
    }
    static class ViewHolder {
        TextView productName_tv;
        TextView price_tv;
        TextView quantity_tv;
    }

}







