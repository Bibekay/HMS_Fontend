package com.example.hms.api;

import com.example.hms.models.Users;
import com.example.hms.serverresponse.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HMS_API {
    //  for Users model //
    @POST("users/signup")
    Call<SignUpResponse> signup(@Body Users users);
}
