package com.example.hms.bll;

import com.example.hms.api.HMS_API;
import com.example.hms.serverresponse.SignUpResponse;
import com.example.hms.url.url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBLL {
    boolean isSuccess = false;


    public boolean checkUser(String username, String password) {
        HMS_API hms_api = url.getInstance().create(HMS_API.class);
        Call<SignUpResponse> UsersCall = hms_api.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = UsersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Login Successfull")) {

                url.token += loginResponse.body().getToken();
                //url.admin += loginResponse.body().getAdmin();
                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean checkadmin(String username, String password) {

        HMS_API hms_api = url.getInstance().create(HMS_API.class);
        Call<SignUpResponse> UsersCall = hms_api.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = UsersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("isadmin")) {

                url.token += loginResponse.body().getToken();
                //url.admin += loginResponse.body().getAdmin();
                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }





}
