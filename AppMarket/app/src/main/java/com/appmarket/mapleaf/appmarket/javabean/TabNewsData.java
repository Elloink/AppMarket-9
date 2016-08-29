package com.appmarket.mapleaf.appmarket.javabean;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/4.
 */
public class TabNewsData {
    public int retcode;
    public TabDetail data;




    public class TabDetail{
        public String title;
        public String more;
        public ArrayList<TabNewsDetail> news;
        public ArrayList<TopNewsDetail> topnews;
    }
    public class TabNewsDetail{
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }
    public class TopNewsDetail{
        public String id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TopNewsDetail{" +
                    "id='" + id + '\'' +
                    ", topimage='" + topimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }



}
