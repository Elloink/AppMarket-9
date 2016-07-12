package com.appmarket.mapleaf.applicationmarket.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class MyApplication extends Application{
    private static Context context;
    private static Handler handler;
    private static int mainThreadID;


    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadID() {
        return mainThreadID;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        context = getApplicationContext();
        handler = new Handler();
        mainThreadID = android.os.Process.myTid();




    }
}
