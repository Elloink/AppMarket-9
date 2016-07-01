package com.example.mapleaf.news.fragment;

import android.view.View;

import com.example.mapleaf.news.R;

/**
 * Created by Mapleaf on 2016/7/1.
 */
public class Content_Fragment extends BaseFragment{
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.content_fragment,null);
        return view;
    }
}
