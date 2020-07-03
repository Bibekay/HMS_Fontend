package com.example.hms.url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class url {
    public static final String BASE_URL = "http://192.168.1.76:3001/";
    //public static final String BASE_URL = "http://10.0.2.2:3001/";

    //Dawa wifi
  //  public static final String BASE_URL = "http://192.168.1.14:3001/";

    public static String token = "Bearer ";
    public static String status = "Status";

    public static Retrofit getInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;
    }
}
