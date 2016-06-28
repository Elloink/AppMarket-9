package com.example.mapleaf.yokumenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Mapleaf on 2016/6/27.
 */
public class RefleshListView extends ListView{
    private int currentY;
    private View headerView;
    private int measuredHeight;
    private final int PULL_REFLESH=0;
    private final int RELEASE_REFLESH=1;
    private final int REFLESHING=2;
    private int currentState=0;
    private ImageView iv_arrow;
    private ProgressBar pb_rotate;
    private TextView tv_reflesh;
    private TextView tv_time;
    private RotateAnimation upAnimation,downAnimation;
    public RefleshListView(Context context) {
        super(context);
        init();
    }


    public RefleshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        headerView = View.inflate(getContext(),R.layout.reflesh_header,null);
        iv_arrow= (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_rotate = (ProgressBar) headerView.findViewById(R.id.pb_rotate);
        tv_reflesh= (TextView) headerView.findViewById(R.id.tv_reflesh);
        tv_time= (TextView) headerView.findViewById(R.id.tv_time);
        headerView.measure(0,0);
        measuredHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0,-measuredHeight,0,0);
        addHeaderView(headerView);
        upAnimation = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);
        downAnimation = new RotateAnimation(-180,-360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        downAnimation.setDuration(300);
        downAnimation.setFillAfter(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentY= (int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(currentState==REFLESHING){
                    break;
                }
                int deltaY = (int) (ev.getY()-currentY);
                int height = -measuredHeight+deltaY;
                if(height>-measuredHeight&&getFirstVisiblePosition()==0){
                    headerView.setPadding(0,height,0,0);
                    if(height>=0&&currentState==PULL_REFLESH){
                        currentState=RELEASE_REFLESH;
                        reflesh();
                    }else if(height<0&&currentState==RELEASE_REFLESH){
                        currentState=PULL_REFLESH;
                        reflesh();
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                if(currentState==PULL_REFLESH){
                    headerView.setPadding(0,-measuredHeight,0,0);
                }else if(currentState==RELEASE_REFLESH){
                    currentState=REFLESHING;
                    reflesh();
                    if(listener!=null){
                        listener.onReflesh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    private void reflesh(){
        switch (currentState){
            case PULL_REFLESH:
                tv_reflesh.setText("下拉刷新");
                pb_rotate.setVisibility(INVISIBLE);
                iv_arrow.setVisibility(VISIBLE);
                iv_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFLESH:
                //iv_arrow.setImage
                tv_reflesh.setText("松开刷新");
                pb_rotate.setVisibility(INVISIBLE);
                iv_arrow.setVisibility(VISIBLE);
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFLESHING:
                headerView.setPadding(0,0,0,0);
                tv_reflesh.setText("正在刷新");
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(INVISIBLE);
                pb_rotate.setVisibility(VISIBLE);
                break;
        }
    }
    public void completeReflesh(){
        headerView.setPadding(0,-measuredHeight,0,0);
        iv_arrow.setVisibility(VISIBLE);
        pb_rotate.setVisibility(INVISIBLE);
        tv_reflesh.setText("下拉刷新");


        tv_time.setText(new Date().toString());

        currentState=PULL_REFLESH;
    }
    public void setOnRefleshListener(OnRefleshListener listener){
        this.listener=listener;
    }
    private OnRefleshListener listener;
    public interface OnRefleshListener{
        void onReflesh();
    }
}
