package com.example.scansmart;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity2 extends AppCompatActivity {
    int userID;
    String email;
    String name;
    String password;
    String stripe_customer_identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try{
            Bundle b = getIntent().getExtras();
            userID = b.getInt("userID");
            email = b.getString("email");
            password = b.getString("password");
            name = b.getString("name");
            stripe_customer_identifier = b.getString("stripe_customer_identifier");


        } catch(Exception ex){
            userID = 0;
            email = "";
            password ="";
            name ="";
            stripe_customer_identifier ="";



        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_discover, R.id.navigation_cart, R.id.navigation_history, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public int getUserID() {
        return userID;
    }
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getName(){return name;}
    public String getStripe_customer_identifier(){return stripe_customer_identifier;}


}