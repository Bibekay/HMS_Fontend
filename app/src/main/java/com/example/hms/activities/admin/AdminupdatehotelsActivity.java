package com.example.hms.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hms.R;
import com.example.hms.activities.users.UserviewhotelsdetailsActivity;
import com.example.hms.url.url;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminupdatehotelsActivity extends AppCompatActivity {

    String id;
    EditText hotelName, descricption, hotelPrice;
    ImageView  imageBack;
    CircleImageView hotelImage;
    Button updateHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminupdatehotels);

        imageBack = findViewById(R.id.iv_backpressed);
        hotelName = findViewById(R.id.et_hotelName);
        descricption = findViewById(R.id.et_description);
        hotelPrice = findViewById(R.id.et_price);
        hotelImage = findViewById(R.id.civ_hotelImage);
        updateHotel = findViewById(R.id.btnSave);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminupdatehotelsActivity.super.onBackPressed();
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


    }
}