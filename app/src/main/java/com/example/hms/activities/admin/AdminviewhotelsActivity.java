package com.example.hms.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.hms.R;
import com.example.hms.activities.users.UserviewhotelsActivity;
import com.example.hms.adapter.UserviewhotelsAdapter;

public class AdminviewhotelsActivity extends AppCompatActivity {

    AdminviewhotelsActivity activity;
    UserviewhotelsAdapter userviewhotelsAdapter;
    private RecyclerView recyclerView;
    ImageView back;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminviewhotels);



    }
}