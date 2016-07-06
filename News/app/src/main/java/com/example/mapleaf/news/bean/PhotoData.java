package com.example.mapleaf.news.bean;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/6.
 */
public class PhotoData {
    public int retcode;
    public PhotosInfo data;
    public class PhotosInfo{
        public String title;
        public ArrayList<PhotoInfo> news;
    }
    public class PhotoInfo{
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }
}
