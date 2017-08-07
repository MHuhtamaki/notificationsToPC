package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Created by marhuh on 7.8.2017.
 */

class CustomAdapter extends ArrayAdapter<String>{


    public CustomAdapter(@NonNull Context context, String[] networks) {
        super(context,R.layout.custom_row,networks);
    }
}
