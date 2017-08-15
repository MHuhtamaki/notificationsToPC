package com.huhtamaki.marhuh.simplenotifications;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

/**
 * Created by marhuh on 15.8.2017.
 */

public class TransferNotification {

    private String title;
    private String text_content;
    private Context context;

    public TransferNotification(Context context,String title, String text_content){
        this.title = title;
        this.text_content = text_content;
        this.context = context;
    }

    public void transfer(){
    }
}
