package com.example.hms.bll;

import com.example.hms.api.HMS_API;
import com.example.hms.models.Users;
import com.example.hms.serverresponse.SignUpResponse;
import com.example.hms.url.url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SignupBLL {

    boolean isSuccess = false;

    public boolean signup(String fullname, String username, String contact, String email, String password) {
        Users users = new Users (fullname, username, contact, email, password);

        HMS_API hms_api = url.getInstance().create(HMS_API.class);
        Call<SignUpResponse> UsersCall = hms_api.signup(users);

        try {
            Response<SignUpResponse> signupResponse = UsersCall.execute();
            if (signupResponse.isSuccessful() &&
                    signupResponse.body().getStatus().equals("Signup Successful")) {

//                url.token += signupResponse.body().getToken();
//                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
