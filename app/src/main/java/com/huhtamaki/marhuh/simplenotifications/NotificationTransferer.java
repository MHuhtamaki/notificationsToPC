package com.huhtamaki.marhuh.simplenotifications;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by marhuh on 15.8.2017.
 */

public class NotificationTransferer {

    private String[] ipAndPort;
    private String ipWithPort;
    private String targetIP;
    private String targetPort;

    private String title;
    private String text_content;
    private String connected_wifi_name;
    private Context context;

    private SharedPreferences prefs_ips;
    private SharedPreferences prefs_wifis;

    private WifiManager wifiMgr;
    private WifiInfo wifi_info;

    public NotificationTransferer(Context context, String title, String text_content){
        this.title = title;
        this.text_content = text_content;
        this.context = context;
    }

    public void isWifiEnabled(){

        prefs_ips = context.getSharedPreferences("networkInfo", Context.MODE_PRIVATE);

        wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi_info = wifiMgr.getConnectionInfo();
        connected_wifi_name = wifi_info.getSSID();
        connected_wifi_name = connected_wifi_name.replace("\"","");

        // Check if current connected wifi is enabled by user in WifiActivity.
        prefs_wifis = context.getSharedPreferences("networkStates", Context.MODE_PRIVATE);
        boolean networkEnabled = prefs_wifis.getBoolean(connected_wifi_name,false);

        if(networkEnabled){
            ipWithPort = prefs_ips.getString(connected_wifi_name,"");

            if(ipWithPort.equals("")){
                Toast.makeText(context, "SimpleNotifications: No IP stored for current wifi!", Toast.LENGTH_LONG).show();
            }
            else{
                ipAndPort = ipWithPort.split("-");
                targetIP = ipAndPort[0];
                targetPort = ipAndPort[1];


                //Toast.makeText(context, targetIP, Toast.LENGTH_LONG).show();

                // Create parameters for the Asynctask 'SendNotificationTask'.
                NotificationParams params = new NotificationParams(title, text_content, targetIP, targetPort);
                // Everything ok, start the actual data transfer.
                transfer(params);
            }
        }
    }

    public void transfer(NotificationParams params){
        new SendNotificationTask().execute(params);
    }
}
