package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.activity.MainActivity;
import com.example.mapleaf.news.bean.NewsData;
import com.example.mapleaf.news.utils.DetailViewPager;
import com.example.mapleaf.news.utils.TopNewsViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/3.
 */
public class DetailPager1 extends BaseDetailPager{
    private ViewPager viewPager;
    private ArrayList<TabDetailPager> list ;
    private ArrayList<NewsData.NewsMenuData.NewsTabData> tabDatas;
    private MyScrollIndicatorView scrollIndicatorView;
    private IndicatorViewPager indicatorViewPager;

    public DetailPager1(Activity activity, ArrayList<NewsData.NewsMenuData.NewsTabData> children) {
        super(activity);
        tabDatas = children;
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.detailpager1,null);
        viewPager= (ViewPager) view.findViewById(R.id.vp_detailpager1);
        scrollIndicatorView = (MyScrollIndicatorView) view.findViewById(R.id.indicator);
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
        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(0xFFFF0000, Color.GRAY).setSize(selectSize, unSelectSize));

        scrollIndicatorView.setScrollBar(new ColorBar(mActivity, 0xFFFF0000, 4));

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter());
        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                MainActivity mainActivity = (MainActivity) mActivity;
                SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
                if(currentItem==0){
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else{
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }
        });
    }









    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        private String[] versions = {"Cupcake", "Donut", "Éclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lolipop", "Marshmallow"};
        private String[] names = {"纸杯蛋糕", "甜甜圈", "闪电泡芙", "冻酸奶", "姜饼", "蜂巢", "冰激凌三明治", "果冻豆", "奇巧巧克力棒", "棒棒糖", "棉花糖"};

        @Override
        public int getCount() {
            return tabDatas.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = mActivity.getLayoutInflater().inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabDatas.get(position).title);

            int witdh = getTextWidth(textView);
            int padding = DisplayUtil.dipToPix(mActivity, 8);
            //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
            //1.3f是根据上面字体大小变化的倍数1.3f设置
            textView.setWidth((int) (witdh * 1.3f) + padding);

            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            TabDetailPager pager = list.get(position);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_UNCHANGED;
        }

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }

    }

}
