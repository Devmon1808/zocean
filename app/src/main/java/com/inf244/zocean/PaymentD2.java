package com.inf244.zocean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentD2 extends AppCompatActivity {

    Button confirmPaymentButton;
    RadioButton onlinePaymentRadio, cashPaymentRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_single_1nights);

        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);
        onlinePaymentRadio = findViewById(R.id.onlinePaymentRadio);
        cashPaymentRadio = findViewById(R.id.cashPaymentRadio);

        confirmPaymentButton.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentD2.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
