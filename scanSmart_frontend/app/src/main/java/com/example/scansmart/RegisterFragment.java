package com.example.scansmart;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    private static final String KEY_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String register_url = "http://localhost:3000/users/";


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // OkHttpClient client = new OkHttpClient.Builder()
       //         .retryOnConnectionFailure(false)
       //         .build();
       // AndroidNetworking.initialize(getActivity().getApplicationContext(),client);

        View root = inflater.inflate(R.layout.fragment_register, container, false);





        etUsername = root.findViewById(R.id.et_name);
        etPassword = root.findViewById(R.id.et_password);
        etConfirmPassword = root.findViewById(R.id.et_repassword);
        etEmail = root.findViewById(R.id.et_email);

        Button register = root.findViewById(R.id.btn_register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                    Log.v("fml",username);
                    Fragment fragment = new LoginFragment();
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.viewPager,fragment).commit();
                    Log.v("FML AGAIN", username);

                }

            }
        });
        return root;
    }


        private void registerUser() {
        Log.v("lol", username);

/*
        AndroidNetworking.post("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/")
                .addBodyParameter("name", username)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                //.setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v("success",username);

                    }

                    @Override
                    public void onError(ANError error) {

                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d("T1", "onError errorCode : " + error.getErrorCode());
                            Log.d("T2", "onError errorBody : " + error.getErrorBody());
                            Log.d("T3", "onError errorDetail : " + error.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                            //ApiError apiError = error.getErrorAsObject(ApiError.class);
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("t4", "onError errorDetail : " + error.getErrorDetail());
                        }
                        // handle error
                    }
                });
*/

            String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/?name=%1$s&password=%2$s&email=%3$s",
                    username,
                    password,
                    email);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.v("Yay", "Yay");
                            Intent i = new Intent(getActivity(), MainActivity2.class);
                            startActivity(i);
                            ((Activity) getActivity()).overridePendingTransition(0, 0);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("error" , "error");
                }


            });
            // Add the request to the RequestQueue.
            RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
                // form parameters




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



        // Inflate the layout for this fragment



    }

