package com.example.hms.api;

import com.example.hms.models.Users;
import com.example.hms.serverresponse.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HMS_API {
    //  for Users model //
    @POST("users/signup")
    Call<SignUpResponse> signup(@Body Users users);

    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponse> checkUser(@Field("username") String username, @Field("password") String password);

    @GET("users/me")
    Call<Users> getUserDetails(@Header("Authorization") String token);
}
