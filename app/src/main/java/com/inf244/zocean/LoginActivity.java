package com.inf244.zocean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFF0A1929);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);

            if (username.equals("admin") && password.equals("123")) {
                prefs.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("userType", "admin")
                        .apply();

                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
            else if (username.equals("guest") && password.equals("123")) {
                prefs.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("userType", "guest")
                        .apply();

                Intent intent = new Intent(LoginActivity.this, DateSelection.class);
                intent.putExtra("ROOM_TYPE", getIntent().getStringExtra("ROOM_TYPE"));
                intent.putExtra("ROOM_PRICE", getIntent().getIntExtra("ROOM_PRICE", 0));
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Invalid credentials!\nGuest: guest/123\nAdmin: admin/123", Toast.LENGTH_LONG).show();
            }
        });
    }
}