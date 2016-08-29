package com.appmarket.mapleaf.appmarket.pager.menudetailpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.activity.MainActivity;
import com.appmarket.mapleaf.appmarket.javabean.NewsData;
import com.appmarket.mapleaf.appmarket.pager.TabDetailPager;
import com.appmarket.mapleaf.appmarket.utils.MyTabPageIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/8/23.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager{
    private ViewPager vp_tab_detail;
    private ArrayList<NewsData.NewsMenuData.NewsTabData> children;
    private MyTabPageIndicator mIndicator;
    private ArrayList<TabDetailPager> list;
    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsMenuData.NewsTabData> children) {
        super(activity);
        this.children=children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail,null);
        vp_tab_detail = (ViewPager) view.findViewById(R.id.vp_tab_detail);
        mIndicator = (MyTabPageIndicator) view.findViewById(R.id.tabPageIndicator);
        return view;
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        for(int i=0;i<children.size();i++){
            list.add(new TabDetailPager(mActivity,children.get(i)));
        }
        vp_tab_detail.setAdapter(new PagerAdapter() {
            @Override
            public CharSequence getPageTitle(int position) {
                NewsData.NewsMenuData.NewsTabData newsTabData = children.get(position);
                return newsTabData.title;
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TabDetailPager tabDetailPager = list.get(position);
                View view = tabDetailPager.initView();
                container.addView(view);
                tabDetailPager.initData();
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mIndicator.setViewPager(vp_tab_detail);
        vp_tab_detail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity mainActivity = (MainActivity) mActivity;
                SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
                if(position==0){
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else{
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
