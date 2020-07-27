package com.example.hms.api;

import com.example.hms.models.Hotels;
import com.example.hms.models.Users;
import com.example.hms.serverresponse.ImageResponse;
import com.example.hms.serverresponse.SignUpResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface HMS_API {
    //  for Users model //
    @POST("users/signup")
    Call<SignUpResponse> signup(@Body Users users);

    @FormUrlEncoded
    @POST("users/login")
    Call<SignUpResponse> checkUser(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("upload/users")
    Call<ImageResponse> uploadImage(@Header("Authorization") String token, @Part MultipartBody.Part file);


    @GET("users/me")
    Call<Users> getUserDetails(@Header("Authorization") String token);

    @PUT("users/me")
    Call<Users> updateUser(@Header("Authorization") String token, @Body Users users);



    //for model hotels//

    @Multipart
    @POST("upload/hotels")
    Call<ImageResponse> uploadHotelImage(@Header("Authorization") String token, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("hotels/hotel")
    Call<Hotels> addHotels(@Header("Authorization") String token,
                             @Field("hotel_name") String hotel_name,
                             @Field("description") String description,
                             @Field("price") String price,
                             @Field("hotel_image") String hotel_image);


    @GET("hotels/hotel")
    Call<List<Hotels>> getHotels(@Header("Authorization") String token);


}
