package com.example.mapleaf.news.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Mapleaf on 2016/7/4.
 */
public class TopNewsViewPager extends ViewPager{
    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int startX;
    int startY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();
                if(Math.abs(endX-startX)>Math.abs(endY-startY)){
                    if(endX>startX){
                        if(getCurrentItem()==0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else{
                        if(getCurrentItem()==getAdapter().getCount()-1)
                        getParent().requestDisallowInterceptTouchEvent(false);

                    }
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
