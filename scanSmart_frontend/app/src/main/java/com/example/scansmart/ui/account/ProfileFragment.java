package com.example.scansmart.ui.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scansmart.MainActivity;
import com.example.scansmart.MainActivity2;
import com.example.scansmart.R;
import com.example.scansmart.ui.CustomToast;
import com.example.scansmart.ui.RestClient;
import com.example.scansmart.ui.User;
import com.example.scansmart.ui.UserResult;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    int a=0;

    public ProfileFragment(){
    }

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "hello");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("profile2","profile2");
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        Log.e("um","good");


        etEmail = getView().findViewById(R.id.et_edit_email);
        etName = getView().findViewById(R.id.et_edit_name);
        etPassword = getView().findViewById(R.id.et_edit_password);
        Button save = getView().findViewById(R.id.save);
/*
        Log.wtf("started", "started");
        String userName = ((MainActivity2)getActivity()).getName();
        String email = ((MainActivity2)getActivity()).getEmail();
        String password = ((MainActivity2)getActivity()).getPassword();
        Log.wtf("userName", userName);
        Log.e("?","?");

 */

        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userName = pref.getString("name","0");
        String password = pref.getString("password","0");
        String email = pref.getString("email","0");

        System.out.println(userName);
        etName.setText(userName);
        etEmail.setText(email);
        etPassword.setText(password);



        save.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
               // Snackbar.make(getView(), R.string.missing_value_string, Snackbar.LENGTH_LONG).show();
                Toast.makeText(getContext(), "missing values" , Toast.LENGTH_SHORT).show();
            } else {

                Call<UserResult> call = RestClient.getRestService(getContext()).update(etName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString());
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
                                Toast.makeText(getContext(), "Saved!" , Toast.LENGTH_SHORT).show();

                            } else if(userResult.getCode()!=200) {
                                Toast.makeText(getContext(), "Saved!" , Toast.LENGTH_SHORT).show();


                            }} else if(String.valueOf(userResult) ==  null || userResult==null){

                                Toast.makeText(getContext(), "Error occured" , Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            //new CustomToast().Show_Toast(getActivity(), root,
                            //"Please Enter Correct Data");
                            Toast.makeText(getContext(), "Error occured" , Toast.LENGTH_SHORT).show();
                            a=0;
                        }

                    }

                    @Override
                    public void onFailure(Call<UserResult> call, Throwable t) {
                        Log.d("Error==> ", t.getMessage());

                    }
                });



               // User user = repository.getInstance().geCurrentUser();


               /*user.setUsername(etName.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setPassword(etPassword.getText().toString());
                //repository.updateUser(user);

                */
            }

        });


    }
}