package com.appmarket.mapleaf.applicationmarket.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.appmarket.mapleaf.applicationmarket.Fragment.BaseFragment;
import com.appmarket.mapleaf.applicationmarket.Fragment.FragmentFactory;
import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;
import com.appmarket.mapleaf.applicationmarket.view.PagerTab;

public class MainActivity extends BaseActivity {

    private PagerTab pagerTab;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagerTab = (PagerTab) findViewById(R.id.pagerTab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        pagerTab.setViewPager(viewPager);
        pagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    class MyAdapter extends FragmentPagerAdapter{

        private String[] stringArray;
        public MyAdapter(FragmentManager fm) {
            super(fm);
            stringArray = UIUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringArray[position];
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return stringArray.length;
        }
    }
}
