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

        // Creation of a DialogBuilder for prompting the IP address from the user.
        createDialogBuilder();
        // Creation of a dialog.
        ad = builder.create();

        // Populate network listview with configured wifi networks.
        // and create onClickListener to list items.
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

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    // Get the name of a clicked wifi network.
                    TextView current = view.findViewById(R.id.row_text);
                    current_target_SSID = current.getText().toString();
                    //Log.d("SSID",current_target_SSID);

                    // Ask user to input an IP address.
                    ad.show();
                }
            });
        }
        else{
            Toast.makeText(this, "Failed to find configured networks, try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void createDialogBuilder() {

        builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(WifiActivity.this);
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setTitle("IP Address");
        builder.setMessage("Please, specify an IP address where to send notifications.");
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                // Get entered Ip address and check it for unwanted characters.
                String ip = input.getText().toString();
                String[] bad_strings = {" ",",","-"};
                for(String s : bad_strings){
                    if(ip.contains(s)){
                        ip = ip.replace(s,"");
                    }
                }
                // Store given IP address for sending notifications.
                // Store the ip with a current SSID (SSID stored in listview onItemClickListener).
                storeTargetIP(ip);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    private void storeTargetIP(String ip) {

        SharedPreferences pref = getSharedPreferences("networkInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(current_target_SSID,ip);
        editor.apply();

        Toast.makeText(this, "Target IP stored!", Toast.LENGTH_SHORT).show();
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
