package com.example.scansmart.ui.cart;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.scansmart.R;

import org.json.JSONArray;

public class CartAdapter{


    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cartlist, parent, false);
    }


    public void bindView(View view, Context context, Cursor cursor) {

        // getting theviews

        TextView productName, price, quantity;

        productName = view.findViewById(R.id.productName);
        price = view.findViewById(R.id.price);
        quantity = view.findViewById(R.id.quantity);

        AndroidNetworking.get("http://localhost:3000/movements/")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .setTag("test")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
//                        String nameofproduct =
//                        String pricesofproduct =
//                        String quantitysofproduct =
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

//        productName.setText(nameofproduct);
//        price.setText(pricesofproduct);
//        quantity.setText(quantitysofproduct);


    }
}