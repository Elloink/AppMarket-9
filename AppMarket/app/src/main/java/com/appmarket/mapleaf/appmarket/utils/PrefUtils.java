package com.appmarket.mapleaf.appmarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mapleaf on 2016/8/18.
 */
public class PrefUtils {
    public static boolean getSharePreferenceBoolean(Context context, String key, boolean defValue) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defValue);
    }

    public static void setSharePreferenceBoolean(Context context, String key, boolean putValue) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, putValue).commit();
    }

    public static String getSharePreferenceString(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static void setSharePreferenceString(Context context, String key, String putValue) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putString(key, putValue).commit();
    }

    public static int getSharePreferenceInt(Context context, String key, int defValue) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    public static void setSharePreferenceInt(Context context, String key, int putValue) {
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putInt(key, putValue).commit();
    }
}
