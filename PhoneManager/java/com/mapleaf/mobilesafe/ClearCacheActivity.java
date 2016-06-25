package com.mapleaf.mobilesafe;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.mapleaf.fragment.Cache_fragment;
import com.mapleaf.fragment.SDcard_fragment;

public class ClearCacheActivity extends FragmentActivity {


    private Cache_fragment cache_fragment;
    private SDcard_fragment sDcard_fragment;
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);
        cache_fragment = new Cache_fragment();
        sDcard_fragment = new SDcard_fragment();
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_clearcache,cache_fragment);
        fragmentTransaction.add(R.id.fl_clearcache,sDcard_fragment);
        fragmentTransaction.hide(sDcard_fragment);
        fragmentTransaction.commit();

    }
    public void clearCache(View v){
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.hide(sDcard_fragment);
        fragmentTransaction.show(cache_fragment);
        fragmentTransaction.commit();
    }
    public void sdclear(View view){
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.hide(cache_fragment);
        fragmentTransaction.show(sDcard_fragment);
        fragmentTransaction.commit();
    }
}
