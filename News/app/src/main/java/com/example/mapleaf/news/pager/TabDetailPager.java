package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.mapleaf.news.bean.NewsData;

/**
 * Created by Mapleaf on 2016/7/3.
 */
public class TabDetailPager extends BaseDetailPager{
    private NewsData.NewsMenuData.NewsTabData tabData;
    private TextView textView;
    public TabDetailPager(Activity activity, NewsData.NewsMenuData.NewsTabData newsTabData) {
        super(activity);
        tabData=newsTabData;
    }

    @Override
    protected View initView() {
        textView = new TextView(mActivity);
        textView.setText("菜单详情页");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(40);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        textView.setText(tabData.title);
    }
}
