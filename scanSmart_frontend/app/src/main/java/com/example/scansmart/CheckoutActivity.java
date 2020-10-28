package com.example.scansmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.model.Token;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    BigDecimal amt;
    CardView addPayment, creditcard;
    TextView creditCardText;
    EditText amount;
    String amount_;
    int REQUEST_CODE = 0077;
    Button checkOut;
    public String stripe_token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Token token;

        addPayment = (CardView) findViewById(R.id.addPayment);
        creditCardText = (TextView) findViewById(R.id.credit_card_text);
        //amount = (EditText) findViewById(R.id.input);
        creditcard = (CardView) findViewById(R.id.credit_card);
        checkOut = (Button) findViewById(R.id.checkout);




        //final Bundle extras = getIntent().getExtras();
        // final Bundle donationInfo = (Bundle) extras.get("donationInfo");
        //amt = (BigDecimal) donationInfo.get("amount");
        //Utility.amountSent = amt.floatValue();

        // amount.setText( "$"+ Utility.amountSent + "SGD");


        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPayment.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked","clickedd");


                String chargeURL = "https://cz-3002-scansmart-api-7ndhk.ondigitalocean.app" + "/charges";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest =  new StringRequest(Request.Method.POST, chargeURL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println("SUCCESS");
                        Toast.makeText(getApplicationContext(), "Payment successful!" , Toast.LENGTH_SHORT).show();
                        if(Utility.alertSummoned == false){
                            Utility.flag = "moneySent";
                            //alert();
                        }


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                System.out.println("FAILURE");
                                error.getLocalizedMessage();
                                if(Utility.alertSummoned == false){
                                    Utility.flag = "failure";
                                    //alert();
                                }


                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        String customer_id = intent.getStringExtra("customer_id");

                        String customer_id = getIntent().getStringExtra("Stripe_customer_identifier");



                        //Integer intAmount = Integer.valueOf(amount_);
                        Integer intAmount = 1000;

                        params.put("amount", intAmount.toString());
                        //params.put("email", donationInfo.get("email").toString());
                        params.put("currency", "SGD");
                        params.put("customer_id",customer_id );
                        //params.put("source", extras.get("stripe_token").toString());
                        //params.put("source", "tok_amex");
                        //params.put("description", "*");// enter a description of transaction

                        Log.d("yayyy","yayyyy");
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == resultCode){

            creditcard.setVisibility(View.VISIBLE);
            stripe_token = data.getStringExtra("stripe_token");

            if(stripe_token.length()>1) {
                checkOut.setVisibility(View.VISIBLE);





            }

            creditCardText.setText(data.getStringExtra("cardtype")+" card ending with "+data.getStringExtra("card"));

        }

    }
}