package com.example.scansmart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.fragment.app.Fragment;
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

import java.io.IOException;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LoginFragment extends Fragment  {
    private static final String KEY_EMPTY = "";
    private static View root;
    private EditText etEmail;
    private EditText etPassword;
    private String email;
    private String password;

    private String login_url = "http://localhost:3000/users/authenticate";
    Gson gson = new Gson();
    View progress;
    String userString;
    User user;




    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        AndroidNetworking.initialize(getActivity().getApplicationContext());

        root = inflater.inflate(R.layout.fragment_login, container, false);
        Button login = root.findViewById(R.id.btn_login);

        etEmail = root.findViewById(R.id.et_loginemail);
        etPassword = root.findViewById(R.id.et_loginpassword);
        /*
        SharedPreferences sharedPreferences;
        sharedPreferences = getContext().getSharedPreferences("Preferences", 0);
        userString = sharedPreferences.getString("User", "");
        user = gson.fromJson(userString, User.class);
        */

        // Inflate the layout for this fragment

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if (validateInputs()) {
                    user = new User(email, password);
                    loginUser(user);
                    Intent nextIntent = new Intent(getActivity(), MainActivity2.class);
                    startActivity(nextIntent);
                }
            }
        });
        return root;
    }


    private void loginUser() {
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
                        try {
                            JSONObject obj = new JSONObject(response);
                            int id = Integer.parseInt(obj.getString("id"));
                            Log.v("id", Integer.toString(id));
                            Bundle b = new Bundle();
                            b.putInt("userID", id);
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
                Log.v("error" , "error");
            }



  private void loginUser (User user) {

      Call<UserResult> call = RestClient.getRestService(getContext()).login(user);
      call.enqueue(new Callback<UserResult>() {
          @Override
          public void onResponse(Call<UserResult> call, Response<UserResult> response) {

              Log.d("Response :=>", response + "");
              if (response != null) {


                  UserResult userResult = response.body();
                  if (userResult.getCode() == 200) {
                      Log.v("ok","great");

                      //Toast.makeText(getContext(), userResult.getStatus(), Toast.LENGTH_LONG).show();
                      //startActivity(new Intent(getContext(), MainActivity2.class));
                      //getActivity().finish();
                  } else {
                      //new CustomToast().Show_Toast(getContext(), root,
                             // userResult.getStatus());
                  }

              } else {
                  new CustomToast().Show_Toast(getActivity(), root,
                          "Please Enter Correct Data");
              }


        });
        // Add the request to the RequestQueue.
        RequestSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }

          }

          @Override
          public void onFailure(Call<UserResult> call, Throwable t) {
              Log.d("Error==> ", t.getMessage());

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