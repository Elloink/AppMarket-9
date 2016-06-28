package com.example.mapleaf.yokumenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Mapleaf on 2016/6/28.
 */
public class SlideMenu extends ViewGroup{
    private View menuView,mainView;
    public SlideMenu(Context context) {
        super(context);
        init();

    }



    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView=getChildAt(0);
        mainView=getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        menuView.measure(menuView.getLayoutParams().width,heightMeasureSpec);
        mainView.measure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        menuView.layout(-menuView.getLayoutParams().width,0,0,menuView.getMeasuredHeight());
        mainView.layout(0,0,mainView.getMeasuredWidth(),mainView.getMeasuredHeight());
    }

    private int downX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int deltaX = moveX-downX;
                int newScrollX = getScrollX()-deltaX;
                if(newScrollX<-menuView.getLayoutParams().width) newScrollX = -menuView.getLayoutParams().width;
                if(newScrollX>0) newScrollX=0;
                scrollTo(newScrollX,0);
                downX=moveX;
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollX()<-menuView.getLayoutParams().width/2){
                    scrollTo(-menuView.getLayoutParams().width,0);
                }else{
                    scrollTo(0,0);

                }
                break;
        }
        return true;
    }
    class ScrollAnimation extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            super.applyTransformation(interpolatedTime, t);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int deltaX = moveX-downX;
                if(Math.abs(deltaX)>8){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);

    }
    public void changeMenu(){
        if(getScrollX()==0){
            scrollTo(-menuView.getLayoutParams().width,0);

        }else if (getScrollX()==-menuView.getLayoutParams().width){
            scrollTo(0,0);

        }
    }
}
