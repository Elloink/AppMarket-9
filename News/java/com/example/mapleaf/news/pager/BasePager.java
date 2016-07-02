package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Mapleaf on 2016/7/2.
 */
public class BasePager {
    protected Activity mActivity;
    protected TextView tv_pager;
    protected FrameLayout fl_pager;
    protected ImageView iv_pager_menu;
    public View mView;
    protected SlidingMenu slidingMenu;
    public BasePager(Activity activity){
        mActivity=activity;
        initView();

    }
    private void initView(){
        mView =View.inflate(mActivity, R.layout.basepager_layout,null);
        tv_pager = (TextView) mView.findViewById(R.id.tv_pager_title);
        fl_pager = (FrameLayout) mView.findViewById(R.id.fl_pager);
        iv_pager_menu = (ImageView) mView.findViewById(R.id.iv_pager_menu);
    }
    public void initData(){
        MainActivity mainActivity = (MainActivity) mActivity;
        slidingMenu = mainActivity.getSlidingMenu();
        iv_pager_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(slidingMenu.isMenuShowing()){
                    slidingMenu.setSlidingEnabled(false);
                }else{
                    slidingMenu.setSlidingEnabled(true);
                    slidingMenu.showMenu();

                }
            }
        });
    }
    protected  void setSlidingMenuEnable(boolean flag){

        slidingMenu.setSlidingEnabled(flag);
    }
}
