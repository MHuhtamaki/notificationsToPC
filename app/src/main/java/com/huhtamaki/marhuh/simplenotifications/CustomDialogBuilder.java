package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by marhuh on 11.8.2017.
 */

public class CustomDialogBuilder {

    private AlertDialog.Builder builder;
    private Context context;
    private String current_target_SSID;

    public CustomDialogBuilder(Context context, String current_target_SSID){
        this.context = context;
        this.current_target_SSID = current_target_SSID;
    }


    public AlertDialog.Builder createDialogBuilder(String title) {

        builder = new AlertDialog.Builder(context);

        if(title.equals("IP Address")){
            final EditText input = new EditText(context);
            input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            builder.setTitle(title);
            builder.setMessage("Specify an IP and port number where to send notifications. " + "\n" +
                    "e.g. 10.0.0.1-1234");
            builder.setView(input);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    // Get entered Ip address and check it for unwanted characters.
                    String ip = input.getText().toString();
                    String[] bad_strings = {" ",","};
                    for(String s : bad_strings){
                        if(ip.contains(s)){
                            ip = ip.replace(s,"");
                        }
                    }
                    // Check the ip address for validity.
                    ValidateIP validator = new ValidateIP();
                    boolean isValidIP = validator.isValidIp(ip);
                    Toast.makeText(context,String.valueOf(isValidIP),Toast.LENGTH_SHORT).show();

                    if(isValidIP){
                        //Toast.makeText(context, current_target_SSID +  ":" + ip, Toast.LENGTH_SHORT).show();
                        // Store given IP address for sending notifications.
                        storeTargetIP(ip);
                    }
                    else{
                        Toast.makeText(context, "Specified IP address not valid!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        else{
            // DISABLED AT THE MOMENT

           /* builder.setTitle(title);
            builder.setMessage("Do you wish to connect to this network?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Toast.makeText(context, current_target_SSID, Toast.LENGTH_SHORT).show();

                    // Connect to the wifi-network.
                    ConnectToWifi connector = new ConnectToWifi(context, current_target_SSID);
                    boolean result  = connector.connect();

                    // If connection established successfully, recall activity 'WifiActivity'
                    if(result){
                        // This needs to fixed so that previous activity will be killed first.
                        Intent intent = new Intent(context, WifiActivity.class);
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, "Attempt to connect failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });*/
        }
        return builder;
    }

    private void storeTargetIP(String ip) {

        SharedPreferences pref = context.getSharedPreferences("networkInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(current_target_SSID,ip);
        editor.apply();

        Toast.makeText(context, "Target IP stored!", Toast.LENGTH_SHORT).show();
    }
}
