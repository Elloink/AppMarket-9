package com.mapleaf.service;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.RemoteViews;

import com.mapleaf.engine.TaskEngine;
import com.mapleaf.mobilesafe.R;
import com.mapleaf.myutils.TaskUtils;
import com.mapleaf.receiver.MyWidgetReceiver;
import com.mapleaf.receiver.ScreenOffReceiver;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WidgetService extends Service {
    public WidgetService() {
    }

    private class WidgetReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo processInfo:runningAppProcesses){
                if(!processInfo.processName.equals(getPackageName())){
                    activityManager.killBackgroundProcesses(processInfo.processName);

                }
            }
        }
    }
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private WidgetReceiver receiver;
    private Timer timer;
    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new WidgetReceiver();
        ScreenOffReceiver screenOffReceiver = new ScreenOffReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(screenOffReceiver,filter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("yijianqingli");
        registerReceiver(receiver,intentFilter);
        final AppWidgetManager manager = AppWidgetManager.getInstance(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ComponentName componentName = new ComponentName(WidgetService.this, MyWidgetReceiver.class);
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.my_appwidget);
                views.setTextViewText(R.id.tv_widget_1, "正在运行的软件："+TaskUtils.getProcess(WidgetService.this));
                views.setTextViewText(R.id.tv_widget_2,"可用内存："+ Formatter.formatFileSize(WidgetService.this,TaskUtils.getAvailMem(WidgetService.this)));
                Intent intent = new Intent();
                intent.setAction("yijianqingli");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(WidgetService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.btn_widget,pendingIntent);
                manager.updateAppWidget(componentName,views);
            }
        },2000,2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        if(receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }
}
