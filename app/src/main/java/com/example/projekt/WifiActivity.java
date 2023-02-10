package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WifiActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        b1 = findViewById(R.id.on);
        b2 = findViewById(R.id.off);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager my_wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                my_wifi.setWifiEnabled(true);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager my_wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                my_wifi.setWifiEnabled(false);
            }
        });

    }
}