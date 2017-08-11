package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private WifiManager manager;
    private ArrayList<String> SSIDs;
    private AlertDialog.Builder builder;
    private AlertDialog ad;
    private String current_target_SSID;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Set actionbar.
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();

        populateNetworksToListview();

    }

    private void populateNetworksToListview() {

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
        }
        else{
            Toast.makeText(this, "Failed to find configured networks, try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void setNavigationViewListener(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.myNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.networks:
                Intent intent = new Intent(this, WifiActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.apps:
                Intent apps = new Intent(this, AppsActivity.class);
                startActivity(apps);
                break;
            case R.id.settings:
                // TODO: Implement settings activity.
        }
        return true;
    }
}
