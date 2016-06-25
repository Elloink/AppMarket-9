package com.mapleaf.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public abstract class FatherActivity extends Activity {


    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this,new Myges());
    }
private class Myges extends GestureDetector.SimpleOnGestureListener{
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float startX = e1.getRawX();
        float endX = e2.getRawX();
        if((startX-endX)>100){
            tonext();
        }
        if((endX-startX)>100){
            topre();
        }
        return true;
    }
}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void pre(View v) {
        topre();
    }

    public void next(View v) {
        tonext();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            topre();
        }
        return super.onKeyDown(keyCode, event);
    }

    public abstract void topre();

    public abstract void tonext();
}
