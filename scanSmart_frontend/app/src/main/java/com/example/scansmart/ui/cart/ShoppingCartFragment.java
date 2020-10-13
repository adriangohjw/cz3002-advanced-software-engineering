package com.example.scansmart.ui.cart;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scansmart.R;
import com.example.scansmart.ui.cart.CartAdapter;
import androidx.fragment.app.Fragment;

public class ShoppingCartFragment extends Fragment {

    public CartAdapter mAdapter;
    public static final int LOADER = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        Button clearthedata = root.findViewById(R.id.clear);

        clearthedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete 'users/:id/cart
            }
        });

        ListView listView = root.findViewById(R.id.list);
//        mAdapter = new CartAdapter(getActivity(), null);
//        listView.setAdapter(mAdapter);

        return root;
    }

}