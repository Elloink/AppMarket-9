package com.mapleaf.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;


import com.mapleaf.bean.TaskInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mapleaf on 2016/6/20.
 */
public class TaskEngine {
    public static List<TaskInfo> getTaskInfo(Context context) {
        List<TaskInfo> list = new ArrayList<>();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager packageManager = context.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses
                ) {
            TaskInfo taskInfo = new TaskInfo();
            String packageName = processInfo.processName;
            taskInfo.setPackageName(packageName);
            Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{processInfo.pid});
            int totalPss = processMemoryInfo[0].getTotalPss();
            long ramSize = totalPss*1024;
            taskInfo.setRamSize(ramSize);
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
                int flags = applicationInfo.flags;
                boolean isUser;
                if((applicationInfo.FLAG_SYSTEM&flags)==applicationInfo.FLAG_SYSTEM){
                    isUser = false;
                }else{
                    isUser = true;
                }
                taskInfo.setUser(isUser);
                Drawable icon = applicationInfo.loadIcon(packageManager);
                taskInfo.setIcon(icon);
                String name = applicationInfo.loadLabel(packageManager).toString();
                taskInfo.setName(name);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            list.add(taskInfo);
        }
        return list;
    }
}
