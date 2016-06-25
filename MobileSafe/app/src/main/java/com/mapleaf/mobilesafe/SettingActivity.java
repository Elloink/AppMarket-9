package com.mapleaf.mobilesafe;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.mapleaf.engine.SmsEngine;
import com.mapleaf.service.RocketService;
import com.mapleaf.ui.SettingClickItem;
import com.mapleaf.ui.SettingItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity {

    private SettingItem settingItem;
    private SharedPreferences sp;
    private SettingClickItem settingClickItem;
    private SettingClickItem location;

    public interface ProgressSet{
        void setMax(int Max);
        void setProgress(int progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        final String [] items = {"卫士蓝","苹果绿","金属灰","活力橙","半透明"};
        location = (SettingClickItem) findViewById(R.id.setting_location);
        location.setTitle("更换图标位置");
        location.setContent("设置归属地提示框位置");
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this,LocationActivity.class);
                startActivity(intent);
            }
        });
        settingItem = (SettingItem) findViewById(R.id.setting_update);
        settingClickItem = (SettingClickItem) findViewById(R.id.setting_color);
        settingClickItem.setTitle("更新背景");

        settingClickItem.setContent(sp.getString("color","卫士蓝"));
        settingClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setIcon(R.drawable.safe);
                builder.setTitle("归属地风格");
                builder.setNegativeButton("cancel",null);

                builder.setSingleChoiceItems(items, sp.getInt("colornum",0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor edit = sp.edit();
                        settingClickItem.setContent(items[which]);
                        edit.putString("color",items[which]);
                        edit.putInt("colornum",which);
                        edit.commit();
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
     //   settingItem.setTitle("提示更新");
        if(sp.getBoolean("update",true)){
            settingItem.setCheck(true);

        }else{
            settingItem.setCheck(false);

        }

        settingItem.setOnClickListener(new View.OnClickListener() {
            SharedPreferences.Editor edit = sp.edit();
            @Override
            public void onClick(View v) {
                if(settingItem.isChecked()){

                    settingItem.setCheck(false);
                    edit.putBoolean("update",false);
                }else{

                    settingItem.setCheck(true);
                    edit.putBoolean("update",true);
                }
                edit.commit();
            }
        });
    }
    public void startRocket(View view){
        startService(new Intent(SettingActivity.this, RocketService.class));
        finish();
    }
    public void endRocket(View view){
        stopService(new Intent(SettingActivity.this, RocketService.class));
        finish();
    }
    private ProgressDialog dialog;
    public void beifenSMS(View view){
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                SmsEngine.getAllSms(getApplicationContext(), new ProgressSet() {
                    @Override
                    public void setMax(int Max) {
                        dialog.setMax(Max);
                    }

                    @Override
                    public void setProgress(int progress) {
                        dialog.setProgress(progress);
                    }
                });
                dialog.dismiss();
            }
        }.start();

    }
    public void huifuSMS(View view){
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                XmlPullParser pullParser = Xml.newPullParser();
//                try {
//                    pullParser.setInput(new FileInputStream(new File(getFilesDir(),"sms.xml")),"utf-8");
//                    int eventType = pullParser.getEventType();
//                    while (eventType!=pullParser.END_DOCUMENT){
//                        switch (eventType){
//                            case XmlPullParser.START_DOCUMENT:
//                                break;
//                            case XmlPullParser.START_TAG:
//                                if(pullParser.getName().equals("address")){
//                                    eventType = pullParser.next();
//                                    System.out.println(pullParser.getText()+"---------------");
//                                }
//                                break;
//                            case XmlPullParser.END_TAG:
//                                break;
//                        }
//                    }
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        ContentResolver resolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("address","18744015432");
        values.put("type",1);
        values.put("body","i love u");
        values.put("date","235235235");
        resolver.insert(Uri.parse("content://sms"),values);
    }
}
