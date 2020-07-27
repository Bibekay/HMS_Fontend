package com.example.hms.activities.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.adapter.UserviewhotelsAdapter;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Hotels;
import com.example.hms.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserviewhotelsActivity extends AppCompatActivity {

    UserviewhotelsActivity activity;
    UserviewhotelsAdapter userviewhotelsAdapter;
    private RecyclerView recyclerView;
    ImageView back;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewhotels);

        activity = this;
        recyclerView = findViewById(R.id.rv_recentlyAddedHotels);
        back = findViewById(R.id.iv_backpressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserviewhotelsActivity.super.onBackPressed();
            }
        });

        showHotels();


    }

    private void showHotels() {

        HMS_API hms_api = url.getInstance().create(HMS_API.class);
        Call<List<Hotels>> hotelsCall = hms_api.getHotels(url.token);


        hotelsCall.enqueue(new Callback<List<Hotels>>() {
            @Override
            public void onResponse(Call<List<Hotels>> call, Response<List<Hotels>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(UserviewhotelsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                List<Hotels> hotelsList = response.body();
                activity.userviewhotelsAdapter = new UserviewhotelsAdapter(UserviewhotelsActivity.this, hotelsList);
                recyclerView.setAdapter(userviewhotelsAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(UserviewhotelsActivity.this,1));

            }

            @Override
            public void onFailure(Call<List<Hotels>> call, Throwable t) {

            }
        });

    }
}