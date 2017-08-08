package com.huhtamaki.marhuh.simplenotifications;

import android.graphics.drawable.Drawable;

/**
 * Created by marhuh on 8.8.2017.
 */

public class Application {

    private String name = "";
    private int icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
