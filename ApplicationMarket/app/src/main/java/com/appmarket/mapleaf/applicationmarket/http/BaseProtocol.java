package com.appmarket.mapleaf.applicationmarket.http;

import android.os.SystemClock;

import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;
import com.appmarket.mapleaf.applicationmarket.utils.IOUtils;
import com.appmarket.mapleaf.applicationmarket.utils.StringUtils;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/10.
 */
public abstract class BaseProtocol<T> {

    public T applist ;
    public T getData(int index){

        String result1 = getCache(index);
        //先从缓存中读取数据，若不存在则去网络中获取。
        if(StringUtils.isEmpty(result1)){
            getDataFromServer(index);
            SystemClock.sleep(1000);
            return applist;
        }
        if(result1!=null){
            paseData(result1);
            return applist;

        }
        return null;


    }
    /*
    * 从网络请求数据
    * */
    public void getDataFromServer(final int index) {

        RequestParams params = new RequestParams("http://127.0.0.1:8090/"+getKey()+"?index="+index+getParams());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {
            }
            @Override
            public void onError(Throwable arg0, boolean arg1) {


            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onSuccess(String arg0) {


                if(!StringUtils.isEmpty(arg0)){
                    setCache(index,arg0);
                }
                paseData(arg0);

            }
        });


    }


    public abstract String getParams();

    public abstract String getKey();

    public void setCache(int index,String json){
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir,getKey()+"?index="+index+getParams());
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            long deadLine = System.currentTimeMillis()+30*60*1000;
            writer.write(deadLine+"\n");
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }
    public String getCache(int index){
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir,getKey()+"?index="+index+getParams());
        if(cacheFile.exists()){
            BufferedReader reader =null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadLine = reader.readLine();
                long line = Long.parseLong(deadLine);
                if(System.currentTimeMillis()<line){
                    StringBuffer sb = new StringBuffer();
                    String l ;
                    while((l=reader.readLine())!=null){
                        sb.append(l);
                    }
                    return sb.toString();
                }else{
                    cacheFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }
    public abstract void paseData(String result);
}
