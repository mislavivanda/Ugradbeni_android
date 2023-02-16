package com.example.projekt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentTeachingSession extends AppCompatActivity {
    private Button evidenceButton;
    private ConnectivityManager mConnectivityManager;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private RequestQueue mQueue;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private WifiManager wifi;
    private String url = "https://ugradbeniserver-mislaviva.pitunnel.com/api/teachingsession/student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        mQueue = Volley.newRequestQueue(this);
        evidenceButton = findViewById(R.id.actionButton);
        evidenceButton.setText("Registriraj se");
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(wifi.isWifiEnabled()) {
                            connect("26FF66", "qa9aq7hqnj");
                        }
                    }
                });
        evidenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("wifistatus", String.valueOf(wifi.isWifiEnabled()));
                if (!wifi.isWifiEnabled()){
                    Intent intent = new Intent(Settings.Panel.ACTION_WIFI);
                    someActivityResultLauncher.launch(intent);
                } else connect("26FF66", "qa9aq7hqnj");
            }
        });
        wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        mConnectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkCallback = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                mConnectivityManager.bindProcessToNetwork(network);
                //phone is connected to wifi network
                LinkProperties info = mConnectivityManager.getLinkProperties(network);
                //You need to filter returned results because there will be multiple ipV6 addresses and only one ipV4 address
                //Nadi prvu koja ima .
                List<LinkAddress> ipAdresses = info.getLinkAddresses();
                String ipAdress=".";
                LinkAddress ipv6Adress = null;
                for(int i=0; i<ipAdresses.size(); i++) {
                    Log.i("Ipadress", ipAdresses.get(i).toString());
                    if(ipAdresses.get(i).toString().contains(".")) {
                        ipAdress=ipAdresses.get(i).toString();
                        String[] adressParts = ipAdress.split("/");
                        ipAdress=adressParts[0];
                        break;
                    } else {
                        LinkAddress ipv6AdressTemp = ipAdresses.get(i);
                        Inet6Address ipv6Type = (Inet6Address)ipv6AdressTemp.getAddress();
                        final byte[] ipv6Bytes = ipv6Type.getAddress();
                        if((ipv6Bytes.length == 16) &&
                                (ipv6Bytes[0] == (byte) 0xfe) &&
                                (ipv6Bytes[1] == (byte) 0x80)) {
                            ipv6Adress = ipv6AdressTemp;
                        }
                    }
                }
                Log.i("Ipv6 adress", ipv6Adress.toString());
                String macAdress = getMacAddressFromIpv6(ipv6Adress);
                Log.i("ipadress", ipAdress);
                Log.i("MACadress", macAdress);
                Map<String, String> params = new HashMap();
                params.put("roomName", "B420");
                params.put("macAddress", macAdress);
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
    private static String getMacAddressFromIpv6(final LinkAddress ipv6)
    {
        byte[] eui48mac = null;
        String macAdressString = "";
        Log.i("Ipv6", ipv6.toString());
        if (ipv6 != null) {
            Log.i("macusao", "1");
            /*
             * Make sure that this is an fe80::/64 link-local address.
             */
            Inet6Address ipv6Type = (Inet6Address)ipv6.getAddress();
            final byte[] ipv6Bytes = ipv6Type.getAddress();
            Log.i("ipv6bytes", Integer.toString(ipv6Bytes.length));
            Log.i("cond1", Boolean.toString(ipv6Bytes[0] == (byte) 0xfe));
            Log.i("cond2", Boolean.toString(ipv6Bytes[1] == (byte) 0x80));
            Log.i("cond3", Boolean.toString(ipv6Bytes[11] == (byte) 0xff));
            Log.i("cond4", Boolean.toString(ipv6Bytes[12] == (byte) 0xfe));
            //ako imamo takvu ip adresu onda je moguce izvuci mac iz nje
            if ((ipv6Bytes != null) &&
                    (ipv6Bytes.length == 16) &&
                    (ipv6Bytes[0] == (byte) 0xfe) &&
                    (ipv6Bytes[1] == (byte) 0x80) && (ipv6Bytes[11] == (byte) 0xff) &&
                    (ipv6Bytes[12] == (byte) 0xfe)) {
                /*
                 * Allocate a byte array for storing the EUI-48 MAC address, then fill it
                 * from the appropriate bytes of the IPv6 address. Invert the 7th bit
                 * of the first byte and discard the "ff:fe" portion of the modified
                 * EUI-64 MAC address.
                 */
                eui48mac = new byte[6];
                eui48mac[0] = (byte) (ipv6Bytes[8] ^ 0x2);
                eui48mac[1] = ipv6Bytes[9];
                eui48mac[2] = ipv6Bytes[10];
                eui48mac[3] = ipv6Bytes[13];
                eui48mac[4] = ipv6Bytes[14];
                eui48mac[5] = ipv6Bytes[15];
                Log.i("macusao", "2");
            } else return "02:00:00:00:00:00";
        }
        for(int i=0; i< eui48mac.length ; i++) {
            macAdressString += String.format("%02x",eui48mac[i]) + ":";
        }
        macAdressString = macAdressString.substring(0, macAdressString.length() - 1);
        return macAdressString;
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
                    //.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
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
