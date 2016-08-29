package com.appmarket.mapleaf.appmarket.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.activity.MainActivity;
import com.appmarket.mapleaf.appmarket.pager.pager.BasePager;
import com.appmarket.mapleaf.appmarket.pager.pager.GovPager;
import com.appmarket.mapleaf.appmarket.pager.pager.HomePager;
import com.appmarket.mapleaf.appmarket.pager.pager.NewsPager;
import com.appmarket.mapleaf.appmarket.pager.pager.SettingPager;
import com.appmarket.mapleaf.appmarket.pager.pager.SmartServicePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class MainFragment extends BaseFragment{
    private ViewPager vp_main;
    private ArrayList<BasePager> mPager ;
    private RadioGroup rg_bottom;
    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_main,null);
        vp_main = (ViewPager) view.findViewById(R.id.vp_main);
        rg_bottom = (RadioGroup) view.findViewById(R.id.rg_bottom);
        return view;
    }

    @Override
    protected void initData() {
        mPager = new ArrayList<>();
        mPager.add(new HomePager(mActivity));
        mPager.add(new NewsPager(mActivity));
        mPager.add(new SmartServicePager(mActivity));
        mPager.add(new GovPager(mActivity));
        mPager.add(new SettingPager(mActivity));
        vp_main.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mPager.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                BasePager pager = mPager.get(position);
                //pager.initData();
                View view = pager.mRootView;
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home:
                        vp_main.setCurrentItem(0,false);
                        break;
                    case R.id.rb_news:
                        vp_main.setCurrentItem(1,false);
                        break;
                    case R.id.rb_smart:
                        vp_main.setCurrentItem(2,false);
                        break;
                    case R.id.rb_gov:
                        vp_main.setCurrentItem(3,false);
                        break;
                    case R.id.rb_setting:
                        vp_main.setCurrentItem(4,false);
                        break;
                }
            }
        });
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mPager.get(position);
                pager.initData();
                if(position==1){
                    setLeftMenuOn(true);
                }else{
                    setLeftMenuOn(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.get(0).initData();
        setLeftMenuOn(false);
    }
    private void setLeftMenuOn(boolean state){
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if(state){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
    public NewsPager getNewsPager(){
        return (NewsPager) mPager.get(1);
    }
}
