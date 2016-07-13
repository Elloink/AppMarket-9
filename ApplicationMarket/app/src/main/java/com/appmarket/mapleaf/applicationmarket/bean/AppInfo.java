package com.appmarket.mapleaf.applicationmarket.bean;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/10.
 */
public class AppInfo {
    public String des;
    public String downloadUrl;
    public String iconUrl;
    public String id;
    public String name;
    public String packageName;
    public long size;
    public float stars;

    public String author;
    public String date;
    public String downloadNum;
    public String version;
    public ArrayList<SafeInfo> safe;
    public ArrayList<String> screen;

    public static class SafeInfo{
        public String safeDes;
        public String safeDesUrl;
        public String safeUrl;
    }
}
