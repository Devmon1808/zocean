package com.inf244.zocean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button singleRoomButton, doubleRoomButton, suiteRoomButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFF0A1929);

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        singleRoomButton = findViewById(R.id.singleRoomButton);
        doubleRoomButton = findViewById(R.id.doubleRoomButton);
        suiteRoomButton = findViewById(R.id.suiteRoomButton);

        singleRoomButton.setOnClickListener(v -> handleReservation("OCEAN SINGLE", 37000));
        doubleRoomButton.setOnClickListener(v -> handleReservation("OCEAN DOUBLE", 67000));
        suiteRoomButton.setOnClickListener(v -> handleReservation("OCEAN SUITE", 97000));
    }

    private void handleReservation(String roomType, int roomPrice) {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Intent intent;
        if (isLoggedIn) {
            intent = new Intent(MainActivity.this, DateSelection.class);
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        intent.putExtra("ROOM_TYPE", roomType);
        intent.putExtra("ROOM_PRICE", roomPrice);
        startActivity(intent);
    }
}