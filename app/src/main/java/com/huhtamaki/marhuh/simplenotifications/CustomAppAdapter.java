package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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

/**
 * Created by marhuh on 8.8.2017.
 */

public class CustomAppAdapter extends ArrayAdapter<Application> {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public CustomAppAdapter(@NonNull Context context, ArrayList<Application> appList) {
        super(context,R.layout.app_custom_row ,appList);
        prefs = context.getSharedPreferences("appStates",context.MODE_PRIVATE);
        editor = prefs.edit();
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.app_custom_row, parent, false);

        Application app = getItem(position);
        final String app_name = app.getName();
        final String app_packageName = app.getPackageName();
        Drawable app_icon = app.getIcon();

        TextView row_text = customView.findViewById(R.id.app_name);
        ImageView row_image = customView.findViewById(R.id.app_img);
        row_text.setText(app_name);
        row_image.setImageDrawable(app_icon);

        Switch mySwitch = customView.findViewById(R.id.app_switch);
        boolean value = prefs.getBoolean(app_packageName,false);
        mySwitch.setChecked(value);


        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                editor.putBoolean(app_packageName,state);
                editor.apply();
                //Toast.makeText(context, app_packageName + "\n" + state, Toast.LENGTH_SHORT).show();
            }
        });

        return customView;

    }
}
