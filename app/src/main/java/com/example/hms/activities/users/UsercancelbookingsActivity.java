package com.example.hms.activities.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.activities.admin.AdminupdatehotelsActivity;
import com.example.hms.activities.admin.AdminviewhotelsActivity;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Bookings;
import com.example.hms.models.Hotels;
import com.example.hms.url.url;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsercancelbookingsActivity extends AppCompatActivity {

    String id;
    TextView hotelName, descricption, hotelPrice;
    ImageView imageBack;
    CircleImageView hotelImage;
    Button cancelBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercancelbookings);

        imageBack = findViewById(R.id.iv_backpressed);
        hotelName = findViewById(R.id.et_hotelName);
        descricption = findViewById(R.id.et_description);
        hotelPrice = findViewById(R.id.et_price);
        hotelImage = findViewById(R.id.civ_hotelImage);
        cancelBooking = findViewById(R.id.btnCancel);


        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsercancelbookingsActivity.this, UserbookingActivity.class);
                startActivity(intent);

            }
        });

        //retriving single data through recycle view   //
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            hotelName.setText(bundle.getString("hotel_name"));
            descricption.setText(bundle.getString("description"));
            hotelPrice.setText(bundle.getString("price"));

            String imagepath = url.BASE_URL + bundle.getString("hotel_image");
            Picasso.get().load(imagepath).into(hotelImage);
        }

        cancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingCancel();
            }

            private void bookingCancel() {

                HMS_API ims_api = url.getInstance().create(HMS_API.class);
                Call<Bookings> voidCall = ims_api.deleteBooking(url.token, id);

                voidCall.enqueue(new Callback<Bookings>() {
                    @Override
                    public void onResponse(Call<Bookings> call, Response<Bookings> response) {

                        if (!response.isSuccessful()) {
                            Toast.makeText(UsercancelbookingsActivity.this, "Code : " + response.code() + ", Message : " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(UsercancelbookingsActivity.this, UserdashActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Bookings> call, Throwable t) {
                        Toast.makeText(UsercancelbookingsActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
}