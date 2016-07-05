package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.bean.NewsData;
import com.example.mapleaf.news.bean.TabNewsData;
import com.example.mapleaf.news.utils.Global;
import com.example.mapleaf.news.utils.RefleshListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/3.
 */
public class TabDetailPager extends BaseDetailPager{
    private NewsData.NewsMenuData.NewsTabData tabData;
    private TabNewsData tabNewsData;
    private View view;
    private String mUrl;
    private RefleshListView listView;
    private TextView textView;
    private ViewPager vp_tabdetail;
    public TabDetailPager(Activity activity, NewsData.NewsMenuData.NewsTabData newsTabData) {
        super(activity);
        tabData=newsTabData;
        mUrl = Global.SERVER_URL+tabData.url;
    }

    @Override
    protected View initView() {
        view = View.inflate(mActivity, R.layout.tabdetailpager,null);
        View headerView = View.inflate(mActivity,R.layout.tab_news_header,null);
        textView = (TextView) view.findViewById(R.id.tv_tabdetail);
        listView = (RefleshListView) view.findViewById(R.id.lv_tabdetail);
        listView.addHeaderView(headerView);
        vp_tabdetail = (ViewPager) view.findViewById(R.id.vp_tabdetail);

        vp_tabdetail.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                TabNewsData.TopNewsDetail topNewsDetail = tabNewsData.data.topnews.get(position);
//                textView.setText(topNewsDetail.title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        getDataFromServer();
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(mUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {
            }
            @Override
            public void onError(Throwable arg0, boolean arg1) {
            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onSuccess(String arg0) {

                parse(arg0);
            }
        });
    }
    private ArrayList<TabNewsData.TabNewsDetail> news;
    private void parse(String arg0) {
        Gson gson = new Gson();
        tabNewsData = gson.fromJson(arg0, TabNewsData.class);
        System.out.println("++++++++++++++++++++++++"+tabNewsData.data.topnews.toString());
        vp_tabdetail.setAdapter(new MyTabDetailPagerAdapter());
        news = tabNewsData.data.news;
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return news.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView tv_news = new TextView(mActivity);
                tv_news.setText(news.get(i).title);
                return tv_news;
            }
        });

    }
    private ImageOptions options;
    class MyTabDetailPagerAdapter extends PagerAdapter{

        public MyTabDetailPagerAdapter(){
            options = new ImageOptions.Builder().setFailureDrawableId(R.drawable.topnews_item_default)
                    .setLoadingDrawableId(R.drawable.topnews_item_default).build();
        }
        @Override
        public int getCount() {
            return tabNewsData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            x.image().bind(imageView, tabNewsData.data.topnews.get(position).topimage,options);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}
