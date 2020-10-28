package com.example.scansmart.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.scansmart.MainActivity2;
import com.example.scansmart.R;
import com.example.scansmart.ViewCardActivity;
import com.example.scansmart.profiledetailsActivity;
import com.example.scansmart.ui.cart.ShoppingCartFragment;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        root = inflater.inflate(R.layout.fragment_account, container, false);
        Log.v("hi", "hi");
        return root;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        click();
    }

    private void click() {
        Button prof_btn = getView().findViewById(R.id.btn_profile_details);
        prof_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextIntent = new Intent(getActivity(), profiledetailsActivity.class);
                startActivity(nextIntent);

            }
        });

        Button card_btn = getView().findViewById(R.id.btn_card_details);
        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getActivity(), ViewCardActivity.class);
                startActivity(nextIntent);

            }
        });








    }


}