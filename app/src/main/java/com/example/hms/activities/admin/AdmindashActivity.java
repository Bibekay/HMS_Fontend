package com.example.hms.activities.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hms.R;
import com.example.hms.activities.users.LoginActivity;
import com.example.hms.activities.users.SignupActivity;
import com.example.hms.api.HMS_API;
import com.example.hms.models.Users;
import com.example.hms.url.url;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdmindashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView adminName;

    RelativeLayout rruser,rrAdminLogout;



    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindash_actvity);

        adminName = findViewById(R.id.txtusername);

        rruser = findViewById(R.id.rruser);
        rrAdminLogout = findViewById(R.id.rradminlogout);

        //Admin`s info display in Admin Dashboard
        openadminName();

        //Admin`s user info display
        rruser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent openUserInfo = new Intent(AdmindashActivity.this, UserInfoActivity.class);
                startActivity(openUserInfo);



            }

        });

        //Admin Logout
        rrAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdmindashActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        SharedPreferences sharedPreferences = AdmindashActivity.this.getSharedPreferences("IMS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");
                        editor.remove("isadmin");
                        editor.remove("status");
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                        url.token = "Bearer ";
                        url.status = "Status";
                        Intent i = new Intent(AdmindashActivity.this, SignupActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //Menu Hooks

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);


        navigationDrawer();


    }

    //Admin info display in Admin Dashboard
    private void openadminName() {

        HMS_API hmsApi = url.getInstance().create(HMS_API.class);
        Call<Users> usersCall = hmsApi.getUserDetails(url.token);
        System.out.println("token is:"+url.token);

        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AdmindashActivity.this, "code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(response.body().getImage()!=null) {
//                    String imgpath = url.BASE_URL + response.body().getImage();
//                    System.out.println("image response is :"+imgpath);
//
//                    Picasso.get().load(imgpath).into(imgProfileAdmin);
//
//                }
//                else
//                {
//                    Picasso.get().load(R.drawable.image1).into(imgProfileAdmin);
//                }

                adminName.setText(response.body().getUsername());

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(AdmindashActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }




    //Navigation drawer function

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);

                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(AdmindashActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        SharedPreferences sharedPreferences = AdmindashActivity.this.getSharedPreferences("IMS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");
                        editor.remove("isadmin");
                        editor.remove("status");
                        editor.remove("username");
                        editor.remove("password");
                        editor.commit();
                        url.token = "Bearer ";
                        url.status = "Status";
                        Intent i = new Intent(AdmindashActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                break;


        }
        return true;
    }

}



