package com.inf244.zocean;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentSummaryActivity extends AppCompatActivity {

    private TextView roomTypeText, nightsText, totalPriceText;
    private RadioGroup paymentMethodGroup;
    private Button confirmPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFF0A1929);

        roomTypeText = findViewById(R.id.roomTypeText);
        nightsText = findViewById(R.id.nightsText);
        totalPriceText = findViewById(R.id.totalPriceText);
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);

        String roomType = getIntent().getStringExtra("ROOM_TYPE");
        int nights = getIntent().getIntExtra("NIGHTS", 1);
        int totalPrice = getIntent().getIntExtra("TOTAL_PRICE", 0);

        roomTypeText.setText(roomType);
        nightsText.setText(nights + " night(s)");
        totalPriceText.setText("₱" + String.format("%,d", totalPrice));

        confirmPaymentButton.setOnClickListener(v -> {
            int selectedPaymentId = paymentMethodGroup.getCheckedRadioButtonId();

            if (selectedPaymentId == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            String paymentMethod;
            if (selectedPaymentId == R.id.onlinePaymentRadio) {
                paymentMethod = "Online Payment (Full Amount)";
            } else {
                paymentMethod = "Cash Payment (50% Deposit)";
            }

            Toast.makeText(this, "Booking confirmed!\n" +
                            roomType + " for " + nights + " night(s)\n" +
                            "Payment: " + paymentMethod,
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent(PaymentSummaryActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
