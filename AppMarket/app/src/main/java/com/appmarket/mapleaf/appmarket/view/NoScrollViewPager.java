package com.appmarket.mapleaf.appmarket.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class NoScrollViewPager extends ViewPager{
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
