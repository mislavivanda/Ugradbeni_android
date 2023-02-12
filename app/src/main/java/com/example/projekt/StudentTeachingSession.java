package com.example.projekt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentTeachingSession extends AppCompatActivity {
    private Button evidenceButton;
    private ConnectivityManager mConnectivityManager;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private RequestQueue mQueue;
    private String url = "https://ugradbeniserver-mislaviva.pitunnel.com/api/teachingsession/student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_teaching_session);
        mQueue = Volley.newRequestQueue(this);
        evidenceButton = findViewById(R.id.studentSession);
        evidenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect("Redmi Note 10 5G", "mislav1108");
            }
        });
        mConnectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkCallback = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                //phone is connected to wifi network
                LinkProperties info = mConnectivityManager.getLinkProperties(network);
                //You need to filter returned results because there will be multiple ipV6 addresses and only one ipV4 address
                //Nadi prvu koja ima .
                List<LinkAddress> ipAdresses = info.getLinkAddresses();
                String ipAdress=".";
                for(int i=0; i<ipAdresses.size(); i++) {
                    if(ipAdresses.get(i).toString().contains(".")) {
                        ipAdress=ipAdresses.get(i).toString();
                        String[] adressParts = ipAdress.split("/");
                        ipAdress=adressParts[0];
                        break;
                    }
                }
                Log.i("wifimac", ipAdress);
                //nije moguce dohvatit MAC na Android 11
                Map<String, String> params = new HashMap();
                params.put("roomName", "B420");
                params.put("macAddress", "02:00:00:00:00:00");
                params.put("ipAddress", ipAdress);
                params.put("studentID", "451-2021");
                JSONObject objParams = new JSONObject(params);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, objParams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                evidenceButton.setText("Registriran!");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                mQueue.add(request);
            }

            @Override
            public void onLosing(@NonNull Network network, int maxMsToLive) {
                super.onLosing(network, maxMsToLive);
                //phone is about to lose connection to network
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                //phone lost connection to network
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                //user cancelled wifi connection
            }
        };
    }
    public void connect(String ssid, String password) {
        NetworkSpecifier networkSpecifier  = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            networkSpecifier = new WifiNetworkSpecifier.Builder()
                    .setSsid(ssid)
                    .setWpa2Passphrase(password)
                    .setIsHiddenSsid(true) //specify if the network does not broadcast itself and OS must perform a forced scan in order to connect
                    .build();
        }
        NetworkRequest networkRequest  = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            networkRequest = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .setNetworkSpecifier(networkSpecifier)
                    .build();
        }
        mConnectivityManager.requestNetwork(networkRequest, mNetworkCallback);
    }

    public void disconnectFromNetwork(){
        //Unregistering network callback instance supplied to requestNetwork call disconnects phone from the connected network
        mConnectivityManager.unregisterNetworkCallback(mNetworkCallback);
    }
}
