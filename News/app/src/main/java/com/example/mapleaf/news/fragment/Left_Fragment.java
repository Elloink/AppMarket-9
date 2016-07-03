package com.example.mapleaf.news.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.activity.MainActivity;
import com.example.mapleaf.news.bean.NewsData;
import com.example.mapleaf.news.pager.pager2;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/1.
 */
public class Left_Fragment extends BaseFragment{
    private ListView lv_leftfragment;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.left_fragment_layout,null);
        lv_leftfragment = (ListView) view.findViewById(R.id.lv_leftfragment);
        return view;
    }

    private int curClickedItem;
    @Override
    public void initDate() {
        super.initDate();
        lv_leftfragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                curClickedItem = i;
                adapter.notifyDataSetChanged();
                setItemClickContent(i);
                MainActivity mainActivity = (MainActivity) activity;
                SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
                slidingMenu.toggle();
            }
        });
    }

    private void setItemClickContent(int i) {
        MainActivity mainActivity = (MainActivity) activity;
        Content_Fragment contentFragment = mainActivity.getContentFragment();
        pager2 pager2 = contentFragment.getPager2();
        pager2.setClickItemContent(i);
    }

    private ArrayList<NewsData.NewsMenuData> menuData;
    private MyLeftFragmentAdapter adapter;
    public void setNewsData(NewsData newsData) {

        menuData = newsData.data;
        adapter = new MyLeftFragmentAdapter();
        lv_leftfragment.setAdapter(adapter);
    }

    public class MyLeftFragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menuData.size();
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
            View item = View.inflate(getContext(),R.layout.leftfragment_item,null);
            TextView tv = (TextView) item.findViewById(R.id.tv_leftfragment_item);
            tv.setText(menuData.get(i).title);
            if(curClickedItem==i){
                tv.setEnabled(true);
            }else{
                tv.setEnabled(false);
            }
            return item;
        }
    }
}


