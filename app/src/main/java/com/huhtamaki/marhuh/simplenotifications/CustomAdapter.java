package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
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

/**
 * Created by marhuh on 7.8.2017.
 */

class CustomAdapter extends ArrayAdapter<String>{

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public CustomAdapter(@NonNull Context context, ArrayList<String> networks) {
        super(context,R.layout.custom_row,networks);
        prefs = context.getSharedPreferences("networkStates",context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        final String networkSSID = getItem(position);
        final TextView rowText = customView.findViewById(R.id.row_text);
        ImageView listImage = customView.findViewById(R.id.iw_wifi);
        Switch mySwitch = customView.findViewById(R.id.wifi_switch);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //rowText.setText(networkSSID + ", state:" + String.valueOf(b));
                editor.putString(networkSSID,String.valueOf(b));
                editor.apply();
            }
        });

        rowText.setText(networkSSID);
        listImage.setImageResource(R.drawable.wifi_icon_new);

        return customView;
    }
}
