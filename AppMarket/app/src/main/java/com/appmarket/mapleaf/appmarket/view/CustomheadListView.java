package com.appmarket.mapleaf.appmarket.view;

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

import com.appmarket.mapleaf.appmarket.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mapleaf on 2016/8/25.
 */
public class CustomheadListView extends ListView{
    private int headerViewHeight;
    private View headerView;
    private static final int STATE_PULL_REFRESH = 1;
    private static final int STATE_RELEASE_REFRESH = 2;
    private static final int STATE_REFRESHING =3;
    private int currentState = STATE_PULL_REFRESH;
    private TextView tv_title;
    private TextView tv_time;
    private ImageView iv_arr;
    private ProgressBar pb_refresh;
    private RotateAnimation animUp;
    private RotateAnimation animDown;
    public CustomheadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public CustomheadListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }

    public CustomheadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.refresh_header,null);
        tv_title = (TextView) headerView.findViewById(R.id.tv_refresh);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);
        iv_arr = (ImageView) headerView.findViewById(R.id.iv_refresh_arr);
        pb_refresh = (ProgressBar) headerView.findViewById(R.id.pb_refresh);
        addHeaderView(headerView);
        headerView.measure(0,0);
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0,-headerViewHeight,0,0);
        initAnimation();
        setCurrentTime();
    }
    int startY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(startY==-1){//如果按住的是头条新闻的viewpager进行下拉，ACTION_DOWN会被viewpager消费掉，导致startY没有赋值，此处重新赋值
                    startY = (int) ev.getY();
                }
                if(currentState==STATE_REFRESHING){
                    break;
                }
                int endY = (int) ev.getY();
                int dY = endY - startY;
                int firstVisiblePosition = getFirstVisiblePosition();
                if(dY>0 && firstVisiblePosition==0){
                    int padding = dY-headerViewHeight;
                    headerView.setPadding(0,padding,0,0);
                    if(padding>0&&currentState!=STATE_RELEASE_REFRESH){
                        currentState=STATE_RELEASE_REFRESH;
                        refreshHeaderViewState();
                    }else if(padding<0&&currentState!=STATE_PULL_REFRESH){
                        currentState=STATE_PULL_REFRESH;
                        refreshHeaderViewState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if(currentState==STATE_PULL_REFRESH){
                    headerView.setPadding(0,-headerViewHeight,0,0);
                }else if(currentState==STATE_RELEASE_REFRESH){
                    headerView.setPadding(0,0,0,0);
                    currentState=STATE_REFRESHING;
                    refreshHeaderViewState();
                    if(mListener!=null)
                        mListener.OnRefresh();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshHeaderViewState() {
        switch (currentState){
            case STATE_PULL_REFRESH:
                tv_title.setText("下拉刷新");
                iv_arr.setVisibility(VISIBLE);
                pb_refresh.setVisibility(INVISIBLE);
                iv_arr.startAnimation(animDown);
                break;
            case STATE_RELEASE_REFRESH:
                tv_title.setText("松开刷新");
                iv_arr.setVisibility(VISIBLE);
                pb_refresh.setVisibility(INVISIBLE);
                iv_arr.startAnimation(animUp);
                break;
            case STATE_REFRESHING:
                tv_title.setText("正在刷新...");
                iv_arr.clearAnimation();
                pb_refresh.setVisibility(VISIBLE);
                iv_arr.setVisibility(INVISIBLE);
                break;
        }
    }
    private void initAnimation(){
        animUp = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animUp.setDuration(300);
        animUp.setFillAfter(true);

        animDown = new RotateAnimation(180,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animDown.setDuration(300);
        animDown.setFillAfter(true);
    }

    public void loadMoreComplete() {
        footerView.setPadding(0,-footerViewHeight,0,0);
        isLoadingMore=false;
    }

    public interface OnRefreshListener{
        void OnRefresh();
        void OnLoadMore();
    }
    public void setOnRefreshListener(OnRefreshListener listener){
        mListener = listener;
    }
    private OnRefreshListener mListener;
    public void refreshComplete(boolean flag){
        headerView.setPadding(0,-headerViewHeight,0,0);
        currentState = STATE_PULL_REFRESH;
        refreshHeaderViewState();
        if(flag){
            setCurrentTime();
        }
    }
    private void setCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        tv_time.setText(time);
    }
    private int footerViewHeight;
    private boolean isLoadingMore;
    private View footerView;
    private void initFooterView(){
        footerView = View.inflate(getContext(),R.layout.loadmore,null);
        addFooterView(footerView);
        footerView.measure(0,0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0,-footerViewHeight,0,0);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i==SCROLL_STATE_IDLE&&!isLoadingMore){
                    int count = getCount();
                    if(getLastVisiblePosition()==count-1){
                        footerView.setPadding(0,0,0,0);
                        isLoadingMore=true;
                        setSelection(count-1);
                        if(mListener!=null){
                            mListener.OnLoadMore();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

}
