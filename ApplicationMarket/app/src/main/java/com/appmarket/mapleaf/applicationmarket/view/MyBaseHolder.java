package com.appmarket.mapleaf.applicationmarket.view;

import android.view.View;

/**
 * Created by Mapleaf on 2016/7/9.
 */
public abstract class MyBaseHolder<T> {
    private View rootView;
    private T data;
    public MyBaseHolder(){
        rootView = initView();
        rootView.setTag(this);
    }
    public View getRootView(){
        return rootView;
    }
    public void setData(T data){
        this.data=data;
        refreshView(data);
    }
    public T getData(){
        return data;
    }
    public abstract View initView();
    public abstract void refreshView(T data);
}
