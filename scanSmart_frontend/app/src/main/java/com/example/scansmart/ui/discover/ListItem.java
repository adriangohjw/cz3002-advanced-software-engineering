package com.example.scansmart.ui.discover;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public  class ListItem {
    private String name ;
    private String price;
    private String discounted_price;
    @SerializedName("image")
    private int imageUrl;

    public int getImageUrl() {
        return imageUrl; }

   public void setImageUrl(int imageUrl) {
       this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDiscounted_price() {
        return discounted_price;
    }
    public void setDiscounted_price(String discounted_price) {
        this.discounted_price = discounted_price;
    }
}