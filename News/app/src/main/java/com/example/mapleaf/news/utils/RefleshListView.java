package com.example.mapleaf.news.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mapleaf.news.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mapleaf on 2016/7/5.
 */
public class RefleshListView extends ListView{
    public static final int PULL = 0;
    public static final int RELEASE = 1;
    public static final int REFRESHING = 2;
    private int curState = PULL;

    public RefleshListView(Context context) {
        super(context);
        initHeaderView();
        initFootView();
    }

    public RefleshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFootView();

    }

    public RefleshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFootView();

    }
    private View headerView;
    private TextView tv_refresh;
    private ImageView iv_refresh;
    private ProgressBar pb_refresh;
    private TextView tv_time;
    private int measuredHeight;
    private void initHeaderView(){

        headerView = View.inflate(getContext(), R.layout.refresh_header,null);
        initAnim();
        tv_refresh = (TextView) headerView.findViewById(R.id.tv_refresh);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);
        iv_refresh = (ImageView) headerView.findViewById(R.id.iv_refresh_arr);
        pb_refresh = (ProgressBar) headerView.findViewById(R.id.pb_refresh);
        addHeaderView(headerView);
        headerView.measure(0,0);
        measuredHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0,-measuredHeight,0,0);

    }
    private View footView;
    private int footMeasuredHeight;
    private boolean isLast;
    private void initFootView(){
        footView = View.inflate(getContext(),R.layout.footview_loadmore,null);
        addFooterView(footView);
        footView.measure(0,0);
        footMeasuredHeight = footView.getMeasuredHeight();
        footView.setPadding(0,-footMeasuredHeight,0,0);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i==SCROLL_STATE_IDLE||i==SCROLL_STATE_FLING){
                    if(getLastVisiblePosition()==getCount()-1&&!isLast){
                        footView.setPadding(0,0,0,0);
                        setSelection(getCount()-1);
                        isLast=true;
                        mListener.loadMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }



    private int startY=-1;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(getFirstVisiblePosition()==0){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:

                    startY= (int) ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(curState==REFRESHING){
                        break;
                    }
                    if(startY==-1){
                        startY= (int) ev.getRawY();
                    }
                    int curY = (int) ev.getRawY();
                    int padding = curY-startY-measuredHeight;
                    if(curY>startY){
                        headerView.setPadding(0,padding,0,0);
                        if(padding>0 &&curState!=RELEASE){
                            curState=RELEASE;
                            refreshState();
                        }else if(padding<=0&&curState!=PULL){
                            curState=PULL;

                            refreshState();
                        }
                        return true;
                    }
                    startY=curY;
                    break;
                case MotionEvent.ACTION_UP:
                    startY=-1;
                    if(curState==PULL){
                        headerView.setPadding(0,-measuredHeight,0,0);
                    }else if(curState==RELEASE){
                        curState=REFRESHING;
                        refreshState();
                        headerView.setPadding(0,0,0,0);
                        mListener.onRefresh();
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);

    }
    public void onRefreshFinish(){
        if(isLast){
            footView.setPadding(0,-footMeasuredHeight,0,0);
            isLast=false;
        }else{
            curState=PULL;
            tv_refresh.setText("下拉刷新");
            pb_refresh.setVisibility(INVISIBLE);
            iv_refresh.setVisibility(VISIBLE);
            headerView.setPadding(0,-measuredHeight,0,0);
        }
    }
    private RotateAnimation animationup;
    private RotateAnimation animationdown;

    private void initAnim(){
        animationup = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationup.setDuration(500);
        animationup.setFillAfter(true);

        animationdown = new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationdown.setDuration(500);
        animationdown.setFillAfter(true);

    }
    public void getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        tv_time.setText(time);


    }

    private void refreshState() {
        switch (curState){
            case PULL:
                tv_refresh.setText("下拉刷新");
                pb_refresh.clearAnimation();
                pb_refresh.setVisibility(INVISIBLE);
                iv_refresh.setVisibility(VISIBLE);
                iv_refresh.startAnimation(animationdown);
                break;
            case RELEASE:
                tv_refresh.setText("松开刷新");
                pb_refresh.setVisibility(INVISIBLE);
                iv_refresh.setVisibility(VISIBLE);
                iv_refresh.startAnimation(animationup);
                break;
            case REFRESHING:
                tv_refresh.setText("正在刷新");
                iv_refresh.clearAnimation();
                pb_refresh.setVisibility(VISIBLE);
                iv_refresh.setVisibility(INVISIBLE);
                break;
        }
    }
    private OnRefreshListener mListener;
    public void setOnRefreshListener(OnRefreshListener listener){
        mListener = listener;
    }
    public interface OnRefreshListener{
        void onRefresh();
        void loadMore();
    }
}
