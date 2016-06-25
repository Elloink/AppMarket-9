package com.mapleaf.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mapleaf on 2016/6/2.
 */

public class ContackEngine {
    public static List<HashMap<String,String>> getContact(Context context){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();

        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri data_uri = Uri.parse("content://com.android.contacts/data");
        ContentResolver contentResolver = context.getContentResolver();
        Cursor query = contentResolver.query(raw_uri, new String[]{"_id"}, null, null, null);
        while(query.moveToNext()){
            String id = query.getString(0);
            if((!id.equals(null))&&(!id.equals(""))){
                Cursor c = contentResolver.query(data_uri, new String[]{"data1", "mimetype_id","raw_contact_id"}, "raw_contact_id=?", new String[]{id}, null);
                 while(c.moveToNext()){
                     HashMap<String,String> map = new HashMap<>();

                     String data = c.getString(0);
                    String mimetype = c.getString(1);
                    if(mimetype.equals("5")){
                        map.put("phone",data);
                    }else if(mimetype.equals("7")){
                        map.put("name",data);
                    }
                     list.add(map);

                 }
                c.close();
            }

        }
        query.close();
        return list;
    }
}
