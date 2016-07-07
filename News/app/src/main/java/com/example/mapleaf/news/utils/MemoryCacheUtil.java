package com.example.mapleaf.news.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Mapleaf on 2016/7/7.
 */
public class MemoryCacheUtil {
    private LruCache<String,Bitmap> lruCache ;
    public MemoryCacheUtil(){
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("____________________maxMemory"+maxMemory);
        lruCache = new LruCache<String,Bitmap>((int) (maxMemory/8)){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;

            }
        };
    }
    public Bitmap getCacheFromMemory(String url){
        return lruCache.get(url);
    }
    public void setCacheToMemory(String url,Bitmap bitmap){
        lruCache.put(url,bitmap);
    }
}
