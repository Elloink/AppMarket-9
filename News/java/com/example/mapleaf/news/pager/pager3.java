package com.example.mapleaf.news.pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;


/**
 * Created by Mapleaf on 2016/7/2.
 */
public class pager3 extends BasePager {
    public pager3(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        tv_pager.setText("服务");
        TextView textView = new TextView(mActivity);
        textView.setText("服务");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(40);
        fl_pager.addView(textView);
        setSlidingMenuEnable(true);

    }
}
