package com.example.scansmart.ui.discover;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.scansmart.R;

public class DiscoverFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        return root;
    }
}

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scansmart.R;
import com.example.scansmart.ui.ProductResult;
import com.example.scansmart.ui.RestClient;
import com.example.scansmart.ui.cart.CartViewModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiscoverFragment extends Fragment {


    List<ListItem> productList = new ArrayList<>();
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            root = inflater.inflate(R.layout.fragment_discover, container, false);
            getListData();
            return root;

    }

    private void getListData() {
       Log.v("huh","huhh");
        Call<ProductResult> call = RestClient.getRestService(getContext()).Products();
        call.enqueue(new Callback<ProductResult>() {

            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    ProductResult productResult = response.body();

                    //if (productResult.getCode() == 200)
                    {    productList = productResult.getProductList();
                        ArrayList<ListItem> results = new ArrayList<>();

                        Log.d("code","yea");

                        for(int i =0;i<productList.size();i++)
                        {ListItem prod = new ListItem();
                        prod.setName(productList.get(i).getName());
                        prod.setPrice(productList.get(i).getPrice());
                        prod.setDiscounted_price(productList.get(i).getDiscounted_price());
                            results.add(prod);
                        }
                        setUpRecyclerView(results);
                }
            }
        }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                Log.d("Error", t.getMessage());


            }


            });


    }


    private void  setUpRecyclerView(ArrayList prodlist) {

            final ListView lv = (ListView) root.findViewById(R.id.prod_list);
            lv.setAdapter(new CustomListAdapter(this.getContext(), prodlist));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    ListItem prod = (ListItem) lv.getItemAtPosition(position);
                    Toast.makeText(getContext(), "Selected :" + " " + prod.getName() + ", " + prod.getPrice(), Toast.LENGTH_SHORT).show();
                }
            });

    }
}



                //mine
/*
        ArrayList<ListItem> results = new ArrayList<>();
        ListItem prod1 = new ListItem();
        prod1.setImageUrl(R.drawable.icon1);
        prod1.setName("Alarm clock");
        prod1.setDisc_price("$5.99");
        prod1.setOriginal_price("$10.99");
        results.add(prod1);
        ListItem prod2 = new ListItem();
        prod2.setImageUrl(R.drawable.icon2);
        prod2.setName("Weird Avocado");
        prod2.setDisc_price("$4.99");
        prod2.setOriginal_price("$5.99");
        results.add(prod2);
        ListItem prod3 = new ListItem();
        prod3.setImageUrl(R.drawable.icon3);
        prod3.setName("Cereal");
        prod3.setDisc_price("$7.99");
        prod3.setOriginal_price("$12.99");
        results.add(prod3);
        ListItem prod4 = new ListItem();
        prod4.setImageUrl(R.drawable.icon4);
        prod4.setName("Plant");
        prod4.setDisc_price("$10.99");
        prod4.setOriginal_price("$11.99");
        results.add(prod4);
        ListItem prod5 = new ListItem();
        prod5.setImageUrl(R.drawable.icon5);
        prod5.setName("Lipstick");
        prod5.setDisc_price("$7.99");
        prod5.setOriginal_price("$10.99");
        results.add(prod5);
        return results;

 */




