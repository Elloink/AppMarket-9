package com.appmarket.mapleaf.applicationmarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

/**
 * Created by Mapleaf on 2016/7/9.
 */
public abstract class LoadingPage extends FrameLayout{
    private View loadingView;
    private View errView;
    private View emptyView;
    private View successView;

    public static final int STATER_UNDO =1;
    public static final int STATER_LOADING =2;
    public static final int STATER_ERROR =3;
    public static final int STATER_EMPTY =4;
    public static final int STATER_SUCCESS =5;
    private int curState =STATER_UNDO;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }
    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView() {
        if(loadingView==null){
            loadingView = UIUtils.inflate(R.layout.loading_layout);
            addView(loadingView);
        }
        if(errView==null){
            errView = UIUtils.inflate(R.layout.error_layout);
            Button btn_err_retry = (Button) errView.findViewById(R.id.btn_err_retry);
            btn_err_retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            });
            addView(errView);
        }
        if(emptyView==null){
            emptyView = UIUtils.inflate(R.layout.empty_layout);
            addView(emptyView);
        }
        showPage();
    }

    private void showPage() {
        loadingView.setVisibility((curState==STATER_UNDO||curState==STATER_LOADING)?VISIBLE:GONE);
        errView.setVisibility((curState==STATER_ERROR)?VISIBLE:GONE);
        emptyView.setVisibility((curState==STATER_EMPTY)?VISIBLE:GONE);

        if(successView==null&&curState==STATER_SUCCESS){
            successView=onCreateSuccessView();
            if(successView!=null){
                addView(successView);
            }
        }
        if(successView!=null){
            successView.setVisibility(curState==STATER_SUCCESS?VISIBLE:GONE);
        }
    }
    public void loadData(){
        if(curState!=STATER_LOADING){
            curState=STATER_LOADING;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    int resultState = onLoad();
                    curState=resultState;
                    UIUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            showPage();
                        }
                    });
                }
            }.start();
        }

    }
    public abstract int onLoad();
    public abstract View onCreateSuccessView();
}
