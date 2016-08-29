package com.appmarket.mapleaf.appmarket.pager.pager;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.appmarket.mapleaf.appmarket.Global.GlobalConstants;
import com.appmarket.mapleaf.appmarket.activity.MainActivity;
import com.appmarket.mapleaf.appmarket.fragment.LeftMenuFragment;
import com.appmarket.mapleaf.appmarket.javabean.NewsData;
import com.appmarket.mapleaf.appmarket.pager.menudetailpager.BaseMenuDetailPager;
import com.appmarket.mapleaf.appmarket.pager.menudetailpager.InteractMenuDetailPager;
import com.appmarket.mapleaf.appmarket.pager.menudetailpager.NewsMenuDetailPager;
import com.appmarket.mapleaf.appmarket.pager.menudetailpager.PhotoMenuDetailPager;
import com.appmarket.mapleaf.appmarket.pager.menudetailpager.TopicMenuDetailPager;
import com.appmarket.mapleaf.appmarket.utils.CacheUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class NewsPager extends BasePager{
    private ArrayList<BaseMenuDetailPager> mDetailPagers;
    private NewsData data;
    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mActivity, GlobalConstants.CATEGORY_URL);
        if(TextUtils.isEmpty(cache)){
            getDataFromServer();
        }else{
            parseData(cache);
        }

    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(GlobalConstants.CATEGORY_URL);
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
                CacheUtils.setCache(mActivity,GlobalConstants.CATEGORY_URL,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        data = gson.fromJson(result, NewsData.class);
        MainActivity mainActivity = (MainActivity) mActivity;
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(MainActivity.TAG_LEFTMENUFRAGMENT);
        fragment.setLeftMenuData(data.data);
        mDetailPagers = new ArrayList<>();
        mDetailPagers.add(new NewsMenuDetailPager(mActivity,data.data.get(0).children));
        mDetailPagers.add(new TopicMenuDetailPager(mActivity));
        mDetailPagers.add(new PhotoMenuDetailPager(mActivity));
        mDetailPagers.add(new InteractMenuDetailPager(mActivity));
        setDetailPager(0);
    }

    public void setDetailPager(int position) {
        tv_title.setText(data.data.get(position).title);
        BaseMenuDetailPager detailPager = mDetailPagers.get(position);
        View view = detailPager.initView();
        fl_pager.removeAllViews();
        fl_pager.addView(view);
        detailPager.initData();
    }
}
