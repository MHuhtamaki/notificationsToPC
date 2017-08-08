package com.huhtamaki.marhuh.simplenotifications;

import android.graphics.drawable.Drawable;

/**
 * Created by marhuh on 8.8.2017.
 */

public class Application {

    private String name = "";
    private Drawable icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
