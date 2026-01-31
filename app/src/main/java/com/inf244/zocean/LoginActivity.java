package com.inf244.zocean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            prefs.edit().putBoolean("isLoggedIn", true).apply();

            Intent intent = new Intent(LoginActivity.this, DateSelection.class);
            intent.putExtra("ROOM_TYPE", getIntent().getStringExtra("ROOM_TYPE"));
            startActivity(intent);
            finish();
        });
    }
}
