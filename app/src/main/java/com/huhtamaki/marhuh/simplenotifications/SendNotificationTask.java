package com.huhtamaki.marhuh.simplenotifications;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by marhuh on 16.8.2017.
 */

public class SendNotificationTask extends AsyncTask<NotificationParams, Void, Void> {

    @Override
    protected Void doInBackground(NotificationParams... notificationParamses) {

        String title = notificationParamses[0].title;
        String text_content = notificationParamses[0].text_content;
        String targetIP = notificationParamses[0].IP;
        String targetPort = notificationParamses[0].port;
        try{
            Socket socket = new Socket(targetIP,Integer.parseInt(targetPort));
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(title + "\n" + text_content);
            socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
