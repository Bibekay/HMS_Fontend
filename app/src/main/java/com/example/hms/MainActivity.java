package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hms.activities.admin.AdmindashActivity;
import com.example.hms.activities.users.UserdashActivity;

public class MainActivity extends AppCompatActivity {
    TextView dash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dash = findViewById(R.id.welcome);

        Open();


    }

    private void Open() {

        Intent openAdmin = new Intent(MainActivity.this, UserdashActivity.class);
        startActivity(openAdmin);
        finish();
    }
}
