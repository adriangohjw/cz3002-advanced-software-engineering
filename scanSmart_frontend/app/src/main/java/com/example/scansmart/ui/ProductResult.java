package com.example.scansmart.ui;

import com.example.scansmart.ui.discover.ListItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResult {
    @SerializedName("products")
    List<ListItem> productList;
    @SerializedName("code")
    int code;
    @SerializedName("status")
    String status;


    public ProductResult(List<ListItem> productList, int code, String status) {
        this.productList = productList;
        this.code = code;
        this.status = status;
    }

    public List<ListItem> getProductList() {
        return productList;
    }

    public void setProductList(List<ListItem> productList) {
        this.productList = productList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}