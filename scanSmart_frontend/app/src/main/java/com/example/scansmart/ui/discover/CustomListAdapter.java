package com.example.scansmart.ui.discover;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scansmart.R;

import java.util.ArrayList;
/**
 * Created by tutlane on 23-08-2017.
 */
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
            holder.uOriginalPrice = (TextView) v.findViewById(R.id.original_price);
            holder.uProdImage  = (ImageView) v.findViewById(R.id.prod_icon);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getName());
        holder.uDiscPrice.setText(listData.get(position).getDisc_price());
        holder.uOriginalPrice.setText(listData.get(position).getOriginal_price());
        holder.uProdImage.setImageResource(listData.get(position).getImageUrl());
        return v;
    }
    static class ViewHolder {
        TextView uName;
        TextView uDiscPrice;
        TextView uOriginalPrice;
        ImageView uProdImage;
    }
}