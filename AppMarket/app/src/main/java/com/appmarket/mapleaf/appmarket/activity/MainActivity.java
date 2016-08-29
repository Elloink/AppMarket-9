package com.appmarket.mapleaf.appmarket.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.fragment.LeftMenuFragment;
import com.appmarket.mapleaf.appmarket.fragment.MainFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    public static final String TAG_MAINFRAGMENT = "TAG_MainFragment";
    public static final String TAG_LEFTMENUFRAGMENT = "TAG_LeftMenuFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.layout_slidingmenu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(400);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_main,new MainFragment(),TAG_MAINFRAGMENT);
        transaction.replace(R.id.fl_leftmenu,new LeftMenuFragment(),TAG_LEFTMENUFRAGMENT);
        transaction.commit();
    }

}
