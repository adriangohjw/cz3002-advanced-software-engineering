package com.example.scansmart.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scansmart.MainActivity2;
import com.example.scansmart.R;
import com.example.scansmart.RequestSingleton;
import com.example.scansmart.ui.history.HistoryList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {
    int userID = 8;
    ArrayList<String> productName;
    ArrayList<Integer> price;
    ArrayList<Integer> quantity;
    ListView simpleList;
    TextView itemCount;
    TextView price_count;
    int total_items = 0;
    int final_price;
    int product_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        //userID = ((MainActivity2) getActivity()).getUserID();
        //initialise lists
        productName = new ArrayList<String>();
        price = new ArrayList<Integer>();
        quantity = new ArrayList<Integer>();
        getOrders();

        simpleList = (ListView) root.findViewById(R.id.list1);
        itemCount = (TextView) root.findViewById(R.id.total_items1);
        price_count = (TextView) root.findViewById(R.id.price_count);

        Button scan = root.findViewById(R.id.scan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CartBarcodeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.shopping_cart, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button check_out = root.findViewById(R.id.check_out);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CartBarcodeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.shopping_cart, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button clear = root.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/%1$s/cart",
                        userID);
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                simpleList.setAdapter(null);
                                Log.v("Yay", "Yay");
                                getOrders();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("error", "error");
                    }


                });
                // Add the request to the RequestQueue.
                RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
            }
        });

        return root;
    }

    public void getOrders() {
        //get orders
        productName.clear();
        price.clear();
        quantity.clear();
        String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/%1$s/cart",
                userID);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Yay", "Yay");
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            Log.wtf("json", String.valueOf(jsonObj));
                            //Log.v("response", response);
                            final_price = jsonObj.getInt("total_discounted_cart_amount");
                            JSONArray ja_data = jsonObj.getJSONArray("cart_products");
                            int length = ja_data.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject orderjsonObj = ja_data.getJSONObject(i);
                                //Log.v("object", String.valueOf(orderjsonObj));
                                product_id = orderjsonObj.getInt("id");
                                Log.wtf("product id", Integer.toString(product_id));
                                int quantity_var = orderjsonObj.getInt("quantity");
                                total_items += quantity_var;
                                quantity.add(quantity_var);
                                int price_var = orderjsonObj.getInt("total_discounted_price");
                                price.add(price_var);
                                JSONObject detailsJObject = orderjsonObj.getJSONObject("product");
                                //Log.wtf("details", String.valueOf(detailsJObject));
                                String name_var = detailsJObject.getString("name");
                                productName.add(name_var);
                            }
                            if (getActivity() != null) {
                                CartAdapter cartAdapter = new CartAdapter(getActivity().getApplicationContext(), productName, price, quantity, product_id, userID);
                                simpleList.setAdapter(cartAdapter);

                                itemCount.setText(String.valueOf(total_items));
                                price_count.setText(String.valueOf(final_price));
                            }
                        } catch (JSONException e) {
                            Log.v("cmi", "cmi lah");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "error");
            }
        });
        // Add the request to the RequestQueue.
        RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}