package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfesorActivity extends AppCompatActivity {
    private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        button = (Button) findViewById(R.id.evidencija);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfesorEvidence();
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
    public void openProfesorEvidence(){
        Intent intent = new Intent(this, ProfesorTeachingSession.class);
        startActivity(intent);
    }
    public void openPregledActivity(){
        Intent intent = new Intent(this,PregledprisutnostiprofesorActivity2.class);
        startActivity(intent);
    }

}