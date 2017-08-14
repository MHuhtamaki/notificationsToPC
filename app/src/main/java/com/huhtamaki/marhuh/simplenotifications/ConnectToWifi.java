package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by marhuh on 14.8.2017.
 */

public class ConnectToWifi {

    private String current_target_SSID;
    private Context context;

    public ConnectToWifi(Context context, String current_target_SSID){
        this.context = context;
        this.current_target_SSID = current_target_SSID;
    }

    public void connect(){

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }
}
