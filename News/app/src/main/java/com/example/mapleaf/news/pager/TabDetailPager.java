package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.activity.NewsActivity;
import com.example.mapleaf.news.bean.NewsData;
import com.example.mapleaf.news.bean.TabNewsData;
import com.example.mapleaf.news.utils.Global;
import com.example.mapleaf.news.utils.PrefUtil;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ids = PrefUtil.getStringPref(mActivity, "ids", "");
                String nowId = news.get(i-2).id;
                if(!ids.contains(nowId)){
                    ids = ids + nowId +",";
                    PrefUtil.setStringPref(mActivity,"ids",ids);
                }
                TextView tv = (TextView) view.findViewById(R.id.tv_listview_item);
                tv.setTextColor(Color.RED);
                Intent intent = new Intent();
                intent.setClass(mActivity, NewsActivity.class);
                intent.putExtra("url",news.get(i-2).url);
                mActivity.startActivity(intent);
            }
        });
        listView.setOnRefreshListener(new RefleshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void loadMore() {
                if(moreUrl!=null){
                    getMoreFromServer();
                }else{
                    Toast.makeText(mActivity,"已经是最后一页",Toast.LENGTH_SHORT).show();
                    listView.onRefreshFinish();
                }
            }
        });
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

    private void getMoreFromServer(){
        RequestParams params = new RequestParams(moreUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {
                listView.onRefreshFinish();
            }
            @Override
            public void onError(Throwable arg0, boolean arg1) {
                listView.onRefreshFinish();
            }
            @Override
            public void onFinished() {
                listView.onRefreshFinish();
            }
            @Override
            public void onSuccess(String arg0) {

                Gson gson = new Gson();
                TabNewsData moreNewsData = gson.fromJson(arg0, TabNewsData.class);
                ArrayList<TabNewsData.TabNewsDetail> moreNews = moreNewsData.data.news;
                news.addAll(moreNews);
                myAdapter.notifyDataSetChanged();
                listView.onRefreshFinish();


            }
        });
    }
    private void getDataFromServer() {
        RequestParams params = new RequestParams(mUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(Callback.CancelledException arg0) {
                listView.onRefreshFinish();
            }
            @Override
            public void onError(Throwable arg0, boolean arg1) {
                listView.onRefreshFinish();
            }
            @Override
            public void onFinished() {
                listView.onRefreshFinish();
            }
            @Override
            public void onSuccess(String arg0) {

                parse(arg0);
                listView.onRefreshFinish();
                listView.getCurrentTime();
            }
        });
    }
    private String moreUrl;
    private ArrayList<TabNewsData.TabNewsDetail> news;
    private void parse(String arg0) {
        Gson gson = new Gson();
        tabNewsData = gson.fromJson(arg0, TabNewsData.class);
        String more = tabNewsData.data.more;
        if(more!=null||more!=""){
            moreUrl=Global.SERVER_URL+more;
        }else{
            moreUrl=null;
        }

        vp_tabdetail.setAdapter(new MyTabDetailPagerAdapter());
        news = tabNewsData.data.news;
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        if(handler==null){
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    int currentItem = vp_tabdetail.getCurrentItem();
                    if(currentItem==tabNewsData.data.topnews.size()-1){
                        currentItem=0;
                    }else{
                        currentItem++;
                    }
                    vp_tabdetail.setCurrentItem(currentItem);
                    handler.sendEmptyMessageDelayed(0,2000);

                }
            };
        }
        handler.sendEmptyMessageDelayed(0,2000);

    }
    private Handler handler ;
    private MyAdapter myAdapter;
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
    private class MyAdapter extends BaseAdapter{
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
            View tv_news = View.inflate(mActivity,R.layout.listview_item,null);
            TextView tv= (TextView) tv_news.findViewById(R.id.tv_listview_item);

            tv.setText(news.get(i).title);
            return tv_news;
        }
    }
}
