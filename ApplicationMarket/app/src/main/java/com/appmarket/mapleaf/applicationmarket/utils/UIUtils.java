package com.appmarket.mapleaf.applicationmarket.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.appmarket.mapleaf.applicationmarket.global.MyApplication;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class UIUtils {
    public static Context getContext(){
        return MyApplication.getContext();
    }
    public static Handler getHandler(){
        return MyApplication.getHandler();
    }
    public static int getMainThreadID(){
        return MyApplication.getMainThreadID();
    }
    public static int dp2px(float dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density+0.5f);
    }
    public static float px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return px/density;
    }
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }
    public static boolean isRunOnMainThread(){
        int tid = Process.myTid();
        if(tid==getMainThreadID()){
            return true;
        }
        return false;
    }
    public static void runOnMainThread(Runnable runnable){
        if(isRunOnMainThread()){
            runnable.run();
        }else{
            getHandler().post(runnable);
        }
    }

    public static String getString(int id){
        return getContext().getResources().getString(id);
    }
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }
    public static ColorStateList getColorStateList(int id){
        return getContext().getResources().getColorStateList(id);
    }
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelOffset(id);
    }
}
