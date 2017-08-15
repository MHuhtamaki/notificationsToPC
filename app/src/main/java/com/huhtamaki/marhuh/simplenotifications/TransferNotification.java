package com.huhtamaki.marhuh.simplenotifications;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

/**
 * Created by marhuh on 15.8.2017.
 */

public class TransferNotification {

    StatusBarNotification sbn;
    Context context;

    public TransferNotification(Context context, StatusBarNotification sbn){
        this.sbn = sbn;
        this.context = context;
    }

    public void transfer(){

        String notification = sbn.toString();
        Toast.makeText(context, notification, Toast.LENGTH_LONG).show();
    }
}
