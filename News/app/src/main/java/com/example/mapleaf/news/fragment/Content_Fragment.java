package com.example.mapleaf.news.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.pager.BasePager;
import com.example.mapleaf.news.pager.pager1;
import com.example.mapleaf.news.pager.pager2;
import com.example.mapleaf.news.pager.pager3;
import com.example.mapleaf.news.pager.pager4;
import com.example.mapleaf.news.pager.pager5;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mapleaf on 2016/7/1.
 */
public class Content_Fragment extends BaseFragment{

    private RadioGroup rg_content;
    private ViewPager viewPager;
    private List<BasePager> list;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.content_fragment,null);
        viewPager = (ViewPager) view.findViewById(R.id.vp_content);
        rg_content = (RadioGroup) view.findViewById(R.id.rg_content);
        return view;
    }

    @Override
    public void initDate() {
        super.initDate();
        rg_content.check(R.id.rb_content_1);
        rg_content.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_content_1:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_content_2:
                        viewPager.setCurrentItem(1,false);

                        break;
                    case R.id.rb_content_3:
                        viewPager.setCurrentItem(2,false);

                        break;
                    case R.id.rb_content_4:
                        viewPager.setCurrentItem(3,false);

                        break;
                    case R.id.rb_content_5:
                        viewPager.setCurrentItem(4,false);

                        break;
                }
            }
        });
        list = new ArrayList<>();
        pager1 pager_1 = new pager1(getActivity());
        pager2 pager_2 = new pager2(getActivity());
        pager3 pager_3 = new pager3(getActivity());
        pager4 pager_4 = new pager4(getActivity());
        pager5 pager_5 = new pager5(getActivity());
        list.add(pager_1);
        list.add(pager_2);
        list.add(pager_3);
        list.add(pager_4);
        list.add(pager_5);
        viewPager.setAdapter(new PagerAdapter() {
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
                BasePager pager = list.get(position);
                //pager.initData();
                container.addView(pager.mView);
                return pager.mView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = list.get(position);
                pager.initData();


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        list.get(0).initData();
    }

    protected pager2 getPager2(){
        return (pager2) list.get(1);
    }
}
