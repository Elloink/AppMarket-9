package com.mapleaf.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Mapleaf on 2016/6/20.
 */
public class TaskInfo {
    private String name;
    private Drawable icon;
    private boolean isCheck=false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", ramSize=" + ramSize +
                ", packageName='" + packageName + '\'' +
                ", isUser=" + isUser +
                '}';
    }

    public TaskInfo(Drawable icon, String name, long ramSize, String packageName, boolean isUser) {
        this.icon = icon;
        this.name = name;
        this.ramSize = ramSize;
        this.packageName = packageName;
        this.isUser = isUser;
    }
    public TaskInfo(){

    }

    private long ramSize;
    private String packageName;
    private boolean isUser;

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

    public long getRamSize() {
        return ramSize;
    }

    public void setRamSize(long ramSize) {
        this.ramSize = ramSize;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}
