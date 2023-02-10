package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private  Button button1;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.profesor);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        button = (Button) findViewById(R.id.student);
        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openLogActivity();
        }
    });
}

    public void openLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void openLogActivity(){
        Intent intent = new Intent(this,LoginstudentActivity.class);
        startActivity(intent);
    }
}