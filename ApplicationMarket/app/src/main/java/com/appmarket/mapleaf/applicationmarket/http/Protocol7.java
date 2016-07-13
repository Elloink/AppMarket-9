package com.appmarket.mapleaf.applicationmarket.http;

import com.appmarket.mapleaf.applicationmarket.bean.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/12.
 */
public class Protocol7 extends BaseProtocol<ArrayList<String>>{
    @Override
    public String getParams() {
        return "";
    }

    @Override
    public String getKey() {
        return "hot";
    }

    @Override
    public void paseData(String result) {
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(result);
            for(int i=0;i<ja.length();i++){
                String data = ja.getString(i);
                list.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        applist = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            applist.add(list.get(i));
        }
    }
}
