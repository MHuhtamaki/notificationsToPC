package com.huhtamaki.marhuh.simplenotifications;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity {

    private WifiManager manager;
    private ArrayList<String> SSIDs;
    private int PERMISSION_CHANGE_WIFI_STATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_networks);

        manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(true);
        List<WifiConfiguration> networks = manager.getConfiguredNetworks();

        SSIDs = new ArrayList<>();
        if(networks != null){
            for(WifiConfiguration network : networks){
                String id = network.SSID;
                id = id.replace("\"","");
                SSIDs.add(id);
            }

            ListAdapter myListAdapter = new CustomAdapter(this,SSIDs);
            ListView listView = (ListView) findViewById(R.id.listview_networks);
            listView.setAdapter(myListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: Implement code for wifi network click.
                // TODO: Open a list of devices in that network or something...
            }
            });
        }
        else{
            Toast.makeText(this, "No networks", Toast.LENGTH_SHORT).show();
        }

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
                                    ActivityCompat.requestPermissions(WifiActivity.this, new String[]{Manifest.permission.CHANGE_WIFI_STATE},
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
