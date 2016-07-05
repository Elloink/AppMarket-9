package com.example.mapleaf.news.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Mapleaf on 2016/7/4.
 */
public class DetailViewPager extends ViewPager{
    public DetailViewPager(Context context) {
        super(context);
    }

    public DetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentItem()==0){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else{
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
