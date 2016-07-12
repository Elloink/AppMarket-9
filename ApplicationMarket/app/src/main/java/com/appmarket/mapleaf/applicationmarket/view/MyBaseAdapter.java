package com.appmarket.mapleaf.applicationmarket.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

import org.xutils.image.ImageOptions;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/9.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{
    private static final int ITEM_NORMOL = 0;
    private static final int ITEM_MORE = 1;
    private ArrayList<T> list;
    protected ImageOptions options;
    public MyBaseAdapter(ArrayList<T> list){
        this.list = list;
        options = new ImageOptions.Builder().setFailureDrawableId(R.drawable.ic_default)
                .setLoadingDrawableId(R.drawable.ic_default).build();
    }
    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public T getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getCount()-1){
            return ITEM_MORE;
        }else{
            return getInnerType();
        }
    }
    public int getInnerType(){
        return ITEM_NORMOL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyBaseHolder holder;
        if(view ==null){
            if(getItemViewType(i)==ITEM_NORMOL){
                holder = getHolder();
            }else{
                holder = new MoreHolder(hasMore());
            }
        }else{
            holder = (MyBaseHolder) view.getTag();
        }
        if(getItemViewType(i)==ITEM_NORMOL){
            holder.setData(getItem(i));
        }else{
            MoreHolder moreHolder = (MoreHolder) holder;
            Integer data = moreHolder.getData();
            if(data==MoreHolder.STATE_MORE_MORE){
                requestMore(moreHolder);
            }
        }
        return holder.getRootView();
    }
    public abstract MyBaseHolder getHolder();
    public boolean hasMore(){
        return true;
    }
    private boolean isLoadingMore = false;
    public void requestMore(final MoreHolder holder){
        if(!isLoadingMore){
            isLoadingMore=true;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    final ArrayList<T> ts = loadMore();
                    UIUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if(ts!=null){
                                if(ts.size()<20){
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                }else{
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                list.addAll(ts);
                                MyBaseAdapter.this.notifyDataSetChanged();

                            }else{
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadingMore=false;
                        }
                    });

                }
            }.start();
        }

    }
    public abstract ArrayList<T> loadMore();
    public int getListsize(){
        return list.size();
    }
}
