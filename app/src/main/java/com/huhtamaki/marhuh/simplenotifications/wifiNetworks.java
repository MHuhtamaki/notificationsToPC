package com.huhtamaki.marhuh.simplenotifications;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class wifiNetworks extends AppCompatActivity {

    WifiManager manager;
    private int PERMISSION_CHANGE_WIFI_STATE;
    ArrayList<String> SSIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_networks);

        manager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        List<WifiConfiguration> networks = manager.getConfiguredNetworks();
        SSIDs = new ArrayList<>();
        for(WifiConfiguration network : networks){
            SSIDs.add(network.SSID);
        }

        ListAdapter myAdapter = new CustomAdapter(this,SSIDs);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        //handlePermission();

        /*BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    List<ScanResult> mScanResults = manager.getScanResults();
                    // add your logic here
                }
            }
        };
        registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        manager.startScan();*/

    }

    private void handlePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            PERMISSION_CHANGE_WIFI_STATE = ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE);
            if(PERMISSION_CHANGE_WIFI_STATE != PackageManager.PERMISSION_GRANTED){

                // Info is show to the user if permission denied before and tried to use again.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CHANGE_WIFI_STATE)) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(this);
                    }
                    builder.setTitle("Info")
                            .setMessage("Permission to access wifi needed! Grant permission now?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(wifiNetworks.this, new String[]{Manifest.permission.CHANGE_WIFI_STATE},
                                            PERMISSION_CHANGE_WIFI_STATE);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }
                else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE},
                            PERMISSION_CHANGE_WIFI_STATE);
                }
            }
            else{
                // Turn on Wifi
                manager.setWifiEnabled(true);
            }
        }

    }

}
