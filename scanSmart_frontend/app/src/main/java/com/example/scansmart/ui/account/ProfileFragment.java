package com.example.scansmart.ui.account;



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

        String userString;
        SharedPreferences sharedPreferences;
        sharedPreferences = getContext().getSharedPreferences("Preferences", 0);
        userString = sharedPreferences.getString("User", "");
        User user = gson.fromJson(userString, User.class);

        name.setText(user.getUsername());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        return root;
    }
}