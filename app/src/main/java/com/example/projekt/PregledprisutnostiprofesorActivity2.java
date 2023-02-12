package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PregledprisutnostiprofesorActivity2 extends AppCompatActivity {
    private RequestQueue mQueue;
    private String url = "https://ugradbeniserver-mislaviva.pitunnel.com/api/profesor/teachingstats";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregledprisutnostiprofesor2);
        mQueue = Volley.newRequestQueue(this);
        getTeachingStats();
    }


    private void getTeachingStats() {
        Map<String, String> params = new HashMap();
        params.put("profesorID", "12345");
        JSONObject objParams = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, objParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            final ArrayList<PrisutnostView> arrayList = new ArrayList<PrisutnostView>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                String name = data.getString("name");
                                int score = data.getInt("score");
                                int total =data.getInt("total");
                                arrayList.add(new PrisutnostView(name, score, total));
                            }
                            renderTeachingStats(arrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    private void renderTeachingStats (ArrayList<PrisutnostView> arrayList) {

        // Now create the instance of the NumebrsViewAdapter and pass
        // the context and arrayList created above
        prisutnostViewAdapter numbersArrayAdapter = new prisutnostViewAdapter(this, arrayList);

        // create the instance of the ListView to set the numbersViewAdapter
        ListView numbersListView = findViewById(R.id.prisutnostProfesorView);

        // set the numbersViewAdapter for ListView
        numbersListView.setAdapter(numbersArrayAdapter);
    }
}