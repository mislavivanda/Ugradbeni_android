package com.example.projekt;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfesorTeachingSession extends AppCompatActivity {
    private Button evidenceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profesor_teaching_session);

        evidenceButton = findViewById(R.id.profesorSession);


        evidenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager my_wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                my_wifi.setWifiEnabled(true);
            }
        });

    }
}
