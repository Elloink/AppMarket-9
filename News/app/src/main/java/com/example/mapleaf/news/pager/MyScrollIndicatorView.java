package com.example.mapleaf.news.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shizhefei.view.indicator.ScrollIndicatorView;

/**
 * Created by Mapleaf on 2016/7/4.
 */
public class MyScrollIndicatorView extends ScrollIndicatorView {
    public MyScrollIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
