package com.example.scansmart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginFragment extends Fragment {
    private static final String KEY_EMPTY = "";
    private EditText etEmail;
    private EditText etPassword;
    private String email;
    private String password;
    private String login_url = "http://localhost:3000/users/authenticate";


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_login, container, false);



        etEmail = root.findViewById(R.id.et_loginemail);
        etPassword = root.findViewById(R.id.et_loginpassword);

        Button login = root.findViewById(R.id.btn_login);
        // Inflate the layout for this fragment


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (validateInputs()) {
                    loginUser();
                }

            }
        });
        return root;

    }

    private void loginUser() {
        AndroidNetworking.get("http://localhost:3000/users/authenticate")
                .addQueryParameter("email", email)
                .addQueryParameter("password", password)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Intent nextIntent = new Intent(getActivity(),MainActivity2.class);
                        startActivity(nextIntent);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


    }



    private boolean validateInputs() {
        if(KEY_EMPTY.equals(email)){
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }






}