package com.appmarket.mapleaf.applicationmarket.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

/**
 * Created by Mapleaf on 2016/7/9.
 */
public class MoreHolder extends MyBaseHolder<Integer>{

    public static final int STATE_MORE_MORE =1;
    public static final int STATE_MORE_ERROR =2;
    public static final int STATE_MORE_NONE =3;

    private TextView tv_load_err;
    private LinearLayout ll_loading;

    public MoreHolder(boolean hasMore) {
        if(hasMore){
            setData(STATE_MORE_MORE);
        }else{
            setData(STATE_MORE_NONE);
        }
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        tv_load_err = (TextView) view.findViewById(R.id.tv_load_err);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data){
            case STATE_MORE_MORE:
                ll_loading.setVisibility(View.VISIBLE);
                tv_load_err.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                ll_loading.setVisibility(View.GONE);
                tv_load_err.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                ll_loading.setVisibility(View.GONE);
                tv_load_err.setVisibility(View.VISIBLE);
                break;
        }
    }
}
