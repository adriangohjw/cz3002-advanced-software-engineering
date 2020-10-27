package com.example.scansmart.ui.discover;
import android.content.Context;
import android.graphics.Paint;
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

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<ListItem> listData;
    private LayoutInflater layoutInflater;
    public CustomListAdapter(Context aContext, ArrayList<ListItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.prod_name);
            holder.uDiscPrice = (TextView) v.findViewById(R.id.disc_price);
            holder.uPrice = (TextView) v.findViewById(R.id.original_price);

            holder.uProdImage  = (ImageView) v.findViewById(R.id.prod_icon);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.uName.setText(listData.get(position).getName());
        holder.uDiscPrice.setText(listData.get(position).getDiscounted_price());
        holder.uPrice.setText(listData.get(position).getPrice());
        if (listData.get(position).getDiscounted_price()!=null)
        {holder.uPrice.setPaintFlags(holder.uPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.uPrice.setText("original price");
        }
        //holder.uProdImage.setImageResource(listData.get(position).getImageUrl());
        holder.uProdImage.setImageResource(R.drawable.icon1);

        return v;
    }
    static class ViewHolder {
        TextView uName;
        TextView uDiscPrice;
        TextView uPrice;
        ImageView uProdImage;
    }
}