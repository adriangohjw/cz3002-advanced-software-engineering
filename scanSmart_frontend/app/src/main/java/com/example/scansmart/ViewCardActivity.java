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

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String stripe_customer_identifier = sharedPref.getString("stripe_customer_identifier","0");


        String retrieveURL = "https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/:" + stripe_customer_identifier + "/cards";// for server validation of payment
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, retrieveURL, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("SUCCESS");

                if(response.equals("cards =[]")) {
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

            }

            private void CreateToken( Card card) {
                //post method

                Stripe stripe = new Stripe(getApplicationContext(),"pk_test_51HYA96CmbisokLBamubHNmnTR9FwaHgWxWlUPpRfR32mV9dgkPyCzmWNvZMQMRufsaNh1xRBRRJXpSNDY4hhJKP600jmttnVHP");


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

            //elseif error, show
            //print(response) first

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