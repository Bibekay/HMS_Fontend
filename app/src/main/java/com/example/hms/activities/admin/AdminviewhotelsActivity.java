package com.example.hms.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.activities.users.UserviewhotelsActivity;
import com.example.hms.adapter.AdminviewhotelsAdapter;
import com.example.hms.adapter.UserviewhotelsAdapter;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Hotels;
import com.example.hms.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminviewhotelsActivity extends AppCompatActivity {

    AdminviewhotelsActivity activity;
    AdminviewhotelsAdapter adminviewhotelsAdapter;
    private RecyclerView recyclerView;
    ImageView back;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminviewhotels);
        activity = this;
        recyclerView = findViewById(R.id.rv_view_hotels);
        back = findViewById(R.id.iv_backpressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminviewhotelsActivity.super.onBackPressed();
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
                    Toast.makeText(AdminviewhotelsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                List<Hotels> hotelsList = response.body();
                activity.adminviewhotelsAdapter = new AdminviewhotelsAdapter(AdminviewhotelsActivity.this, hotelsList);
                recyclerView.setAdapter(adminviewhotelsAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(AdminviewhotelsActivity.this,1));

            }

            @Override
            public void onFailure(Call<List<Hotels>> call, Throwable t) {

            }
        });

    }

}