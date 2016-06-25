package com.mapleaf.myutils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Mapleaf on 2016/6/3.
 */

public abstract class MyAsycnTask {
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            afterTask();
        }
    };
    public abstract void preTask();
    public abstract  void doInBack();
    public abstract  void afterTask();
    public void excute(){
        preTask();
        new Thread(){
            @Override
            public void run() {
                super.run();
                doInBack();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}
