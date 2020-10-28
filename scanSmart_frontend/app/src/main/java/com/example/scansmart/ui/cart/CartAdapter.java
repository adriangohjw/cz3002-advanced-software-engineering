package com.example.scansmart.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scansmart.MainActivity2;
import com.example.scansmart.R;
import com.example.scansmart.RequestSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.zip.Inflater;

public class CartAdapter extends BaseAdapter {

    private long mLastClickTime = 0;
    Context context;
    ArrayList<String> productName;
    ArrayList<Integer> price;
    ArrayList<Integer> quantity;
    ArrayList<Integer> productId;
    int userID;
    LayoutInflater inflter;
    EventListener listener;

    public interface EventListener {
        void onEvent(int productId, boolean increase, boolean isZero);
    }

    public CartAdapter(Context applicationContext, ArrayList<String> productName, ArrayList<Integer> price, ArrayList<Integer> quantity, ArrayList<Integer> productId, int userID, EventListener listener) {
        this.context = context;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
        this.userID = userID;
        this.listener = listener;
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
        final ViewHolder holder;

        if (v == null) {
            v = inflter.inflate(R.layout.cartlist, null);

            holder = new ViewHolder();
            holder.productName_tv = (TextView) v.findViewById(R.id.productName);
            holder.price_tv = (TextView) v.findViewById(R.id.price);
            holder.quantity_tv  = (TextView) v.findViewById(R.id.quantity);
            holder.plus = (ImageButton) v.findViewById(R.id.increase);
            holder.minus= (ImageButton) v.findViewById(R.id.decrease);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.productName_tv.setText(productName.get(position));
        holder.price_tv.setText(Integer.toString(price.get(position)));
        holder.quantity_tv.setText(Integer.toString(quantity.get(position)));
        holder.productId = productId.get(position);

        //Handle buttons and add onClickListeners
        holder.plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                //do something
                Log.wtf("plus", "plus clicked");
                Log.wtf("product ID added", Integer.toString(holder.productId));
                listener.onEvent(holder.productId, true, false);
            }

        });

        if (quantity.get(position) > 0){
            holder.minus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // mis-clicking prevention, using threshold of 1000 ms
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    //do something
                    Log.wtf("minus clicked", "minus clicked");
                    Log.wtf("product ID minus", Integer.toString(holder.productId));
                    listener.onEvent(holder.productId, false, false);
                }
            });
        }else{
            //delete item
            listener.onEvent(holder.productId, false, true);
            holder.minus.setAlpha(.5f);
            holder.minus.setClickable(false);
        }


        return v;
    }
    static class ViewHolder {
        TextView productName_tv;
        TextView price_tv;
        TextView quantity_tv;
        ImageButton plus;
        ImageButton minus;
        int productId;
    }
}







