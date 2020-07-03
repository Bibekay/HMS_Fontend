package com.example.hms.activities.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.adapter.UserInfoAdapter;
import com.example.hms.api.Admin_api;
import com.example.hms.models.Users;
import com.example.hms.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserInfoActivity extends AppCompatActivity {

    UserInfoActivity activity;
    private RecyclerView recyclerView;
    private SearchView searchusername;
    UserInfoAdapter userinfo_Adapter;

    public UserInfoActivity() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_userinfo);

        activity = this;
        recyclerView = findViewById(R.id.users_list);
        searchusername = findViewById(R.id.admin_user_search_view);

        loaduserinfo();

        searchusername.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userinfo_Adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    private void loaduserinfo() {


        Admin_api userAPI = url.getInstance().create(Admin_api.class);
        Call<List<Users>> usersCall = userAPI.getCustomer(url.token);
        System.out.println("token is:"+url.token);


        usersCall.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(UserInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                List<Users> usersList = response.body();
                activity.userinfo_Adapter = new UserInfoAdapter(UserInfoActivity.this, usersList);
                recyclerView.setAdapter(userinfo_Adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(UserInfoActivity.this,1));

            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }

    public void AdminDashOpen(View view) {
        Intent openadmindashboard = new Intent(this, AdmindashActivity.class);
        startActivity(openadmindashboard);
    }

}
