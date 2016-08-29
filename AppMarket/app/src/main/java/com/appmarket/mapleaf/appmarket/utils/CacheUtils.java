package com.appmarket.mapleaf.appmarket.utils;

import android.content.Context;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class CacheUtils {
    public static void setCache(Context context,String url,String json){
        PrefUtils.setSharePreferenceString(context,url,json);
    }
    public static String getCache(Context context,String url){
        return PrefUtils.getSharePreferenceString(context,url,null);
    }
}
