package com.huhtamaki.marhuh.simplenotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by marhuh on 23.8.2017.
 */

public class NotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Notification received", Toast.LENGTH_LONG).show();
        String title = intent.getStringExtra("title");
        String text_content = intent.getStringExtra("text_content");

        NotificationTransferer transferer = new NotificationTransferer(context, title, text_content);
        transferer.isWifiEnabled();
    }
}
