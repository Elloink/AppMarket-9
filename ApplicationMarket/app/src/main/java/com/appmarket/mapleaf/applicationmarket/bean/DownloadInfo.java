package com.appmarket.mapleaf.applicationmarket.bean;

import android.os.Environment;

import com.appmarket.mapleaf.applicationmarket.manage.DownloadManager;

import java.io.File;

/**
 * Created by Mapleaf on 2016/7/15.
 */
public class DownloadInfo {
    public String id;
    public String name;
    public String downloadUrl;
    public long size;
    public String packageName;
    public long curSize;
    public int curState;
    public static final String APP_MARKET = "APP_MARKET";
    public static final String DOWNLOAD = "download";
    public String path;
    public float getProgress(){
        return (float)curSize/size;
    }

    public static DownloadInfo copy(AppInfo info){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.name = info.name;
        downloadInfo.id = info.id;
        downloadInfo.downloadUrl = info.downloadUrl;
        downloadInfo.packageName = info.packageName;
        downloadInfo.size = info.size;
        downloadInfo.curSize=0;
        downloadInfo.curState = DownloadManager.STATE_UNDO;
        downloadInfo.path = downloadInfo.getFilePath();
        return downloadInfo;
    }

    public String getFilePath(){
        StringBuffer sb = new StringBuffer();
        String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
        sb.append(sdCard);
        sb.append(File.separator);
        sb.append(APP_MARKET);
        sb.append(File.separator);
        sb.append(DOWNLOAD);
        if(createDir(sb.toString())){
            return sb.toString()+File.separator+name+".apk";
        }
        return null;
    }

    private boolean createDir(String dir){
        File dirFile = new File(dir);
        if(!dirFile.exists()||!dirFile.isDirectory()){
            return dirFile.mkdirs();
        }
        return true;
    }
}
