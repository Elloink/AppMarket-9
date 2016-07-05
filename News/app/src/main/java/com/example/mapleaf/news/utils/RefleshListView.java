package com.example.mapleaf.news.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.example.mapleaf.news.R;

/**
 * Created by Mapleaf on 2016/7/5.
 */
public class RefleshListView extends ListView{
    public RefleshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefleshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefleshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }
    private void initHeaderView(){
        View headerView = View.inflate(getContext(), R.layout.refresh_header,null);
        addHeaderView(headerView);
        headerView.measure(0,0);
//        int measuredHeight = headerView.getMeasuredHeight();
//        headerView.setPadding(0,-measuredHeight,0,0);
    }
}
