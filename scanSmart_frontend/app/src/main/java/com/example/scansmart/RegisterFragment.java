package com.example.scansmart;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
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
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.scansmart.ui.User;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        View root = inflater.inflate(R.layout.fragment_register, container, false);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .build();


        AndroidNetworking.initialize(getActivity().getApplicationContext(),client);

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

        AndroidNetworking.post("http://localhost:3000/users/")
                .addQueryParameter("name", username)
                .addQueryParameter("email", email)
                .addQueryParameter("password", password)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
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

