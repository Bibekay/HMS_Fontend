package com.example.hms.api;



import com.example.hms.models.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


public interface Admin_api {

    //user detail

    @GET("users/userdetails")
    Call<List<Users>> getCustomer(@Header("Authorization") String token);

    @GET("users/{username}")
    Call<List<Users>> getUsername(@Header("username") String question);




}
