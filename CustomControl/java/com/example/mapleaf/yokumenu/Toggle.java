package com.example.mapleaf.yokumenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mapleaf on 2016/6/27.
 */
public class Toggle extends View{
    private ToggleState state;
    private Bitmap background;
    private Bitmap slide;
    public Toggle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Toggle(Context context) {
        super(context);
    }
    public enum ToggleState{
        open,close
    }
    public void setToggleState(ToggleState state){
        this.state = state;
    }
    public void setBackgroundRes(int ResId){
        background = BitmapFactory.decodeResource(getResources(),ResId);
    }
    public void setSlideRes(int ResId){
        slide = BitmapFactory.decodeResource(getResources(),ResId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(background.getWidth(),background.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,0,0,null);
        if(isSliding){
            canvas.drawBitmap(slide,loc,0,null);
            isSliding=false;
        }else{
            if(state==ToggleState.open){
                canvas.drawBitmap(slide,loc,0,null);
            }
            if(state==ToggleState.close){
                canvas.drawBitmap(slide,0,0,null);
            }
        }

    }

    private boolean isSliding;
    private int currentX,loc;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isSliding=true;
        currentX = (int) event.getX();
        loc = currentX-slide.getWidth()/2;
        if(currentX<slide.getWidth()/2){
            loc=0;
        }else if(currentX>background.getWidth()-slide.getWidth()/2){
            loc=background.getWidth()-slide.getWidth();
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                if(currentX<background.getWidth()/2){
                    loc=0;
                    if(state!=ToggleState.open){
                        if(listener!=null){
                            listener.onToggleStateChange(ToggleState.open);
                        }
                    }
                    state=ToggleState.open;
                }else{
                    loc=background.getWidth()-slide.getWidth();
                    if(state!=ToggleState.close){
                        if(listener!=null){
                            listener.onToggleStateChange(ToggleState.close);
                        }
                    }
                    state=ToggleState.close;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        invalidate();
        return true;

    }
    private OnToggleStateChangeListener listener;
    public void setOnToggleStateChangeListener(OnToggleStateChangeListener listener){
        this.listener = listener;
    }
    public interface OnToggleStateChangeListener{
        void onToggleStateChange(ToggleState state);
    }
}
