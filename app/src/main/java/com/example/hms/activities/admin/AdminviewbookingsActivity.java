package com.example.hms.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.activities.users.UserbookingActivity;
import com.example.hms.adapter.AdminviewbookingsAdapter;
import com.example.hms.adapter.UserbookingsAdapter;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Bookings;
import com.example.hms.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminviewbookingsActivity extends AppCompatActivity {

    AdminviewbookingsActivity activity;
    AdminviewbookingsAdapter adminviewbookingsAdapter;
    private RecyclerView recyclerView;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminviewbookings);


        activity = this;
        recyclerView = findViewById(R.id.rv_view_bookings);
        back = findViewById(R.id.iv_backpressed);

        showBookings();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminviewbookingsActivity.super.onBackPressed();
            }
        });

    }

    private void showBookings(){

        HMS_API hms_api = url.getInstance().create(HMS_API.class);
        Call<List<Bookings>> orders = hms_api.getAllBookings(url.token);


        orders.enqueue(new Callback<List<Bookings>>() {
            @Override
            public void onResponse(Call<List<Bookings>> call, Response<List<Bookings>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminviewbookingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                List<Bookings> bookingsList = response.body();
                activity.adminviewbookingsAdapter = new AdminviewbookingsAdapter(AdminviewbookingsActivity.this, bookingsList);
                recyclerView.setAdapter(adminviewbookingsAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(AdminviewbookingsActivity.this, 1));

            }

            @Override
            public void onFailure(Call<List<Bookings>> call, Throwable t) {
                Toast.makeText(AdminviewbookingsActivity.this, "error message" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}