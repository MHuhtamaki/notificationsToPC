package com.huhtamaki.marhuh.simplenotifications;

/**
 * Created by marhuh on 16.8.2017.
 */

public class NotificationParams {

    String title;
    String text_content;
    String IP;
    String port;

    public NotificationParams(String title, String text_content, String IP, String port){
        this.title = title;
        this.text_content = text_content;
        this.IP = IP;
        this.port = port;
    }
}
