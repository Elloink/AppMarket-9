package com.mapleaf.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.mapleaf.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mapleaf on 2016/6/19.
 */
public class AppEngine {
    public static List<AppInfo> getAppInfos(Context context){
        List<AppInfo> list = new ArrayList<>();
        PackageManager packageManager=context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo :
                installedPackages) {
            String packageName=packageInfo.packageName;
            String versionName = packageInfo.versionName;
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            Drawable icon = applicationInfo.loadIcon(packageManager);
            String name = applicationInfo.loadLabel(packageManager).toString();
            boolean isUser;
            boolean isSD;
            int flags = applicationInfo.flags;
            if((applicationInfo.FLAG_SYSTEM & flags)==applicationInfo.FLAG_SYSTEM){
                isUser=false;
            }else{
                isUser=true;
            }
            if((applicationInfo.FLAG_EXTERNAL_STORAGE & flags)==applicationInfo.FLAG_EXTERNAL_STORAGE){
                isSD=true;
            }else{
                isSD=false;
            }
            AppInfo appInfo = new AppInfo(name,icon,packageName,versionName,isSD,isUser);
            list.add(appInfo);
        }

        return list;
    }
}
