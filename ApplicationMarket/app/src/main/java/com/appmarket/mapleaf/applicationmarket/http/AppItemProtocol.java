package com.appmarket.mapleaf.applicationmarket.http;

import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/12.
 */
public class AppItemProtocol extends BaseProtocol<AppInfo>{
    private String packageName;
    public AppItemProtocol(String packageName){
        this.packageName = packageName;
    }
    @Override
    public String getParams() {
        return "&packageName="+packageName;
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public void paseData(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            AppInfo info = new AppInfo();

            info.des = jo.getString("des");
            info.downloadUrl = jo.getString("downloadUrl");
            info.iconUrl = jo.getString("iconUrl");
            info.id = jo.getString("id");
            info.name = jo.getString("name");
            info.packageName = jo.getString("packageName");
            info.size = jo.getLong("size");
            info.stars = (float) jo.getDouble("stars");

            info.author = jo.getString("author");
            info.date = jo.getString("date");
            info.downloadNum = jo.getString("downloadNum");
            info.version = jo.getString("version");

            JSONArray ja = jo.getJSONArray("safe");
            ArrayList<AppInfo.SafeInfo> safeInfoArrayList = new ArrayList<>();
            for(int i =0;i<ja.length();i++){
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo.SafeInfo safeInfo = new AppInfo.SafeInfo();
                safeInfo.safeDes = jo1.getString("safeDes");
                safeInfo.safeDesUrl = jo1.getString("safeDesUrl");
                safeInfo.safeUrl = jo1.getString("safeUrl");
                safeInfoArrayList.add(safeInfo);
            }
            info.safe = safeInfoArrayList;

            JSONArray ja1 = jo.getJSONArray("screen");
            ArrayList<String> screenList= new ArrayList<>();
            for(int i=0;i<ja1.length();i++){
                String string = ja1.getString(i);
                screenList.add(string);
            }
            info.screen=screenList;

            applist=info;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
