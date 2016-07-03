package com.example.mapleaf.news.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.fragment.Content_Fragment;
import com.example.mapleaf.news.fragment.Left_Fragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
    private static final String FRAGMENT_CONTENT = "fragment_content";
    private static final String FRAGMENT_LEFT = "fragment_left";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_main_layout);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(600);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content,new Content_Fragment(),FRAGMENT_CONTENT);
        fragmentTransaction.replace(R.id.fl_leftMenu,new Left_Fragment(),FRAGMENT_LEFT);
        fragmentTransaction.commit();

    }
    public Left_Fragment getLeftFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Left_Fragment fragment = (Left_Fragment) supportFragmentManager.findFragmentByTag(FRAGMENT_LEFT);
        return fragment;
    }
    public Content_Fragment getContentFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Content_Fragment fragment = (Content_Fragment) supportFragmentManager.findFragmentByTag(FRAGMENT_CONTENT);
        return fragment;
    }
}
