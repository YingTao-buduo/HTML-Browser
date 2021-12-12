package com.yt.htmlbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;

    private int second = 10;
    private boolean isOperate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);


        tvTimer = findViewById(R.id.tv_timer);

        Button button = findViewById(R.id.btn_find);
        button.setOnClickListener(v -> {
            isOperate = true;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        second = 10;
        isOperate = false;
        timer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOperate = true;
    }

    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if((int)msg.obj == 0){
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                startActivity(intent);
            }else{
                timer();
            }
        }
    };

    private void timer(){
        if (isOperate){
            return;
        }
        tvTimer.setText(String.valueOf(second));
        Message message = new Message();
        message.obj = --second;
        handler.sendMessageDelayed(message, 1000);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                SharedPreferences sp = getSharedPreferences("file", MODE_PRIVATE);
                String path = "/" + uri.getPath().substring(uri.getPath().indexOf(":") + 1);
                sp.edit().putString("path", path).apply();
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                startActivity(intent);
            }
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}