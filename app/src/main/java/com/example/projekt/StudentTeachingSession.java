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

import java.util.List;

public class StudentTeachingSession extends AppCompatActivity {
    private Button evidenceButton;
    private ConnectivityManager mConnectivityManager;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_teaching_session);
        evidenceButton = findViewById(R.id.studentSession);
        evidenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect("26FF66", "qa9aq7hqnj");
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
                String MAC = "02:00:00:00:00:00";
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
