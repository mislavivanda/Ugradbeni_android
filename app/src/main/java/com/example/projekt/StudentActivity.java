package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentActivity extends AppCompatActivity {
    private Button button;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        button = (Button) findViewById(R.id.prisutnostProfesorView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openwifiActivity();
            }
        });
        button1 = (Button) findViewById(R.id.pregled);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPregledActivity();
            }
        });
    }
    public void openwifiActivity(){
        Intent intent = new Intent(this,WifiActivity.class);
        startActivity(intent);
    }
    public void openPregledActivity(){
        Intent intent = new Intent(this,PregledprisutnostistudentActivity.class);
        startActivity(intent);
    }


}