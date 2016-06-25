package com.mapleaf.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Mapleaf on 2016/6/19.
 */
public class AppInfo {
    private String name;
    private Drawable icon;
    private String packageName;
    private String versionName;
    private boolean isSD;
    private boolean isUser;

    public AppInfo(){
        super();
    }
    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", isSD=" + isSD +
                ", isUser=" + isUser +
                '}';
    }

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isSD() {
        return isSD;
    }

    public void setSD(boolean SD) {
        isSD = SD;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public AppInfo(String name, Drawable icon, String packageName, String versionName, boolean isSD, boolean isUser) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
        this.versionName = versionName;
        this.isSD = isSD;
        this.isUser = isUser;
    }
}
