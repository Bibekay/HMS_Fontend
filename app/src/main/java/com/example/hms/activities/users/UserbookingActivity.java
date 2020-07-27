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
import com.example.hms.adapter.UserbookingsAdapter;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Bookings;
import com.example.hms.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserbookingActivity extends AppCompatActivity {

    UserbookingActivity activity;
    UserbookingsAdapter userbookingsAdapter;
    private RecyclerView recyclerView;
    ImageView back;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userbooking);

        activity = this;
        recyclerView = findViewById(R.id.rv_booked_hotels);
        back = findViewById(R.id.iv_backpressed);

        showBookings();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserbookingActivity.super.onBackPressed();
            }
        });


    }
    private void showBookings(){

        HMS_API hms_api = url.getInstance().create(HMS_API.class);
        Call<List<Bookings>> orders = hms_api.getMyBookings(url.token);


        orders.enqueue(new Callback<List<Bookings>>() {
            @Override
            public void onResponse(Call<List<Bookings>> call, Response<List<Bookings>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UserbookingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                List<Bookings> bookingsList = response.body();
                activity.userbookingsAdapter = new UserbookingsAdapter(UserbookingActivity.this, bookingsList);
                recyclerView.setAdapter(userbookingsAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(UserbookingActivity.this, 1));

            }

            @Override
            public void onFailure(Call<List<Bookings>> call, Throwable t) {
                Toast.makeText(UserbookingActivity.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}