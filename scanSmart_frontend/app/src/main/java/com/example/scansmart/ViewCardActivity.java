
package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewCardActivity extends AppCompatActivity {

    CardMultilineWidget cardMultilineWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        cardMultilineWidget = findViewById(R.id.card_input_widget);
        retrieveCard();

    }
    private void retrieveCard() {
        //call get users/:id/cards
        //logic: if cards = [], then get and validate stuff -> same as that
        //otherwise, add to text  filed just like before




        int id_;
        SharedPreferences prefs = getSharedPreferences("MyPref",MODE_PRIVATE);
        id_ = prefs.getInt("userID",0);
        Log.e("lol","HOW");
        System.out.println(id_);



        String retrieveURL = "https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/" + id_+ "/cards";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, retrieveURL, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("SUCCESS");
                try {
                    int month = 0;
                    int year =0;
                    String cardNumber;
                    cardNumber = "";
                    String country;

                    //month = null;
                   // year = null;
                    Log.e("card","cardd");

                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray ja_data = jsonObj.getJSONArray("cards");
                    int length = ja_data.length();
                    for (int i = 0; i<length;i++){
                        JSONObject orderjsonObj = ja_data.getJSONObject(i);
                         month = orderjsonObj.getInt("exp_month");
                         year = orderjsonObj.getInt("exp_year");
                         cardNumber = orderjsonObj.getString("last4");
                         country =  orderjsonObj.getString("country");



                    }


                    if(cardNumber.equals(null) && month == 0  && year ==0)
                    {
                        //create new card using the value
                        Card card =  cardMultilineWidget.getCard();
                        if(card == null){
                            Toast.makeText(getApplicationContext(),"Invalid card",Toast.LENGTH_SHORT).show();
                        }else {
                            if (!card.validateCard()) {
                                // Do not continue token creation.
                                Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
                            } else {
                                CreateToken(card);
                            }
                        }

                    }
                    else if(!(cardNumber.equals(null)) &&  month!=0 &&  year!=0){

                        //SHOW CARD DETAILS
                        cardMultilineWidget.setCardNumber(cardNumber);
                        cardMultilineWidget.setCvcCode("***");
                        cardMultilineWidget.setExpiryDate(month,year);


                    }

                } catch (JSONException e) {
                    Log.v("cmi lol", "cmi lah");
                }


            }

            private void CreateToken( Card card) {
                //post method

                Stripe stripe = new Stripe(getApplicationContext(),"pk_test_51HbjJNFV0pkQC9JUBmdVKUqBfZPxOnUiLL1l2CP1H7Zu0MqvE3luqz8BxmliUiO7yhtmRaQ9scBx9j06TgrgXNVR00e0KZNUxm");


                stripe.createCardToken(
                        card,
                        new ApiResultCallback<Token>() {
                            public void onSuccess(Token token) {


                                // Send token to your server
                                Log.e("Stripe Token", token.getId());
                                Intent intent = new Intent();
                                intent.putExtra("card",token.getCard().getLast4());
                                intent.putExtra("stripe_token",token.getId());
                                //intent.putExtra("donationInfo", extras);
                                //intent.putExtra("cardtype",token.getCard().getBrand());
                                //setResult(0077,intent);
                                //return token.getId();
                                //finish();
                                //TOAST MESSAGE SAYING CARD CREATED,

                            }
                            public void onError(Exception error) {

                                // Show localized error message
                                Toast.makeText(getApplicationContext(),
                                        error.getLocalizedMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
            }




        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println("FAILURE");
                        error.getLocalizedMessage();

                    }
                });
        queue.add(stringRequest);
    }



}

