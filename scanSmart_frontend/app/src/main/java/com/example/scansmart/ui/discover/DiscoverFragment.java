package com.example.scansmart.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.scansmart.MainActivity;
import com.example.scansmart.R;
import com.example.scansmart.ui.cart.CartViewModel;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ArrayList userList = getListData();
        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        final ListView lv = (ListView) root.findViewById(R.id.user_list);
        lv.setAdapter(new CustomListAdapter(this.getContext(), userList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem prod = (ListItem) lv.getItemAtPosition(position);
                Toast.makeText(getContext(), "Selected :" + " " + prod.getName() + ", " + prod.getDisc_price(), Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
    private ArrayList getListData() {
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
    }


    }
