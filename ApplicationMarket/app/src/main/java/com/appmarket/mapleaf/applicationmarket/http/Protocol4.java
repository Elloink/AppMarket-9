package com.appmarket.mapleaf.applicationmarket.http;

import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;
import com.appmarket.mapleaf.applicationmarket.bean.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/11.
 */
public class Protocol4 extends BaseProtocol<ArrayList<SubjectInfo>>{
    @Override
    public String getParams() {
        return "";
    }

    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public void paseData(String result) {
        ArrayList<SubjectInfo> list = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(result);
            for(int i =0;i<ja.length();i++){
                JSONObject jsonObject = ja.getJSONObject(i);
                SubjectInfo info = new SubjectInfo();
                info.des = jsonObject.getString("des");
                info.iconUrl = jsonObject.getString("url");
                list.add(info);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        applist = new ArrayList<>();
        for(int i =0;i<list.size();i++){
            applist.add(list.get(i));
        }
    }
}
