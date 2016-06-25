package com.mapleaf.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.mapleaf.db.WatchDogDao;
import com.mapleaf.mobilesafe.WatchDogActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WatchDogService extends Service {
    private String name=null;
    public WatchDogService() {
    }

    private unLockReceiver unLockReceiver ;
    private class unLockReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            name = intent.getStringExtra("pack");
            System.out.println("jiesuoke===================================----------------"+name);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private WatchDogDao dao;
    @Override
    public void onCreate() {
        super.onCreate();
        unLockReceiver = new unLockReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.mapleaf.unlock");
        registerReceiver(unLockReceiver,intentFilter);
        dao = new WatchDogDao(getApplicationContext());
        final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<String> lists = dao.queryAllPackageName();
                boolean b = false;
                List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
                for (ActivityManager.RunningTaskInfo info:runningTasks){
                    ComponentName baseActivity = info.baseActivity;
                    String packageName = baseActivity.getPackageName();
                    if(lists.contains(packageName)){
                        b=true;
                    }else{
                        b = false;
                    }
                    if(b){
                        if(!packageName.equals(name)) {
                        System.out.println("=-============================="+packageName);
                            Intent intent = new Intent(WatchDogService.this, WatchDogActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("packageName", packageName);
                            startActivity(intent);
                        }else{

                        }
                    }
                }


            }
        },200,200);
    }
}
