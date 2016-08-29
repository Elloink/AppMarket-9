package com.appmarket.mapleaf.appmarket;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
