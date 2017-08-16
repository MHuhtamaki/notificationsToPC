package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by marhuh on 14.8.2017.
 */

public class ConnectToWifi {

    private String current_target_SSID;
    private int current_network_ID = -1;
    private Context context;
    private WifiManager manager;
    private boolean connection_result = false;

    public ConnectToWifi(Context context, String current_target_SSID){
        this.context = context;
        this.current_target_SSID = current_target_SSID;
    }

    public boolean connect(){

        manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(true);

        // List of pre-configured networks.
        List<WifiConfiguration> networks = manager.getConfiguredNetworks();

        for(WifiConfiguration network : networks){
            if(network.SSID.replace("\"","").equals(current_target_SSID)){
                current_network_ID = network.networkId;
                //Toast.makeText(context, "SSID:" +current_target_SSID + "\n" + "ID:" + current_network_ID, Toast.LENGTH_SHORT).show();
                connection_result = manager.enableNetwork(current_network_ID,true);
                break;
            }
        }
        return connection_result;
    }
}
