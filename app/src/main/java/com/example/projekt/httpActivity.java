package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class httpActivity extends AppCompatActivity {
    String myUrl = "https://api.mocki.io/v1/a44b26bb";
    TextView resultsTextView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        resultsTextView = (TextView) findViewById(R.id.results);

    }
}