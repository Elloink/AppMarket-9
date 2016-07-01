package com.example.mapleaf.news.fragment;

import android.view.View;

import com.example.mapleaf.news.R;

/**
 * Created by Mapleaf on 2016/7/1.
 */
public class Left_Fragment extends BaseFragment{
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.left_fragment_layout,null);
        return view;
    }
}
