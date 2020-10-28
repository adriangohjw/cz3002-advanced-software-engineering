package com.example.scansmart.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scansmart.MainActivity2;
import com.example.scansmart.R;
import com.example.scansmart.RequestSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndividualHistoryFragment extends Fragment {
    ArrayList<String> productName;
    ArrayList<Integer> price;
    ArrayList<Integer> quantity;
    ListView indList;
    TextView dateTime;
    TextView storeName;
    TextView items;
    TextView final_price;
    int length;
    String historyId;
    int final_quantity;
    String store_name;
    String created_at;
    Integer total_discounted_amount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();
        View root = inflater.inflate(R.layout.fragment_history_individual, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            historyId = bundle.getString("historyId", "0");
            Log.wtf("recevied historyId", historyId);
        }

        //initialise lists
        productName = new ArrayList<String>();
        price= new ArrayList<Integer>();
        quantity = new ArrayList<Integer>();

        indList = (ListView) root.findViewById(R.id.list_ind);
        dateTime = (TextView) root.findViewById(R.id.dateTime_ind);
        storeName = (TextView) root.findViewById(R.id.store_ind);
        items = (TextView) root.findViewById(R.id.items_ind);
        final_price = (TextView) root.findViewById(R.id.price_count_ind);

        //get orders
        String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/orders/?order_id=%1$s",
                historyId);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Yay", "Yay");
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            //Log.v("response", response);
                            store_name  = jsonObj.getString("store_name");
                            created_at  = jsonObj.getString("created_at");
                            total_discounted_amount  = jsonObj.getInt("total_discounted_amount");
                            JSONArray ja_data = jsonObj.getJSONArray("products");
                            length = ja_data.length();
                            final_quantity = 0;
                            for(int i=0; i<length; i++) {
                                JSONObject orderjsonObj = ja_data.getJSONObject(i);
                                String productName_var  = orderjsonObj.getString("name");
                                productName.add(productName_var);
                                Integer price_var = orderjsonObj.getInt("total_discounted_price");
                                price.add(price_var);
                                int quantity_var = orderjsonObj.getInt("quantity");
                                quantity.add(quantity_var);
                                final_quantity = final_quantity + quantity_var;
                            }
                            if (getActivity()!=null) {
                                setList();

                                dateTime.setText(created_at);
                                storeName.setText(store_name);
                                items.setText(String.valueOf(final_quantity));
                                final_price.setText(String.valueOf(total_discounted_amount));
                            }
                        } catch (JSONException e) {
                            Log.v("cmi", "cmi lah");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error" , "error");
            }
        });
        // Add the request to the RequestQueue.
        RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        return root;
    }

    public void setList(){
        IndividualHistoryList individualHistoryList = new IndividualHistoryList(getActivity().getApplicationContext(), productName, price, quantity);
        indList.setAdapter(individualHistoryList);
    }
}