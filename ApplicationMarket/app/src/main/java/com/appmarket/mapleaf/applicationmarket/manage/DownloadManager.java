package com.appmarket.mapleaf.applicationmarket.manage;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;
import com.appmarket.mapleaf.applicationmarket.bean.DownloadInfo;
import com.appmarket.mapleaf.applicationmarket.utils.StringUtils;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mapleaf on 2016/7/15.
 */
public class DownloadManager {
    public static final int STATE_UNDO =1;
    public static final int STATE_WAITING =2;
    public static final int STATE_DOWNLOADING =3;
    public static final int STATE_PAUSE =4;
    public static final int STATE_ERROR =5;
    public static final int STATE_SUCCESS =6;
    private ArrayList<DownloadObserver> observersList = new ArrayList<>();
    private HashMap<String,DownloadInfo> downloadInfoHashMap = new HashMap<>();
    private HashMap<String,DownloadTask> downloadTaskHashMap = new HashMap<>();

    private static DownloadManager downloadManager = new DownloadManager();
    private DownloadManager(){

    }
    public static DownloadManager getInstance(){
        return downloadManager;
    }
    public void registerObserver(DownloadObserver observer){
        observersList.add(observer);
    }
    public void unregisterObserver(DownloadObserver observer){
        observersList.remove(observer);
    }
    public void notifyDownloadStateChanged(DownloadInfo info){
        for (DownloadObserver observer:observersList
             ) {
            observer.onDownloadStateChanged(info);
        }
    }
    public void notifyDownloadProgressChanged(DownloadInfo info){
        for (DownloadObserver observer :
                observersList) {
            observer.onDownloadProgressChanged(info);
        }
    }
    public interface DownloadObserver{
        void onDownloadStateChanged(DownloadInfo info);
        void onDownloadProgressChanged(DownloadInfo info);
    }

    public void download(AppInfo info){
        DownloadInfo downloadInfo = downloadInfoHashMap.get(info.id);
        if(downloadInfo==null){
            downloadInfo = DownloadInfo.copy(info);
        }
        downloadInfo.curState = STATE_WAITING;
        notifyDownloadStateChanged(downloadInfo);
        downloadInfoHashMap.put(downloadInfo.id,downloadInfo);
        DownloadTask task = new DownloadTask(downloadInfo);
        ThreadManager.getThreadPool().execute(task);
        downloadTaskHashMap.put(downloadInfo.id,task);
    }
    class DownloadTask implements Runnable{
        private DownloadInfo info;
        public DownloadTask(DownloadInfo info){
            this.info=info;
        }
        @Override
        public void run() {
            info.curSize = STATE_DOWNLOADING;
            notifyDownloadStateChanged(info);
            File file = new File(info.path);
            if(!file.exists()||file.length()!=info.curSize||info.curSize==0){
                file.delete();
                info.curSize=0;

                RequestParams params = new RequestParams("http://127.0.0.1:8090/download?name="+info.downloadUrl);
                params.setSaveFilePath(info.path);
                x.http().post(params,new Callback.ProgressCallback(){

                    @Override
                    public void onSuccess(Object result) {
                        System.out.println("SUcesss-----------");
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {

                        System.out.println(current+"------------"+total);
                    }
                });

            }else{
                //断点续传
                RequestParams params = new RequestParams("http://127.0.0.1:8090/download?name="+info.downloadUrl+"&range="+file.length());
                params.setSaveFilePath(info.path);
                x.http().post(params,new Callback.ProgressCallback(){

                    @Override
                    public void onSuccess(Object result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {

                    }
                });
            }
        }
    }
    public void parseData(String result){

    }
    public void pause(AppInfo info){
        DownloadInfo downloadInfo = downloadInfoHashMap.get(info.id);
        if(downloadInfo!=null){
            if(downloadInfo.curState==STATE_DOWNLOADING||downloadInfo.curState==STATE_WAITING){
                downloadInfo.curState=STATE_PAUSE;
                notifyDownloadStateChanged(downloadInfo);
                DownloadTask task = downloadTaskHashMap.get(downloadInfo.id);
                if(task!=null){
                    ThreadManager.getThreadPool().cancel(task);
                }
            }
        }
    }
    public void install(AppInfo info){
        DownloadInfo downloadInfo = downloadInfoHashMap.get(info.id);
        if(downloadInfo!=null){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://"+downloadInfo.path),"application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(intent);
        }
    }
}
