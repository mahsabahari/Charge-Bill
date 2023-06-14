package com.example.pardakhtghabz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChargeActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    String operator;
    EditText number;
    EditText mablagh;
    Button btn;
    public String link = "https://topup.pec.ir/";
    OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        number = findViewById(R.id.editText);
        mablagh = findViewById(R.id.editText2);
        radioGroup = findViewById(R.id.radioGroup);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(view -> {

            if (radioGroup.getCheckedRadioButtonId() == R.id.irancel) {
                operator = "1";
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.hamrah) {
                operator = "2";
            } else {
                operator = "3";
            }
            try {
                charge(number.getText().toString(),operator,mablagh.getText().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void charge(String number,String operator,String mablagh) throws IOException {

        RequestBody body = RequestBody.create(JSON, "{MobileNo: " + number+ ",OperatorType: "
                + operator + "," + "AmountPure: "+mablagh+ ",}");
        Request request = new Request.Builder()
                .url(link)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChargeActivity.this, "ارتباط برقرار نشد", Toast.LENGTH_SHORT).show();
                    }

                });
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            assert response.body() != null;
                            String jsonData = response.body().string();
                            JSONObject jsonObject = new JSONObject(jsonData);
                            if (jsonObject.has("error")) {
                                Toast.makeText(ChargeActivity.this, jsonObject.get("error").toString()
                                        , Toast.LENGTH_SHORT).show();
                            } else {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jsonObject.get("url").toString()));
                                startActivity(browserIntent);
                            }
                        } catch (IOException | JSONException e) {
                            Toast.makeText(ChargeActivity.this, "ارتباط برقرار نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}