package com.example.scansmart.ui;

import com.example.scansmart.ui.discover.ListItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestService {

    @POST("users/")
    Call<UserResult> register(@Body User user);

    @GET("users/authenticate")
    Call<UserResult> login(@Query("email") String email, @Query("password") String password);

    @GET("products/")
    Call<ProductResult> Products();



}
