package com.example.mapleaf.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mapleaf on 2016/7/1.
 */
public class PrefUtil {
    public static void setPref(Context context,String key,boolean flag){
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        sp.edit().putBoolean(key,flag).apply();
    }
    public static boolean getPref(Context context,String key,boolean flag){
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        boolean bool = sp.getBoolean(key, flag);
        return bool;
    }
}
