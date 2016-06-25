package com.mapleaf.engine;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by Mapleaf on 2016/6/12.
 */
public class AddressDao {
    public  static String AddressParse(Context context, String num){
        String location = "";
        File file = new File(context.getFilesDir(),"address.db");
        SQLiteDatabase database = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null,SQLiteDatabase.OPEN_READONLY);
        if(num.matches("^1[34578]\\d{9}$")){
            Cursor cursor = database.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)",
                    new String[]{num.substring(0, 7)});
            if(cursor.moveToNext()){
                location = cursor.getString(0);
            }
            cursor.close();
        }else{
            location="not found";
        }


        database.close();
        return location;
    }
}
