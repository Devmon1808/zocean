package com.inf244.zocean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPanel extends AppCompatActivity {

    Button loginButton;
    EditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        loginButton = findViewById(R.id.b_loginButton);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminPanel.this, GuestActivity.class);
            startActivity(intent);
            finish();
        });
    }
    public void goToGuest(View view) {
        Intent intent = new Intent(AdminPanel.this, GuestActivity.class);
        startActivity(intent);
        finish();
    }
}
