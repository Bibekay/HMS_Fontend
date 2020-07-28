package com.example.hms.activities.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.activities.users.UserviewhotelsdetailsActivity;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Hotels;
import com.example.hms.serverresponse.ImageResponse;
import com.example.hms.strictmode.StrictModeClass;
import com.example.hms.url.url;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminupdatehotelsActivity extends AppCompatActivity {

    String id;
    EditText hotelName, descricption, hotelPrice;
    ImageView  imageBack, delete;
    CircleImageView hotelImage;
    Button updateHotel;
    String imagePath, imageName;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;

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
        delete = findViewById(R.id.iv_delete);

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

        updateHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly();
                updateHotels();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHotel();
            }

            private void deleteHotel() {
                HMS_API ims_api = url.getInstance().create(HMS_API.class);
                Call<Hotels> voidCall = ims_api.deleteHotel(url.token, id);

                voidCall.enqueue(new Callback<Hotels>() {
                    @Override
                    public void onResponse(Call<Hotels> call, Response<Hotels> response) {

                        if (!response.isSuccessful()) {
                            Toast.makeText(AdminupdatehotelsActivity.this, "Code : " + response.code() + ", Message : " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(AdminupdatehotelsActivity.this, AdminviewbookingsActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Hotels> call, Throwable t) {
                        Toast.makeText(AdminupdatehotelsActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



        hotelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

    }


    private void saveImageOnly() {

        if (imagePath != null) {
            File file = new File(imagePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);

            HMS_API hms_api = url.getInstance().create(HMS_API.class);
            Call<ImageResponse> imageResponseCall = hms_api.uploadHotelImage(url.token, body);


            StrictModeClass.StrictMode();

            try {
                Response<ImageResponse> imageResponseResponse = imageResponseCall.execute();
                imageName = imageResponseResponse.body().getFilename();
            } catch (IOException e) {
                Toast.makeText(AdminupdatehotelsActivity.this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(AdminupdatehotelsActivity.this, "Please choose file to update picture", Toast.LENGTH_SHORT).show();
        }


    }


    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data!=null) {

                Uri uri = data.getData();
                hotelImage.setImageURI(uri);
                imagePath = getRealPathFromUri(uri);

            } else {
                Toast.makeText(AdminupdatehotelsActivity.this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AdminupdatehotelsActivity.this, "image couldnot upload", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(AdminupdatehotelsActivity.this.getApplicationContext(), uri, proj, null, null, null);

        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String res = cursor.getString(colIndex);
        cursor.close();
        return res;
    }
    private void updateHotels(){
        String hotel_name = hotelName.getText().toString();
        String description = descricption.getText().toString();
        String price = hotelPrice.getText().toString();

        Hotels hotels = new Hotels(hotel_name, description, price, imageName);

        HMS_API ims_api = url.getInstance().create(HMS_API.class);
        Call<Hotels> hotelsCall = ims_api.updateHotel(url.token,id, hotels);

        hotelsCall.enqueue(new Callback<Hotels>() {
            @Override
            public void onResponse(Call<Hotels> call, Response<Hotels> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminupdatehotelsActivity.this, "Code : " + response.code() + ", Message : " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(AdminupdatehotelsActivity.this, AdminviewbookingsActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Hotels> call, Throwable t) {
                Toast.makeText(AdminupdatehotelsActivity.this, "Code" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}