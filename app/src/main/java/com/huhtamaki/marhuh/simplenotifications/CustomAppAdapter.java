package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
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
 * Created by marhuh on 8.8.2017.
 */

public class CustomAppAdapter extends ArrayAdapter<Application> {

    public CustomAppAdapter(@NonNull Context context, ArrayList<Application> appList) {
        super(context,R.layout.app_custom_row ,appList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.app_custom_row, parent, false);

        Application app = getItem(position);
        String app_name = app.getName();
        Drawable app_icon = app.getIcon();

        TextView row_text = customView.findViewById(R.id.app_name);
        ImageView row_image = customView.findViewById(R.id.app_img);
        row_text.setText(app_name);
        row_image.setImageDrawable(app_icon);

        return customView;

    }
}
