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
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Hotels;
import com.example.hms.serverresponse.ImageResponse;
import com.example.hms.strictmode.StrictModeClass;
import com.example.hms.url.url;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminaddhotelsActivity extends AppCompatActivity {
    ImageView back;

    EditText hotelName, hotelDescription, hotelPrice;
    CircleImageView hotelImage;
    Button addHotel;

    String imagePath, imageName;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddhotels);

        hotelName = findViewById(R.id.et_hotelName);
        hotelImage = findViewById(R.id.civ_hotelImage);
        hotelDescription = findViewById(R.id.et_description);
        hotelPrice = findViewById(R.id.et_price);
        addHotel = findViewById(R.id.btnAddHotel);

        back = findViewById(R.id.iv_backpressed);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminaddhotelsActivity.this, AdmindashActivity.class);
                startActivity(i);
            }
        });


        addHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly();
                postHotel();
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
                Toast.makeText(AdminaddhotelsActivity.this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(AdminaddhotelsActivity.this, "Please choose file to update picture", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AdminaddhotelsActivity.this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AdminaddhotelsActivity.this, "image couldnot upload", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(AdminaddhotelsActivity.this.getApplicationContext(), uri, proj, null, null, null);

        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String res = cursor.getString(colIndex);
        cursor.close();
        return res;
    }

    private  void postHotel(){

        String hotel_name = hotelName.getText().toString();
        String description = hotelDescription.getText().toString();
        String price = hotelPrice.getText().toString();



        HMS_API ims_api = url.getInstance().create(HMS_API.class);
        Call<Hotels> hotelsCall = ims_api.addHotels(url.token,  hotel_name, description, price, imageName);

        hotelsCall.enqueue(new Callback<Hotels>() {
            @Override
            public void onResponse(Call<Hotels> call, Response<Hotels> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminaddhotelsActivity.this, "Invalid input " , Toast.LENGTH_SHORT).show();
                    return;

                }
                Intent i = new Intent(AdminaddhotelsActivity.this, AdminviewhotelsActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Hotels> call, Throwable t) {
                Toast.makeText(AdminaddhotelsActivity.this, "Code" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}