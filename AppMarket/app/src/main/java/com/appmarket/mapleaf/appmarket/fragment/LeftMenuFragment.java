package com.appmarket.mapleaf.appmarket.fragment;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appmarket.mapleaf.appmarket.R;
import com.appmarket.mapleaf.appmarket.activity.MainActivity;
import com.appmarket.mapleaf.appmarket.javabean.NewsData;
import com.appmarket.mapleaf.appmarket.pager.pager.NewsPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class LeftMenuFragment extends BaseFragment{
    private ListView lv_leftMenu;
    private int mCurrentItem;
    private MyBaseAdapter mAdapter;
    private ArrayList<NewsData.NewsMenuData> data;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_leftmenu,null);
        lv_leftMenu = (ListView) view.findViewById(R.id.lv_leftmenu);
        return view;
    }

    @Override
    protected void initData() {
        lv_leftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentItem = i;
                mAdapter.notifyDataSetChanged();
                toggle();
                setMenuDetailPager(i);
            }
        });
    }

    private void setMenuDetailPager(int position) {
        MainActivity mainActivity = (MainActivity) mActivity;
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) fm.findFragmentByTag(MainActivity.TAG_MAINFRAGMENT);
        NewsPager newsPager = mainFragment.getNewsPager();
        newsPager.setDetailPager(position);
    }

    public void setLeftMenuData(final ArrayList<NewsData.NewsMenuData> data) {
        mCurrentItem=0;
        this.data=data;
        mAdapter = new MyBaseAdapter();
        lv_leftMenu.setAdapter(mAdapter);
    }
    public class MyBaseAdapter extends BaseAdapter{

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public NewsData.NewsMenuData getItem(int i) {
                return data.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View item = View.inflate(mActivity,R.layout.leftmenu_item,null);
                TextView tv_leftmenu_title = (TextView) item.findViewById(R.id.tv_leftmenu_title);
                ImageView iv_leftmenu_arr = (ImageView) item.findViewById(R.id.iv_leftmenu_arr);
                tv_leftmenu_title.setText(getItem(i).title);
                if(mCurrentItem==i){
                    tv_leftmenu_title.setEnabled(true);
                    iv_leftmenu_arr.setEnabled(true);
                }else{
                    tv_leftmenu_title.setEnabled(false);
                    iv_leftmenu_arr.setEnabled(false);
                }
                return item;
            }

    }
    public void toggle(){
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.toggle();
    }
}
