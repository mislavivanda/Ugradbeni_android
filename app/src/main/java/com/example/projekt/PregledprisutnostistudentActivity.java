package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PregledprisutnostistudentActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private String url = "https://ugradbeniserver-mislaviva.pitunnel.com/api/student/teachingstats";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregledprisutnostistudent);
        mQueue = Volley.newRequestQueue(this);
        //getTeachingStats();
        final ArrayList<PrisutnostView> arrayList = new ArrayList<PrisutnostView>();
        arrayList.add(new PrisutnostView("Ugradbeni računalni sustavi", 3, 13));
        arrayList.add(new PrisutnostView("Ugradbeni računalni sustavi", 3, 13));
        renderTeachingStats(arrayList);
    }

    private void getTeachingStats () {
        Map<String, String> params = new HashMap();
        params.put("studentID", "451-2021");
        JSONObject objParams = new JSONObject(params);
        JSONArray parameters = new JSONArray();
        parameters.put(objParams);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            final ArrayList<PrisutnostView> arrayList = new ArrayList<PrisutnostView>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject data = response.getJSONObject(i);
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
        ListView numbersListView = findViewById(R.id.prisutnostStudentView);

        // set the numbersViewAdapter for ListView
        numbersListView.setAdapter(numbersArrayAdapter);
    }
}