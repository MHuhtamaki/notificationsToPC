package com.huhtamaki.marhuh.simplenotifications;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

/**
 * Created by marhuh on 15.8.2017.
 */

public class NotificationListener extends NotificationListenerService {

    SharedPreferences prefs;
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        //checkApplication(sbn);
        //Toast.makeText(this, sbn.toString(), Toast.LENGTH_LONG).show();
        Bundle extras = sbn.getNotification().extras;
        String title = (String) extras.getCharSequence(Notification.EXTRA_TITLE);
        String text_content = (String)extras.getCharSequence(Notification.EXTRA_TEXT);

        Intent intent = new Intent("com.huhtamaki.marhuh.simplenotifications");
        intent.putExtra("title", title);
        intent.putExtra("text_content", text_content);
        sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        // Implement what you want here
    }

    private void checkApplication(StatusBarNotification sbn) {
        prefs = getSharedPreferences("appStates",MODE_PRIVATE);
        String packageName = sbn.getPackageName();
        boolean state = prefs.getBoolean(packageName,false);

        if(state != false){
            Context context = getApplicationContext();
            TransferNotification transferer = new TransferNotification(context, sbn);
            transferer.transfer();
        }
    }
}
