package com.example.scansmart.ui.cart;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.scansmart.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> productName;
    ArrayList<Integer> price;
    ArrayList<Integer> quantity;
    LayoutInflater inflter;

    public CartAdapter(Context applicationContext, ArrayList<String> productName, ArrayList<Integer> price, ArrayList<Integer> quantity) {
        this.context = context;
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
            v = inflter.inflate(R.layout.cartlist, null);

            holder = new ViewHolder();
            holder.productName_tv = (TextView) v.findViewById(R.id.productName);
            holder.price_tv = (TextView) v.findViewById(R.id.price);
            holder.quantity_tv  = (TextView) v.findViewById(R.id.quantity);
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







