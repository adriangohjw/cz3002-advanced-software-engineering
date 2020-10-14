package com.example.scansmart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;


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


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * A simple {@link Fragment} subclass.
 * <p>
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    private static final String KEY_EMPTY = "";
    private static View root;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private String name;
    private String password;
    private String confirmPassword;
    private String email;

    private String register_url = "http://localhost:3000/users/";
    Gson gson = new Gson();
    User user;



    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         root = inflater.inflate(R.layout.fragment_register, container, false);





        etUsername = root.findViewById(R.id.et_name);
        etPassword = root.findViewById(R.id.et_password);
        etConfirmPassword = root.findViewById(R.id.et_repassword);
        etEmail = root.findViewById(R.id.et_email);

        Button register = root.findViewById(R.id.btn_register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                name = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                if (validateInputs()) {

                    registerUser();

                    user = new User(name,email,password);
                    registerUser(user);

                    Log.v("fml",name);
                    Intent nextIntent = new Intent(getActivity(), MainActivity2.class);
                    startActivity(nextIntent);


                    Log.v("FML AGAIN", name);


                }
            }
        });
        return root;
    }


    private void registerUser() {
        String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/?name=%1$s&password=%2$s&email=%3$s",
                username,
                password,
                email);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Yay", "Yay");
                        Intent i = new Intent(getActivity(), MainActivity2.class);
                        startActivity(i);
                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error" , "error");



        private void registerUser(User userString) {

            Call<UserResult> call = RestClient.getRestService(getContext()).register(userString);
            call.enqueue(new Callback<UserResult>() {
                @Override
                public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                    Log.d("Response :=>", response + "");
                    if (response != null ) {

                        UserResult userResult = response.body();
                        Log.d("user result is:", String.valueOf(userResult));


                        if (userResult != null) {
                            if (userResult.getCode() == 201) {
                                Log.v("great", "yay");

                                //startActivity(new Intent(getContext(), MainActivity.class));
                                //getActivity().finish();
                            } else {
                                Log.isLoggable("yea", userResult.getCode());
                                new CustomToast().Show_Toast(getActivity(), root,
                                        userResult.getStatus());
                                //   "Errorr");

                            }


                        } }
                        else {
                            Log.v("wro2", "enter cor");
                            new CustomToast().Show_Toast(getActivity(), root,
                                    "Please Enter Correct Data");
                        }



                }

                @Override
                public void onFailure(Call<UserResult> call, Throwable t) {
                    Log.d("Error==> ", t.getMessage());

                }
            });
                         }
                // form parameters




        private boolean validateInputs() {
            if (KEY_EMPTY.equals(email)) {
                etEmail.setError("email cannot be empty");
                etEmail.requestFocus();
                return false;

            }
            if (KEY_EMPTY.equals(name)) {
                etUsername.setError("Username cannot be empty");
                etUsername.requestFocus();
                return false;
            }
            if (KEY_EMPTY.equals(password)) {
                etPassword.setError("Password cannot be empty");
                etPassword.requestFocus();
                return false;

            }
        });
        // Add the request to the RequestQueue.
        RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(email)) {
            etEmail.setError("email cannot be empty");
            etEmail.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }


}


