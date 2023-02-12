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
        button = (Button) findViewById(R.id.pregled);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPregledActivity();
            }
        });
        button1 = (Button) findViewById(R.id.evidencija);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudentEvidence();
            }
        });
    }
    public void openStudentEvidence(){
        Intent intent = new Intent(this,StudentTeachingSession.class);
        startActivity(intent);
    }
    public void openPregledActivity(){
        Intent intent = new Intent(this,PregledprisutnostistudentActivity.class);
        startActivity(intent);
    }


}