package com.yt.htmlBrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class LocalShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_show);
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() throws IOException {
        WV webView = findViewById(R.id.wv_av);

        Intent intent = getIntent();
        String type = "";
        String venueId = "";
        String target = "";
        String size = "";

        String url = "file:///android_asset/%s/index.html";

        if (intent != null) {
            venueId = intent.getStringExtra("venueId");
            type = intent.getStringExtra("type");
            url = String.format(url, type);
            switch (type) {
                case "athlete":
                    target = intent.getStringExtra("target");
                    url = url + "?venueId=" + venueId + "&target=" + target;
                    break;
                case "referee":
                    url = url + "?venueId=" + venueId;
                    break;
                case "splash":
                    size = intent.getStringExtra("size");
                    url = url + "?venueId=" + venueId + "&showRing=" + size;
                    break;
            }
        }

        webView.loadUrl(url);
        webView.setInitialScale(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}