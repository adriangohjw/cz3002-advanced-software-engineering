package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scansmart.ui.RestClient;
import com.example.scansmart.ui.UserResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profiledetailsActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiledetails);

        setUp();
    }

    private void setUp() {


        etEmail = findViewById(R.id.et_edit_email);
        etName = findViewById(R.id.et_edit_name);
        etPassword = findViewById(R.id.et_edit_password);
        Button save = findViewById(R.id.save);
        SharedPreferences pref = getSharedPreferences("MyPref",MODE_PRIVATE);

        etEmail.setText(pref.getString("email","0"));
        etName.setText(pref.getString("name","0"));
        etPassword.setText(pref.getString("password","0"));


        save.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                // Snackbar.make(getView(), R.string.missing_value_string, Snackbar.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "missing values" , Toast.LENGTH_SHORT).show();
            } else {

                Call<UserResult> call = RestClient.getRestService(getApplicationContext()).update(etName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString());
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
                                Toast.makeText(getApplicationContext(), "Saved!" , Toast.LENGTH_SHORT).show();

                            } else if(userResult.getCode()!=200) {
                                Toast.makeText(getApplicationContext(), "Saved!" , Toast.LENGTH_SHORT).show();


                            }} else if(String.valueOf(userResult) ==  null || userResult==null){

                                Toast.makeText(getApplicationContext(), "Error occured" , Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            //new CustomToast().Show_Toast(getActivity(), root,
                            //"Please Enter Correct Data");
                            Toast.makeText(getApplicationContext(), "Error occured" , Toast.LENGTH_SHORT).show();

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