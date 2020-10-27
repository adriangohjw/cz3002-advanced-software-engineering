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
    User user = new User();
    int a =1;



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    saveUser();
                    loginUser(email,password);

                    //Intent nextIntent = new Intent(getActivity(), MainActivity2.class);
                    //startActivity(nextIntent);

                }
            }
        });
        return root;
    }

    private void saveUser() {
        SharedPreferences preferences = getContext().getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password);
        System.out.println("name is");
        System.out.println(preferences.getString("name","0"));

        //editor.putString("stripe_customer_identifier", String.valueOf(user.getStripe_customer_identifier()));
        editor.apply();

    }


    private void loginUser (String email,String password) {

        Call<UserResult> call = RestClient.getRestService(getContext()).login(email,password);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {

                Log.d("Response :=>", response + "");
                if (response != null) {


                    UserResult userResult = response.body();
                    Log.v("userresult", String.valueOf(userResult));
                    if (String.valueOf(userResult)!=null  && userResult!=null)
                    { if(userResult.getCode() == 200) {
                        Log.v("ok","great");
                       a=1;

                        Intent nextIntent = new Intent(getActivity(), MainActivity2.class);
                       startActivity(nextIntent);

                        //Toast.makeText(getContext(), userResult.getStatus(), Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(getContext(), MainActivity2.class));
                        //getActivity().finish();
                    } else if(userResult.getCode()!=200) {

                        a=0;

                        Intent nextIntent = new Intent(getActivity(), MainActivity2.class);
                        startActivity(nextIntent);

                        //new CustomToast().Show_Toast(getContext(), root,
                        // userResult.getStatus());
                    }} else if(String.valueOf(userResult) ==  null || userResult==null){
                        Log.v("lool","help");
                        Toast.makeText(getContext(), "Please enter correct details" , Toast.LENGTH_SHORT).show();
                        a=0;

                    }

                } else {
                    //new CustomToast().Show_Toast(getActivity(), root,
                            //"Please Enter Correct Data");
                    Toast.makeText(getContext(), "Please enter correct details" , Toast.LENGTH_SHORT).show();
                    a=0;
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