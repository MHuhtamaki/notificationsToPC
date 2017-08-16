package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by marhuh on 7.8.2017.
 */

class CustomAdapter extends ArrayAdapter<String>{

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;
    private String connected_wifi_name;
    private AlertDialog ad_IP;
    private AlertDialog ad_wifi;
    private AlertDialog.Builder builder;
    private String current_target_SSID;
    private ImageView listImage;

    public CustomAdapter(@NonNull Context context, ArrayList<String> networks) {
        super(context,R.layout.custom_row,networks);
        this.context = context;
        prefs = context.getSharedPreferences("networkStates",Context.MODE_PRIVATE);
        editor = prefs.edit();

        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifi_info = wifiMgr.getConnectionInfo();
        connected_wifi_name = wifi_info.getSSID();
        connected_wifi_name = connected_wifi_name.replace("\"","");

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        final String networkSSID = getItem(position);
        listImage = customView.findViewById(R.id.iw_wifi);
        final TextView rowText = customView.findViewById(R.id.row_text);
        rowText.setText(networkSSID);
        rowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the name of a clicked wifi network.
                current_target_SSID = networkSSID;

                // Creation of a DialogBuilder for prompting the IP address from the user.
                CustomDialogBuilder myDialogBuilder = new CustomDialogBuilder(context, current_target_SSID);
                builder = myDialogBuilder.createDialogBuilder("IP Address");

                // Creation of a dialog.
                ad_IP = builder.create();
                ad_IP.show();
            }
        });

        // Commented out possibility to click wifi icon and connect to that wifi.
        // maybe enabled later on...


        /*listImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the name of a clicked wifi network.
                current_target_SSID = networkSSID;

                // Creation of a DialogBuilder for connecting to a wifi network.
                CustomDialogBuilder myDialogBuilder = new CustomDialogBuilder(context, current_target_SSID);
                builder = myDialogBuilder.createDialogBuilder("Connect to a wifi-network");

                ad_wifi = builder.create();
                ad_wifi.show();
            }
        });*/


        Switch mySwitch = customView.findViewById(R.id.wifi_switch);
        boolean value = prefs.getBoolean(networkSSID,false);
        mySwitch.setChecked(value);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                editor.putBoolean(networkSSID,state);
                editor.apply();
            }
        });

        if(rowText.getText().toString().equals(connected_wifi_name)){
            listImage.setImageResource(R.drawable.wifi_icon_new);
        }
        else{
            listImage.setImageResource(R.drawable.wifi_icon_new_unactive);
        }

        return customView;
    }


}
