package com.example.hms.activities.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hms.R;

public class SignupActivity extends AppCompatActivity {

    Button login, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        login = findViewById(R.id.btnAlreadylogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginpage();
            }

            private void openLoginpage() {
                Intent goToLogin = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }
        });

    }
}
