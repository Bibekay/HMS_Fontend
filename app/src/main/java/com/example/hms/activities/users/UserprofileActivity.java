package com.example.hms.activities.users;

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
import com.example.hms.api.HMS_API;
import com.example.hms.models.Users;
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

public class UserprofileActivity extends AppCompatActivity {

    ImageView back;

    EditText etFullname, etcontact, etEmail,etBirthyear, etGender;
    TextView tvUsername;
    Button btnUpdateprofile;
    CircleImageView ivProfile;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;

    String imagePath,imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        back = findViewById(R.id.iv_backpressed);
        tvUsername = findViewById(R.id.tvUsername);
        etFullname = findViewById(R.id.etFullname);
        etcontact = findViewById(R.id.etcontact);
        etEmail = findViewById(R.id.etEmail);
        etBirthyear = findViewById(R.id.etBirthyear);
        etGender = findViewById(R.id.etGender);
        ivProfile = findViewById(R.id.ivProfile);
        btnUpdateprofile = findViewById(R.id.btnUpdateprofile);


        loadCurrentUser();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserprofileActivity.super.onBackPressed();
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }

            private void chooseImage() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        btnUpdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadFile();
                update();
            }



            private void update() {

                if(imageName!=null){
                    if(etFullname.getText().toString() != null && etcontact.getText().toString() != null && etEmail.getText().toString() != null && etBirthyear.getText().toString()!=null  && etGender.getText().toString()!=null ){
                        Users users = new Users(etFullname.getText().toString(),
                                etcontact.getText().toString(),
                                etEmail.getText().toString(),
                                etBirthyear.getText().toString(),
                                etGender.getText().toString(),
                                imageName, 1);

                        HMS_API hms_api = url.getInstance().create(HMS_API.class);
                        Call<Users> updateInfoCall = hms_api.updateUser(url.token, users);

                        updateInfoCall.enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {
                                if(!response.isSuccessful()){
                                    Toast.makeText(UserprofileActivity.this, "Error : Failed to update info" , Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(UserprofileActivity.this, "Information updated successfully", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {
                                Toast.makeText(UserprofileActivity.this, "Error : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(UserprofileActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(etFullname.getText().toString() != null && etcontact.getText().toString() != null && etEmail.getText().toString() != null && etBirthyear.getText().toString()!=null  && etGender.getText().toString()!=null){
                        Users users = new Users(etFullname.getText().toString(), etcontact.getText().toString(), etEmail.getText().toString(), etBirthyear.getText().toString(), etGender.getText().toString(), 2);
                        HMS_API hms_api = url.getInstance().create(HMS_API.class);
                        Call<Users> updateInfoCall = hms_api.updateUser(url.token, users);

                        updateInfoCall.enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {
                                if(!response.isSuccessful()){
                                    Toast.makeText(UserprofileActivity.this, "Error : Failed to update info" , Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(UserprofileActivity.this, "Information updated successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {
                                Toast.makeText(UserprofileActivity.this, "Error : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(UserprofileActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private void uploadFile() {
                if(imagePath!=null){
                    File file = new File(imagePath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);

                    HMS_API rideshareApi = url.getInstance().create(HMS_API.class);
                    Call<ImageResponse> imageResponseCall = rideshareApi.uploadImage(url.token, body);

                    StrictModeClass.StrictMode();

                    try {
                        Response<ImageResponse> imageResponseResponse = imageResponseCall.execute();
                        imageName = imageResponseResponse.body().getFilename();
                    }catch (IOException e){
                        Toast.makeText(UserprofileActivity.this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(UserprofileActivity.this, "Please choose file to update picture", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data!=null) {

                Uri uri = data.getData();
                ivProfile.setImageURI(uri);
                imagePath = getPath(uri);

            } else {
                Toast.makeText(UserprofileActivity.this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(UserprofileActivity.this, "image couldnot upload", Toast.LENGTH_SHORT).show();
        }
    }


    private String getPath(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(UserprofileActivity.this.getApplicationContext(), uri, proj, null, null, null);

        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String res = cursor.getString(colIndex);
        cursor.close();
        return res;
    }



    private void loadCurrentUser() {

        HMS_API hms_api = url.getInstance().create(HMS_API.class);
        Call<Users> usersCall = hms_api.getUserDetails(url.token);

        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(UserprofileActivity.this, "code" + response.code(), Toast.LENGTH_SHORT ).show();
                    return;
                }
                tvUsername.setText(response.body().getUsername());
                etFullname.setText(response.body().getFullname());
                etcontact.setText(response.body().getContact());
                etEmail.setText(response.body().getEmail());
                etBirthyear.setText(response.body().getBirthyear());
                etGender.setText(response.body().getGender());
                String imagepath = url.BASE_URL + response.body().getImage();
                Picasso.get().load(imagepath).into(ivProfile);

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(UserprofileActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}