package com.example.scansmart.ui;
import org.json.JSONObject;

import java.util.Date;

public class User {

    String name;
    String email;
    String password;
    String stripe_customer_identifier;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public String getUsername1(String email, String password){
        return name;

    }


    public String getStripe_customer_identifier() {return stripe_customer_identifier;}

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStripe_customer_identifiert(Integer id){this.stripe_customer_identifier = stripe_customer_identifier;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}