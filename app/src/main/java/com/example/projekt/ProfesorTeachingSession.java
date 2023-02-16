package com.example.projekt;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfesorTeachingSession extends AppCompatActivity {
    private Button evidenceButton;
    private boolean sessionActive;
    private RequestQueue mQueue;
    private String url = "https://ugradbeniserver-mislaviva.pitunnel.com/api/teachingsession/profesor";

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("sessionActive", sessionActive);
        editor.commit();   // I missed to save the data to preference here,.
    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Log.i("log",sharedPreferences.toString());
        sessionActive = sharedPreferences.getBoolean("sessionActive", false);
    }

    @Override
    public void onBackPressed() {
        SavePreferences();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadPreferences();
        setContentView(R.layout.activity_session);
        mQueue = Volley.newRequestQueue(this);
        evidenceButton = findViewById(R.id.actionButton);
        if(!sessionActive) {
            evidenceButton.setText("Kreiraj sesiju");
        } else evidenceButton.setText("Zatvori sesiju");
        evidenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTeachingSessionAPI();
            }
        });
    }
    private void callTeachingSessionAPI() {
        Map<String, String> params = new HashMap();
        params.put("profesorID", "12345");
        params.put("subjectName", "Ugradbeni računalni sustavi");
        params.put("roomName", "B420");
        JSONObject objParams = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, objParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(sessionActive) {
                            evidenceButton.setText("Kreiraj sesiju");
                            sessionActive=false;
                        }
                        else {
                            evidenceButton.setText("Zatvori sesiju");
                            sessionActive=true;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}
