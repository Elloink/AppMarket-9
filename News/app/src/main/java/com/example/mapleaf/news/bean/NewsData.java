package com.example.mapleaf.news.bean;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/2.
 */
public class NewsData {
    public int retcode;
    public ArrayList<NewsMenuData> data;
    public class NewsMenuData{
        public int type;
        public String id;
        public String title;
        public String url;
        public ArrayList<NewsTabData> children;

        public class NewsTabData{
            public int type;
            public String id;
            public String title;
            public String url;
        }
        }


}
