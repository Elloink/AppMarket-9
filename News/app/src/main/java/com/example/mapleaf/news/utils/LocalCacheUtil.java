package com.example.mapleaf.news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Mapleaf on 2016/7/7.
 */
public class LocalCacheUtil {
    public static final String LOCAL_CACHE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/news_cache";


    public Bitmap getBitmapFromLocal(String url){
        File file = new File(LOCAL_CACHE_PATH,url);
        if(file.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public void setBitmapToLocal(String url, Bitmap bitmap){
        File file = new File(LOCAL_CACHE_PATH,url);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
