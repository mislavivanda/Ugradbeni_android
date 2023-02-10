package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginstudentActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginstudent);
        button = (Button) findViewById(R.id.loginbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudentActivity();
            }
        });
    }
        public void openStudentActivity () {
            Intent intent = new Intent(this, StudentActivity.class);
            startActivity(intent);
        }
    }
