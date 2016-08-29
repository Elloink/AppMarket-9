package com.appmarket.mapleaf.appmarket.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by Mapleaf on 2016/8/24.
 */
public class MyTabPageIndicator extends TabPageIndicator{
    public MyTabPageIndicator(Context context) {
        super(context);
    }

    public MyTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
