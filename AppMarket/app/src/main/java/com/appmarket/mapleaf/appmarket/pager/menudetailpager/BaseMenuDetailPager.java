package com.appmarket.mapleaf.appmarket.pager.menudetailpager;

import android.app.Activity;
import android.view.View;

/**
 * Created by Mapleaf on 2016/8/23.
 */
public abstract class BaseMenuDetailPager {
    public Activity mActivity;
    public View mRootView;
    public BaseMenuDetailPager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }
    public abstract View initView();
    public void initData(){

    }
}
