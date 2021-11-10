package com.yt.htmlBrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SplashActivity extends AppCompatActivity {

    private EditText venueId;
    private EditText size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView(){
        venueId = findViewById(R.id.et_venue_id);
        size = findViewById(R.id.et_size);
        Button confirm = findViewById(R.id.btn_confirm);

        SharedPreferences sp = getSharedPreferences("INFO", MODE_PRIVATE);

        venueId.setText(sp.getString("venueId", null));
        size.setText(sp.getString("size", null));

        confirm.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, LocalShowActivity.class);
            intent.putExtra("type", "splash");
            intent.putExtra("venueId", venueId.getText().toString());
            intent.putExtra("size", size.getText().toString());
            startActivity(intent);
        });
    }
}