package com.example.scansmart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.example.scansmart.ui.CustomToast;
import com.example.scansmart.ui.RestClient;
import com.example.scansmart.ui.User;
import com.example.scansmart.ui.UserResult;
import com.google.gson.Gson;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {
    private static final String KEY_EMPTY = "";
    private static View root;
    private EditText etEmail;
    private EditText etPassword;
    private String email;
    private String password;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        container.removeAllViews();
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        Button login = root.findViewById(R.id.btn_login);
        etEmail = root.findViewById(R.id.et_loginemail);
        etPassword = root.findViewById(R.id.et_loginpassword);

        // Inflate the layout for this fragment
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if (validateInputs()) {
                    loginUser();
                }

            }
        });
        return root;
    }


    private void loginUser() {
        String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/authenticate/?email=%1$s&password=%2$s",
                email,
                password);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.wtf("Yay", "Yay");
                        try {
                            JSONObject obj = new JSONObject(response);
                            int id = Integer.parseInt(obj.getString("id"));

                          String email_ = obj.getString("email");
                           String stripe_customer_identifier = obj.getString("stripe_customer_identifier");
                          String name =  obj.getString("name");
                          String password = obj.getString("password");



                            Bundle b = new Bundle();
                            b.putInt("userID", id);
                           b.putString("email",email_);
                           b.putString("password",password);
                          b.putString("name",name);
                          b.putString("stripe_customer_identifier",stripe_customer_identifier);


                            Intent i = new Intent(getActivity(), MainActivity2.class);
                            i.putExtras(b);
                            startActivity(i);
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
        RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(email)) {
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

}