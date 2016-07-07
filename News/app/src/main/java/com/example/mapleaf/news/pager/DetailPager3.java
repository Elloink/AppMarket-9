package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapleaf.news.R;
import com.example.mapleaf.news.bean.PhotoData;
import com.example.mapleaf.news.utils.Global;
import com.example.mapleaf.news.utils.MyBitmapUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/3.
 */
public class DetailPager3 extends BaseDetailPager{
    public DetailPager3(Activity activity) {
        super(activity);
    }

    private ListView lv_phonemenu;
    private GridView gv_phonemenu;
    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.phone_menu,null);
        lv_phonemenu = (ListView) view.findViewById(R.id.lv_phonemenu);
        gv_phonemenu = (GridView) view.findViewById(R.id.gv_phonemenu);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        RequestParams params = new RequestParams(Global.CATEGORIES_PHONE_URL);
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

    private ArrayList<PhotoData.PhotoInfo> newsList;
    private mlistviewAdapter myAdapter;
    private void parse(String arg0) {
        Gson gson = new Gson();
        PhotoData data = gson.fromJson(arg0, PhotoData.class);
        newsList = data.data.news;
        if(newsList!=null){
            myAdapter = new mlistviewAdapter();
            lv_phonemenu.setAdapter(myAdapter);
            gv_phonemenu.setAdapter(myAdapter);
        }
    }
    //private ImageOptions options;
    private class mlistviewAdapter extends BaseAdapter{
        private MyBitmapUtil bitmapUtil;
        private mlistviewAdapter(){
           // options = new ImageOptions.Builder().setFailureDrawableId(R.drawable.topnews_item_default)
              //      .setLoadingDrawableId(R.drawable.topnews_item_default).build();
            bitmapUtil = new MyBitmapUtil();
        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public PhotoData.PhotoInfo getItem(int i) {
            return newsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view==null){
                view = View.inflate(mActivity,R.layout.phone_menu_item,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_phonemenu = (TextView) view.findViewById(R.id.tv_phonemenu);
                viewHolder.iv_phonemenu = (ImageView) view.findViewById(R.id.iv_phonemenu);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            PhotoData.PhotoInfo item = getItem(i);
            viewHolder.tv_phonemenu.setText(item.title);
            //x.image().bind(viewHolder.iv_phonemenu, item.listimage,options);
            bitmapUtil.display(viewHolder.iv_phonemenu, item.listimage);
            return view;
        }
    }
     private static class ViewHolder{
        private TextView tv_phonemenu;
        private ImageView iv_phonemenu;
    }
}
