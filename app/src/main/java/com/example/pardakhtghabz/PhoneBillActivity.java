package com.example.pardakhtghabz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class PhoneBillActivity extends AppCompatActivity {

    Button btn;
    EditText number;
    TextView error;
    public String link = "https://charge.sep.ir/Inquiry/FixedLineBillInquiry";
    OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_bill);

        btn = findViewById(R.id.button);
        number = findViewById(R.id.editText2);
        error = findViewById(R.id.text2);

        btn.setOnClickListener(view -> {

            try {
                inquiry(number.getText().toString());
            } catch (IOException e) {
                Toast.makeText(this, "ارتباط برقرار نشد!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void inquiry(String number) throws IOException {

        RequestBody body = RequestBody.create(JSON, "{FixedLineNumber: " + number + "}");
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
                        error.setText( "ارتباط برقرار نشد!");
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            assert response.body() != null;
                            String jsonData = response.body().string();
                            JSONObject object = new JSONObject(jsonData);
                            if (object.getString("code").equals("0")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("data",object.getString("data"));
                                Intent intent = new Intent(PhoneBillActivity.this,
                                        BillResultsActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else{
                                error.setText(object.getString("errorMessage"));
                            }
                        } catch (JSONException | IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PhoneBillActivity.this, "ارتباط برقرار نشد!",
                                            Toast.LENGTH_SHORT).show();
                                    call.cancel();
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}