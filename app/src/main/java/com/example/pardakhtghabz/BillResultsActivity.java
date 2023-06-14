package com.example.pardakhtghabz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class BillResultsActivity extends AppCompatActivity {

    TextView midAmount,finalAmount,midId,finalId,billId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_results);

        midAmount = findViewById(R.id.mid_mablagh);
        finalAmount = findViewById(R.id.final_mablagh);
        midId = findViewById(R.id.mid_id);
        finalId = findViewById(R.id.final_id);
        billId = findViewById(R.id.bill_id);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                JSONObject object = new JSONObject(bundle.getString("data"));
                String midAmountString = object.getJSONObject("MidTerm").getString("Amount");
                String finalAmountString = object.getJSONObject("FinalTerm").getString("Amount");
                String midIdString = object.getJSONObject("MidTerm").getString("PaymentID");
                String finalIdString = object.getJSONObject("FinalTerm").getString("PaymentID");
                String billIdString = object.getJSONObject("FinalTerm").getString("BillID");

                midAmount.setText(midAmountString);
                finalAmount.setText(finalAmountString);
                midId.setText(midIdString);
                finalId.setText(finalIdString);
                billId.setText(billIdString);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }
}