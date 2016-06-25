package com.mapleaf.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mapleaf on 2016/6/21.
 */
public class WatchDogDao {
    public WatchDogDao(Context context){
        openHelper = new WatchDogOpenHelper(context);
    }
    private WatchDogOpenHelper openHelper;
    public void add(String packageName){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("packageName",packageName);
        database.insert("info",null,values);
        database.close();
    }
    public void delete(String packageName){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete("info","packageName=?",new String[]{packageName});
        database.close();
    }
    public boolean query(String packageName){
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query("info", new String[]{"packageName"}, "packageName=?", new String[]{packageName}, null, null, null);
        if(cursor.moveToNext()){
            return true;
        }
        cursor.close();
        database.close();
        return false;
    }
    public List<String> queryAllPackageName(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query("info", new String[]{"packageName"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            list.add(name);
        }
        cursor.close();
        database.close();
        return list;
    }
}
