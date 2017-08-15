package com.huhtamaki.marhuh.simplenotifications;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by marhuh on 15.8.2017.
 */

public class NotificationListener extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        //checkApplication(sbn);
        int notificationCode = 53;
        Intent intent = new Intent("com.huhtamaki.marhuh.simplenotifications");
        intent.putExtra("Result",notificationCode);
        sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        // Implement what you want here
    }

    private void checkApplication(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

    }
}
