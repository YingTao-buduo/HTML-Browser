package com.yt.htmlBrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RefereeActivity extends AppCompatActivity {

    private EditText venueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee);
        initView();
    }

    private void initView(){
        venueId = findViewById(R.id.et_venue_id);
        Button confirm = findViewById(R.id.btn_confirm);

        SharedPreferences sp = getSharedPreferences("INFO", MODE_PRIVATE);

        venueId.setText(sp.getString("venueId", null));

        confirm.setOnClickListener(v -> {
            Intent intent = new Intent(RefereeActivity.this, LocalShowActivity.class);
            intent.putExtra("type", "referee");
            intent.putExtra("venueId", venueId.getText().toString());
            startActivity(intent);
        });
    }
}