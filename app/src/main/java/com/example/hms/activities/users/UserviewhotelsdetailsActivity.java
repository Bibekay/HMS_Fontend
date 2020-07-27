package com.example.hms.activities.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Bookings;
import com.example.hms.url.url;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserviewhotelsdetailsActivity extends AppCompatActivity {

    String id;
    TextView hotelName, descricption, hotelPrice;
    ImageView hotelImage, imageBack;
    Button booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewhotelsdetails);

        hotelName = findViewById(R.id.et_hotelName);
        descricption = findViewById(R.id.et_description);
        hotelPrice = findViewById(R.id.et_price);
        hotelImage = findViewById(R.id.civ_hotelImage);
        booking = findViewById(R.id.btnConformBooking);
        imageBack = findViewById(R.id.iv_backpressed);



        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserviewhotelsdetailsActivity.super.onBackPressed();
            }
        });



        //retriving single data through recycle view from productAdapter  //
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            hotelName.setText(bundle.getString("hotel_name"));
            descricption.setText(bundle.getString("description"));
            hotelPrice.setText(bundle.getString("price"));

            String imagepath = url.BASE_URL + bundle.getString("hotel_image");
            Picasso.get().load(imagepath).into(hotelImage);
        }


        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookHotel();
            }

            private void bookHotel() {
                String hotel = id;
                HMS_API ims_api = url.getInstance().create(HMS_API.class);
                Call<Bookings> orderCall = ims_api.bookHotel(url.token, hotel);

                orderCall.enqueue(new Callback<Bookings>() {
                    @Override
                    public void onResponse(Call<Bookings> call, Response<Bookings> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(UserviewhotelsdetailsActivity.this, "Order Successfull", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(UserviewhotelsdetailsActivity.this, "Order Successfull", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Bookings> call, Throwable t) {
                        Toast.makeText(UserviewhotelsdetailsActivity.this,  "Order Successfull", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}