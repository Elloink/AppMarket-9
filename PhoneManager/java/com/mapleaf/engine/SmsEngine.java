package com.mapleaf.engine;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Xml;

import com.mapleaf.mobilesafe.SettingActivity;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mapleaf on 2016/6/23.
 */
public class SmsEngine {
    public static void getAllSms(Context context, SettingActivity.ProgressSet progressSet){
        System.out.println("123");
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        progressSet.setMax(cursor.getCount());

        int process=0;
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(new FileOutputStream(new File(context.getFilesDir(),"sms.xml")),"utf-8");
            serializer.startDocument("utf-8",true);
            serializer.startTag(null,"smss");
            while (cursor.moveToNext()){
                SystemClock.sleep(100);
                serializer.startTag(null,"sms");
                serializer.startTag(null,"address");
                String address = cursor.getString(0);
                serializer.text(address);
                serializer.endTag(null,"address");
                serializer.startTag(null,"date");
                String date = cursor.getString(1);
                serializer.text(date);
                serializer.endTag(null,"date");
                serializer.startTag(null,"type");
                String type = cursor.getString(2);
                serializer.text(type);
                serializer.endTag(null,"type");
                serializer.startTag(null,"body");
                String body = cursor.getString(3);
                serializer.text(body);
                serializer.endTag(null,"body");
                serializer.endTag(null,"sms");
                process++;
                progressSet.setProgress(process);
            }
            serializer.endTag(null,"smss");
            serializer.endDocument();
            serializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
