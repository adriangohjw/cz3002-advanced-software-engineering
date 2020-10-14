package com.example.scansmart.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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
import java.util.zip.Inflater;

public class CartAdapter extends BaseAdapter {
    Context context;
    ViewGroup vgr;
    ArrayList<String> productName;
    ArrayList<Integer> price;
    ArrayList<Integer> quantity;
    int productID;
    int userID;
    LayoutInflater inflter;

    public CartAdapter(Context applicationContext, ArrayList<String> productName, ArrayList<Integer> price, ArrayList<Integer> quantity, int productID, int userID) {
        this.context = context;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productID = productID;
        this.userID = userID;
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
        vgr = vg;

        if (v == null) {
            v = inflter.inflate(R.layout.cartlist, null);

            //Handle buttons and add onClickListeners
            Button add= (Button)v.findViewById(R.id.add);

//            add.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    //do something
//                    String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/cart_products?shopper_id=%1$s&product_id=%2$s",
//                            userID,
//                            productID);
//                    // Request a string response from the provided URL.
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    // Display the first 500 characters of the response string.
//                                    Log.v("Yay", "Yay");
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.v("error", "error");
//                        }
//
//                    });
//                    // Add the request to the RequestQueue.
//                    RequestSingleton.getInstance(vgr.getActivity()).addToRequestQueue(stringRequest);
//                }
//
//            });

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







