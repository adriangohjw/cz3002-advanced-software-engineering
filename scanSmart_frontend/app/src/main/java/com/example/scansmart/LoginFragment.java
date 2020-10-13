package com.example.scansmart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import java.io.IOException;


public class LoginFragment extends Fragment  {
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

        //OkHttpClient client = new OkHttpClient.Builder()
         //       .retryOnConnectionFailure(false)
       //         .build();


       // AndroidNetworking.initialize(getActivity().getApplicationContext(),client);



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
                    Intent nextIntent = new Intent(getActivity(), MainActivity2.class);
                    startActivity(nextIntent);
                }

            }


        });
        return root;

    }




  private void loginUser () {
/*
        AndroidNetworking.get("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/authenticate")
                .addQueryParameter("email", email)
                .addQueryParameter("password", password)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v("success",email);


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
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
      String url = String.format("https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/authenticate?email=%1$s&password=%2$s",
              email,
              password);
      // Request a string response from the provided URL.
      StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
      com.example.scansmart.RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);




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