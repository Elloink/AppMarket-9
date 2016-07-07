package com.example.mapleaf.news.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mapleaf on 2016/7/7.
 */
public class NetCacheUtil {
    private LocalCacheUtil mLocalCacheUtil;
    private MemoryCacheUtil memoryCacheUtil;
    public NetCacheUtil(LocalCacheUtil localCacheUtil, MemoryCacheUtil CacheUtil) {
        mLocalCacheUtil= localCacheUtil;
        memoryCacheUtil = CacheUtil;
    }

    public void getBitmapFromNet(ImageView iv_phonemenu, String listimage) {
        new MyAsynTask().execute(iv_phonemenu,listimage);
    }
    class MyAsynTask extends AsyncTask<Object,Void,Bitmap>{


        private ImageView imageView;
        private String url;
        @Override
        protected Bitmap doInBackground(Object... objects) {
            imageView = (ImageView) objects[0];
            url = (String) objects[1];
            return download(url);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null){
                imageView.setImageBitmap(bitmap);
                mLocalCacheUtil.setBitmapToLocal(url,bitmap);
                memoryCacheUtil.setCacheToMemory(url,bitmap);
            }

        }
    }

    private Bitmap download(String url) {
        HttpURLConnection conn=null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            if(conn.getResponseCode()==200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return null;
    }
}
