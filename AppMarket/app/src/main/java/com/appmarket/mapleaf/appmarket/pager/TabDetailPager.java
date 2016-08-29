package com.appmarket.mapleaf.appmarket.pager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmarket.mapleaf.appmarket.Global.GlobalConstants;
import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.activity.WebViewActivity;
import com.appmarket.mapleaf.appmarket.javabean.NewsData;
import com.appmarket.mapleaf.appmarket.javabean.TabNewsData;
import com.appmarket.mapleaf.appmarket.pager.menudetailpager.BaseMenuDetailPager;
import com.appmarket.mapleaf.appmarket.utils.CacheUtils;
import com.appmarket.mapleaf.appmarket.utils.PrefUtils;
import com.appmarket.mapleaf.appmarket.view.CustomheadListView;
import com.appmarket.mapleaf.appmarket.view.DispatchViewPager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/8/23.
 */
public class TabDetailPager extends BaseMenuDetailPager{
    private NewsData.NewsMenuData.NewsTabData tabData;
    private ArrayList<TabNewsData.TopNewsDetail> topnews;
    private ArrayList<TabNewsData.TabNewsDetail> newsLists;
    private DispatchViewPager vp_tab_detail_image;
    private TextView tv_imageTitle;
    private MyNewsAdapter mAdapter;
    private CustomheadListView lv_tab_detail;
    private Handler mHandler;

    private ArrayList<String> imageList;
    public TabDetailPager(Activity activity, NewsData.NewsMenuData.NewsTabData children) {
        super(activity);
        tabData=children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager_layout,null);


        lv_tab_detail = (CustomheadListView) view.findViewById(R.id.lv_tab_detail);
        View headView = View.inflate(mActivity,R.layout.tab_detail_listview_headview,null);
        tv_imageTitle = (TextView) headView.findViewById(R.id.tv_imagetitle);
        vp_tab_detail_image = (DispatchViewPager) headView.findViewById(R.id.vp_tab_detail_image);
        lv_tab_detail.addHeaderView(headView);
        lv_tab_detail.setOnRefreshListener(new CustomheadListView.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                getDataFromServer();

            }

            @Override
            public void OnLoadMore() {
                if(mMoreURL==null){
                    Toast.makeText(mActivity, "亲，没有下一页了哦 = =", Toast.LENGTH_SHORT).show();
                    lv_tab_detail.loadMoreComplete();
                }else{
                    getMoreDataFromServer();
                }
            }
        });
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mActivity,GlobalConstants.SERVER_URL+tabData.url);
        if(cache==null){
            getDataFromServer();
        }else{
            parseData(cache);
        }
        lv_tab_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int headerViewsCount = lv_tab_detail.getHeaderViewsCount();
                int position = i-headerViewsCount;
                TabNewsData.TabNewsDetail news = newsLists.get(position);
                String id = news.id;
                String read_item = PrefUtils.getSharePreferenceString(mActivity, "read_item", "");
                if(!read_item.contains(id)){
                    PrefUtils.setSharePreferenceString(mActivity,"read_item",read_item+id);
                }
                TextView tv_title = (TextView) view.findViewById(R.id.tv_detail_listview_title);
                tv_title.setTextColor(Color.GRAY);

                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra("url",news.url);
                mActivity.startActivity(intent);
            }
        });
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(GlobalConstants.SERVER_URL+tabData.url);
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
                CacheUtils.setCache(mActivity,GlobalConstants.SERVER_URL+tabData.url,result);
                lv_tab_detail.refreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "亲，联网失败，请检查网络 = =！", Toast.LENGTH_LONG).show();
                lv_tab_detail.refreshComplete(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "亲，联网失败，请检查网络 = =！", Toast.LENGTH_LONG).show();
                lv_tab_detail.refreshComplete(false);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void getMoreDataFromServer(){
        RequestParams params = new RequestParams(mMoreURL);
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseMoreData(result);
                lv_tab_detail.loadMoreComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "亲，联网失败，请检查网络 = =！", Toast.LENGTH_LONG).show();
                lv_tab_detail.loadMoreComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "亲，联网失败，请检查网络 = =！", Toast.LENGTH_LONG).show();
                lv_tab_detail.loadMoreComplete();
            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void parseMoreData(String result){
        ArrayList<TabNewsData.TabNewsDetail> news = tabNewsData.data.news;
        newsLists.addAll(news);
        mAdapter.notifyDataSetChanged();
    }
    private String mMoreURL;
    private TabNewsData tabNewsData;
    private void parseData(String result) {
        Gson gson = new Gson();
        tabNewsData = gson.fromJson(result, TabNewsData.class);
        String moreUrl = tabNewsData.data.more;
        if(TextUtils.isEmpty(moreUrl)){
            mMoreURL = null;
        }else{
            mMoreURL = GlobalConstants.SERVER_URL + moreUrl ;
        }
        topnews = tabNewsData.data.topnews;
        imageList = new ArrayList<>();
        imageList.add("http://192.168.1.108:8080/zhbj/10007/1452327318UU91.jpg");
        imageList.add("http://192.168.1.108:8080/zhbj/10007/1452327318UU92.jpg");
        imageList.add("http://192.168.1.108:8080/zhbj/10007/1452327318UU93.jpg");
        imageList.add("http://192.168.1.108:8080/zhbj/10007/1452327318UU94.png");
        if(topnews!=null){
            vp_tab_detail_image.setAdapter(new MyTabPagerAdapter());
            vp_tab_detail_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tv_imageTitle.setText(topnews.get(position).title);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            tv_imageTitle.setText(topnews.get(0).title);
            if(mHandler==null){
                mHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        int currentItem = vp_tab_detail_image.getCurrentItem();
                        currentItem++;
                        if(currentItem>topnews.size()-1){
                            currentItem=0;
                        }
                        vp_tab_detail_image.setCurrentItem(currentItem);
                        mHandler.sendEmptyMessageDelayed(0,3000);
                    }
                };
                mHandler.sendEmptyMessageDelayed(0,3000);
            }
        }
        newsLists = tabNewsData.data.news;
        mAdapter = new MyNewsAdapter();
        lv_tab_detail.setAdapter(mAdapter);
        vp_tab_detail_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.sendEmptyMessageDelayed(0,3000);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0,3000);
                        break;
                }
                return false;
            }
        });
    }
    private ImageOptions options;
    class MyTabPagerAdapter extends PagerAdapter{
        public MyTabPagerAdapter(){
            options = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.topnews_item_default)
                    .setLoadingDrawableId(R.mipmap.topnews_item_default).build();
        }
        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            x.image().bind(view, imageList.get(position),options);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private class MyNewsAdapter extends BaseAdapter{

        public MyNewsAdapter(){
            options = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.topnews_item_default)
                    .setLoadingDrawableId(R.mipmap.topnews_item_default).build();
        }
        @Override
        public int getCount() {
            return newsLists.size();
        }

        @Override
        public TabNewsData.TabNewsDetail getItem(int i) {
            return newsLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view==null){
                view = View.inflate(mActivity,R.layout.tab_detail_listview_item,null);
                viewHolder= new ViewHolder();
                viewHolder.iv_icon = (ImageView) view.findViewById(R.id.iv_detail_listview_item);
                viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_detail_listview_title);
                viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_detail_listview_date);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            TabNewsData.TabNewsDetail item = getItem(i);
            viewHolder.tv_title.setText(item.title);
            viewHolder.tv_date.setText(item.pubdate);
            String read_item = PrefUtils.getSharePreferenceString(mActivity, "read_item", "");
            if(read_item.contains(item.id)){
                viewHolder.tv_title.setTextColor(Color.GRAY);
            }else{
                viewHolder.tv_title.setTextColor(Color.BLACK);
            }
            x.image().bind(viewHolder.iv_icon,"http://192.168.1.108:8080/zhbj/10007/14800329488K7F.jpg",options);
            return view;
        }
    }
    private class ViewHolder{
        private ImageView iv_icon;
        private TextView tv_title;
        private TextView tv_date;




    }
}
