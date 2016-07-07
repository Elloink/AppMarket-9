package com.example.mapleaf.news.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.mapleaf.news.R;

/**
 * Created by Mapleaf on 2016/7/7.
 */
public class MyBitmapUtil {
    public NetCacheUtil netCacheUtil;
    public LocalCacheUtil localCacheUtil;
    public MemoryCacheUtil memoryCacheUtil;
    public MyBitmapUtil(){
        memoryCacheUtil = new MemoryCacheUtil();
        localCacheUtil = new LocalCacheUtil();
        netCacheUtil = new NetCacheUtil(localCacheUtil,memoryCacheUtil);
    }
    public void display(ImageView iv_phonemenu, String listimage) {
        iv_phonemenu.setImageResource(R.drawable.topnews_item_default);
        //从内存获取数据
        Bitmap cacheFromMemory = memoryCacheUtil.getCacheFromMemory(listimage);
        if(cacheFromMemory!=null){
            iv_phonemenu.setImageBitmap(cacheFromMemory);
            System.out.println("从内存获取数据");
            return;
        }
        //从本地获取数据
        Bitmap bitmapFromLocal = localCacheUtil.getBitmapFromLocal(listimage);
        if(bitmapFromLocal!=null){
            iv_phonemenu.setImageBitmap(bitmapFromLocal);
            System.out.println("从本地获取数据");
            memoryCacheUtil.setCacheToMemory(listimage,bitmapFromLocal);
            return;
        }
        //从网络获取数据
        netCacheUtil.getBitmapFromNet(iv_phonemenu,listimage);
        System.out.println("从网络获取数据");


    }
}
