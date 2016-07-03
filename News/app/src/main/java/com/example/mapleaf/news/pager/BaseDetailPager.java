package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.view.View;

/**
 * Created by Mapleaf on 2016/7/3.
 */
public abstract class BaseDetailPager {
    protected Activity mActivity;
    protected View mRootView;
    public BaseDetailPager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    protected abstract View initView();
    protected  void initData(){

    }
}
