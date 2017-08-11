package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.DialogInterface;
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

    public CustomDialogBuilder(Context context){
        this.context = context;
    }

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
        else{
            builder.setTitle(title);
            builder.setMessage("Do you wish to connect to this network?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(context, "Clicked Yes", Toast.LENGTH_SHORT).show();
                    //listImage.setImageResource(R.drawable.wifi_icon_new);
                }
            });
            builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
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
