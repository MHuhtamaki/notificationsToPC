package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by marhuh on 7.8.2017.
 */

class CustomAdapter extends ArrayAdapter<String>{


    public CustomAdapter(@NonNull Context context, ArrayList<String> networks) {
        super(context,R.layout.custom_row,networks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        String networkSSID = getItem(position);
        TextView rowText = customView.findViewById(R.id.row_text);
        ImageView listImage = customView.findViewById(R.id.iw_wifi);

        rowText.setText(networkSSID);
        listImage.setImageResource(R.drawable.wifi_icon_new);

        return customView;
    }
}
