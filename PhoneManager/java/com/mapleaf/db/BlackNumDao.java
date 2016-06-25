package com.mapleaf.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mapleaf.bean.BlackNumInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mapleaf on 2016/6/18.
 */
public class BlackNumDao {
    private BlackNumOpenHelper openHelper;
    public BlackNumDao(Context context){
        openHelper = new BlackNumOpenHelper(context);
    }
    public void add(String blacknum,String mode){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("blacknum",blacknum);
        values.put("mode",mode);
        database.insert("info",null,values);
        database.close();

    }
    public void delete(String blacknum){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete("info","blacknum=?",new String[]{blacknum});
        database.close();
    }
    public List<BlackNumInfo> queryAll(){
        List<BlackNumInfo> list = new ArrayList<>();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query("info", new String[]{"blacknum", "mode"}, null, null, null, null, "_id desc");
        while(cursor.moveToNext()){
            String blacknum = cursor.getString(0);
            String mode = cursor.getString(1);
            BlackNumInfo numInfo = new BlackNumInfo(blacknum,mode);
            list.add(numInfo);

        }
        cursor.close();
        database.close();
        return list;
    }
    public List<BlackNumInfo> queryPart(String limit,String offset){
        List<BlackNumInfo> list = new ArrayList<>();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        //Cursor cursor = database.query("info", new String[]{"blacknum", "mode"}, null, null, null, null, "_id desc");
        Cursor cursor = database.rawQuery("select blacknum,mode from info order by _id desc limit ? offset ?", new String[]{limit, offset});
        while(cursor.moveToNext()){
            String blacknum = cursor.getString(0);
            String mode = cursor.getString(1);
            BlackNumInfo numInfo = new BlackNumInfo(blacknum,mode);
            list.add(numInfo);

        }
        cursor.close();
        database.close();
        return list;
    }
}
