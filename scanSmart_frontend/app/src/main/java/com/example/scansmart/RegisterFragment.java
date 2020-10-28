package com.example.scansmart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.graphics.Bitmap;



import com.example.scansmart.ui.RestClient;
import com.example.scansmart.ui.User;
import com.example.scansmart.ui.UserResult;
import com.example.scansmart.ui.account.ProfileFragment;
import com.google.gson.Gson;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
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
    Gson gson = new Gson();
    User user;
    int a=0;

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
                    user = new User(name,email,password);
                    registerUser(user);

                }

            }
        });
        return root;
    }


    private void registerUser(User userString) {

        Call<UserResult> call = RestClient.getRestService(getContext()).register(userString);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                Log.d("Response :=>", response + "");
                if (response != null ) {

                    UserResult userResult = response.body();
                    Log.d("user result is:", String.valueOf(userResult));


                    if (String.valueOf(userResult)!=null && userResult != null) {
                        if (userResult.getCode() == 201) {
                            Log.v("great", "yay");

                            //startActivity(new Intent(getContext(), MainActivity.class));
                            //getActivity().finish();
                            Toast.makeText(getContext(), "You've registered successfully" , Toast.LENGTH_SHORT).show();
                            a=1;

                        } else if(userResult.getCode() !=201) {
                            Log.v("w2","w2");
                            Toast.makeText(getContext(), "You've registered successfully" , Toast.LENGTH_SHORT).show();
                            a=1;

                            Fragment fragment = new LoginFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.registerr, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        } } else if(String.valueOf(userResult)==null || userResult==null){
                        Log.v("w3","w3");
                        Toast.makeText(getContext(), "Please enter correct details" , Toast.LENGTH_SHORT).show();
                        a=0;

                    }



                }

                    }

                else {
                    a=0;
                    Log.v("w4","w4");
                    Toast.makeText(getContext(), "Please enter correct details" , Toast.LENGTH_SHORT).show();

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