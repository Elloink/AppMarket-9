package com.appmarket.mapleaf.appmarket.pager.pager;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class BasePager {
    public Activity mActivity;
    public View mRootView;
    public FrameLayout fl_pager;
    public ImageView iv_title_menu;
    public TextView tv_title;
    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager,null);
        fl_pager = (FrameLayout) view.findViewById(R.id.fl_pager);
        iv_title_menu = (ImageView) view.findViewById(R.id.iv_menu);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_title_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        return view;
    }
    public void initData(){

    }
    public void toggle(){
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.toggle();
    }
}
