package com.example.scansmart.ui.account;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;



import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.scansmart.R;
import com.example.scansmart.ui.User;
import com.google.gson.Gson;


public class ProfileFragment extends Fragment {
    TextView name, email, password;

    Gson gson = new Gson();
    View root;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.profile_details, container, false);

        name = root.findViewById(R.id.et_edit_name);
        email = root.findViewById(R.id.et_edit_email);
        password = root.findViewById(R.id.et_edit_password);



        SharedPreferences preferences = getContext().getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        String name_ = preferences.getString("name", "No name defined");
        String email_ = preferences.getString("email", "No name defined");
        String password_ = preferences.getString("password", "No name defined");

        name.setText(name_);
        email.setText(email_);
        password.setText(password_);

        Log.v("namee", String.valueOf(name));
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
    }
}





