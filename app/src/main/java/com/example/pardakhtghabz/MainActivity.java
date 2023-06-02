package com.example.pardakhtghabz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CardView payBill,buyCharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payBill = findViewById(R.id.card_view_phone_bill);
        buyCharge = findViewById(R.id.card_view_buy_charge);

        payBill.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PhoneBillActivity.class);
            startActivity(intent);
        });

        buyCharge.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChargeActivity.class);
            startActivity(intent);
        });

    }
}