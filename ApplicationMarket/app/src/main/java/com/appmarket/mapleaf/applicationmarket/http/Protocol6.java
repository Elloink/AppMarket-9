package com.appmarket.mapleaf.applicationmarket.http;

import com.appmarket.mapleaf.applicationmarket.bean.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/12.
 */
public class Protocol6 extends BaseProtocol<ArrayList<CategoryInfo>>{
    @Override
    public String getParams() {
        return "";
    }

    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public void paseData(String result) {
        ArrayList<CategoryInfo> list = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(result);
            for(int i = 0 ;i<ja.length() ; i++){

                JSONObject jo = ja.getJSONObject(i);
                if(jo.has("title")){
                    CategoryInfo info = new CategoryInfo();
                    info.isTitle=true;
                    info.title = jo.getString("title");
                    System.out.println(info.title+"-346-3463463467");
                    list.add(info);
                }
                if(jo.has("infos")){
                    JSONArray ja1 = jo.getJSONArray("infos");
                    for(int j =0;j<ja1.length();j++){
                        JSONObject jo1 = ja1.getJSONObject(j);
                        CategoryInfo info = new CategoryInfo();
                        info.name1 = jo1.getString("name1");
                        info.name2 = jo1.getString("name2");
                        info.name3 = jo1.getString("name3");
                        info.url1 = jo1.getString("url1");
                        info.url2 = jo1.getString("url2");
                        info.url3 = jo1.getString("url3");
                        info.isTitle=false;
                        list.add(info);
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        applist = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
        applist.add(list.get(i));
    }

    }
}
