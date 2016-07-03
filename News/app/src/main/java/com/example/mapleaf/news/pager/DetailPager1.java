package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.bean.NewsData;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/3.
 */
public class DetailPager1 extends BaseDetailPager{
    private ViewPager viewPager;
    private ArrayList<TabDetailPager> list ;
    private ArrayList<NewsData.NewsMenuData.NewsTabData> tabDatas;
    public DetailPager1(Activity activity, ArrayList<NewsData.NewsMenuData.NewsTabData> children) {
        super(activity);
        tabDatas = children;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.detailpager1,null);
        viewPager= (ViewPager) view.findViewById(R.id.vp_detailpager1);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        for(int i = 0;i<tabDatas.size();i++){
            TabDetailPager pager = new TabDetailPager(mActivity,tabDatas.get(i));
            list.add(pager);
        }
        viewPager.setAdapter(new MydetailPagerAdapter());
    }




    class MydetailPagerAdapter extends PagerAdapter{

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
            container.addView(tabDetailPager.mRootView);
            tabDetailPager.initData();
            return tabDetailPager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
