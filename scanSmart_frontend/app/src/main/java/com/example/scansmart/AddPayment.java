
package com.example.scansmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Token;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import java.util.HashMap;
import java.util.Map;

public class AddPayment  extends AppCompatActivity {
    CardMultilineWidget cardMultilineWidget;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String tokenid;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        final Bundle extras = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Payment");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        cardMultilineWidget = findViewById(R.id.card_input_widget);
        save =  findViewById(R.id.save_payment);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCard();
            }
        });

    }

    private void saveCard() {

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

    private void CreateToken( Card card) {
        Stripe stripe = new Stripe(getApplicationContext(), "pk_test_51HbjJNFV0pkQC9JUBmdVKUqBfZPxOnUiLL1l2CP1H7Zu0MqvE3luqz8BxmliUiO7yhtmRaQ9scBx9j06TgrgXNVR00e0KZNUxm");
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
                        intent.putExtra("cardtype",token.getCard().getBrand());


                       String createcardURL = "https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app/users/" + "75"+"/cards";
                       RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                       StringRequest stringRequest =  new StringRequest(Request.Method.POST, createcardURL, new Response.Listener<String>() {

                           @Override
                           public void onResponse(String response) {
                               System.out.println("SUCCESS");

                           }
                       }, new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {

                               System.out.println("FAILURE");
                               error.getLocalizedMessage();


                           }
                       }) {
                           @Override
                           protected Map<String, String> getParams() {
                               Map<String, String> params = new HashMap<>();
                               params.put("card_token_id", token.getId());
                               Log.d("yayyy","yayyyy");
                               return params;
                           }
                       };

                        queue.add(stringRequest);
                        setResult(0077,intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}


