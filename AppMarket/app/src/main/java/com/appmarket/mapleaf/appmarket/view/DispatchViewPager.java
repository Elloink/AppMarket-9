package com.appmarket.mapleaf.appmarket.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Mapleaf on 2016/8/24.
 */
public class DispatchViewPager extends ViewPager{
    public DispatchViewPager(Context context) {
        super(context);
    }

    public DispatchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int startX;
    int startY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX= (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int dx = endX-startX;
                int dy = endY-startY;
                if(Math.abs(dy)>Math.abs(dx)){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else{
                    int currentItem = getCurrentItem();
                    if(dx>0){
                        //向右划（左翻）
                        if(currentItem==0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else{
                        //向左划（右翻）
                        int count = getAdapter().getCount();
                        if(currentItem==count-1){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
