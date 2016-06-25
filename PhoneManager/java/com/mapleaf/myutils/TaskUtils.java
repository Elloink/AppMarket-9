package com.mapleaf.myutils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.util.List;

/**
 * Created by Mapleaf on 2016/6/21.
 */
public class TaskUtils {
    public static int getProcess(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        int size = runningAppProcesses.size();
        return size;

    }
    public static long getAvailMem(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }
    public static long getTotalMem(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return memoryInfo.totalMem;
        }
        return 1;
    }
}
