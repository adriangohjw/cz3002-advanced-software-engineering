package com.example.scansmart.ui.discover;

import com.google.gson.annotations.SerializedName;

public  class ListItem {
    private String name;
    private String original_price;
    private String disc_price;
    @SerializedName("image")
    private int imageUrl;


    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOriginal_price() {
        return original_price;
    }
    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }
    public String getDisc_price() {
        return disc_price;
    }
    public void setDisc_price(String disc_price) {
        this.disc_price = disc_price;
    }
}