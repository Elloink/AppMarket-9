package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.mapleaf.news.activity.MainActivity;
import com.example.mapleaf.news.bean.NewsData;
import com.example.mapleaf.news.fragment.Left_Fragment;
import com.example.mapleaf.news.utils.Global;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;


/**
 * Created by Mapleaf on 2016/7/2.
 */
public class pager2 extends BasePager {

    private ArrayList<BaseDetailPager> list ;
    protected NewsData newsData;


    public pager2(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        setSlidingMenuEnable(true);
        getDataFromServer();
    }

    public void getDataFromServer() {
        RequestParams params = new RequestParams(Global.CATEGORIES_URL);
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



    protected void parse(String arg0) {
        Gson gson = new Gson();
        newsData = gson.fromJson(arg0, NewsData.class);
        MainActivity mainActivity = (MainActivity) mActivity;
        Left_Fragment leftFragment = mainActivity.getLeftFragment();
        leftFragment.setNewsData(newsData);
        list = new ArrayList<>();
        list.add(new DetailPager1(mActivity,newsData.data.get(0).children));
        list.add(new DetailPager2(mActivity));
        list.add(new DetailPager3(mActivity));
        list.add(new DetailPager4(mActivity));
        setClickItemContent(0);
    }


    public void setClickItemContent(int position) {
        BaseDetailPager pager = list.get(position);
        fl_pager.removeAllViews();
        fl_pager.addView(pager.mRootView);
        NewsData.NewsMenuData menuData = newsData.data.get(position);
        tv_pager.setText(menuData.title);
       pager.initData();
    }
}
