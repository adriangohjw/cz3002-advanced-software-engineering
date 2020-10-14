package com.example.scansmart.ui;
import org.json.JSONObject;

import java.util.Date;

public class User {

    String name;
    String email;
    String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}