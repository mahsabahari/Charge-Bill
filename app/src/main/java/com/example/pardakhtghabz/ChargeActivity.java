package com.example.pardakhtghabz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ChargeActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        btn = findViewById(R.id.button_choose_charge_amount);

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(ChargeActivity.this, ChooseChargeAmountActivity.class);
            startActivity(intent);
        });
    }
}