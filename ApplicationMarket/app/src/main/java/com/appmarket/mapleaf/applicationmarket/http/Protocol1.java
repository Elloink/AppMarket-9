package com.appmarket.mapleaf.applicationmarket.http;

import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/10.
 */
public class Protocol1 extends BaseProtocol<ArrayList<AppInfo>>{
    private ArrayList<String> pictures;

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public void paseData(String result) {
        // Gson, JsonObject
        // 使用JsonObject解析方式: 如果遇到{},就是JsonObject;如果遇到[], 就是JsonArray
        ArrayList<AppInfo> appInfoList = new ArrayList();
        try {
            JSONObject jo = new JSONObject(result);

            // 解析应用列表数据
            JSONArray ja = jo.getJSONArray("list");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);

                AppInfo info = new AppInfo();
                info.des = jo1.getString("des");
                info.downloadUrl = jo1.getString("downloadUrl");
                info.iconUrl = jo1.getString("iconUrl");
                info.id = jo1.getString("id");
                info.name = jo1.getString("name");
                info.packageName = jo1.getString("packageName");
                info.size = jo1.getLong("size");
                info.stars = (float) jo1.getDouble("stars");

                appInfoList.add(info);
            }

            // 初始化轮播条的数据
            JSONArray ja1 = jo.getJSONArray("picture");
            pictures = new ArrayList<>();
            for (int i = 0; i < ja1.length(); i++) {
                String pic = ja1.getString(i);
                pictures.add(pic);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        applist = new ArrayList<>();
        for(int i =0;i<appInfoList.size();i++){
            applist.add(appInfoList.get(i));
        }
    }
    public ArrayList<String> getPicArr(){
        return pictures;
    }
}
