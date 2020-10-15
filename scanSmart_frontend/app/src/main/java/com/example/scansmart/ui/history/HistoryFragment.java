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

public class HistoryFragment extends Fragment {
    int userID;
    ArrayList<String> dateTime;
    ArrayList<String> store;
    ArrayList<Integer> item;
    ListView simpleList;
    TextView itemCount;
    int length;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_history, container, false);
        userID = ((MainActivity2) getActivity()).getUserID();
        //initialise lists
        dateTime = new ArrayList<String>();
        store = new ArrayList<String>();
        item = new ArrayList<Integer>();

        simpleList = (ListView) root.findViewById(R.id.list);
        itemCount = (TextView) root.findViewById(R.id.total_items);

        //get orders
        String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/%1$s/orders",
                userID);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Yay", "Yay");
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            //Log.v("response", response);
                            JSONArray ja_data = jsonObj.getJSONArray("orders");
                            length = ja_data.length();
                            for(int i=0; i<length; i++) {
                                JSONObject orderjsonObj = ja_data.getJSONObject(i);
                                //Log.v("object", String.valueOf(orderjsonObj));
                                JSONObject detailsJObject = orderjsonObj.getJSONObject("details");
                                //Log.wtf("details", String.valueOf(detailsJObject));
                                String dateTime_var  = detailsJObject.getString("created_at");
                                Log.wtf("created", dateTime_var);
                                dateTime.add(dateTime_var);
                                String store_var = detailsJObject.getString("store_name");
                                Log.wtf("store", store_var);
                                store.add(store_var);
                                int item_var = detailsJObject.getInt("items_purchased_count");
                                Log.wtf("count", String.valueOf(item_var));
                                item.add(item_var);
                            }
                            if (getActivity()!=null) {
                                //Log.wtf("dateTime", String.valueOf(dateTime));
                                HistoryList historyList = new HistoryList(getActivity().getApplicationContext(), dateTime, store, item);
                                simpleList.setAdapter(historyList);

                                itemCount.setText(String.valueOf(length));
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
}