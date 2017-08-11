package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
import java.util.Objects;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by marhuh on 7.8.2017.
 */

class CustomAdapter extends ArrayAdapter<String>{

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;
    String connected_wifi_name;

    public CustomAdapter(@NonNull Context context, ArrayList<String> networks) {
        super(context,R.layout.custom_row,networks);
        prefs = context.getSharedPreferences("networkStates",context.MODE_PRIVATE);
        editor = prefs.edit();
        this.context = context;

        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifi_info = wifiMgr.getConnectionInfo();
        connected_wifi_name = wifi_info.getSSID();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        final String networkSSID = getItem(position);
        final TextView rowText = customView.findViewById(R.id.row_text);
        ImageView listImage = customView.findViewById(R.id.iw_wifi);
        rowText.setText(networkSSID);

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
