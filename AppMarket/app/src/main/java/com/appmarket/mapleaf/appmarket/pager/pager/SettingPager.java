package com.appmarket.mapleaf.appmarket.pager.pager;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mapleaf on 2016/8/22.
 */
public class SettingPager extends BasePager{
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tv_title.setText("设置");
        TextView tv = new TextView(mActivity);
        tv.setText("设置content");
        fl_pager.addView(tv);
        iv_title_menu.setVisibility(View.GONE);
    }
}
