package com.yt.htmlbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AthleteActivity extends AppCompatActivity {

    private EditText venueId;
    private EditText target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);

        initView();
    }

    private void initView(){
        venueId = findViewById(R.id.et_venue_id);
        target = findViewById(R.id.et_target);
        Button confirm = findViewById(R.id.btn_confirm);

        SharedPreferences sp = getSharedPreferences("INFO", MODE_PRIVATE);

        venueId.setText(sp.getString("venueId", null));
        target.setText(sp.getString("target", null));

        confirm.setOnClickListener(v -> {
            Intent intent = new Intent(AthleteActivity.this, LocalShowActivity.class);
            intent.putExtra("type", "athlete");
            intent.putExtra("venueId", venueId.getText().toString());
            intent.putExtra("target", target.getText().toString());
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("venueId",venueId.getText().toString());
            editor.putString("target",target.getText().toString());
            editor.commit();
            startActivity(intent);
        });

    }

}