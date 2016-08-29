package com.appmarket.mapleaf.appmarket.pager.menudetailpager;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mapleaf on 2016/8/23.
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager{
    public PhotoMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mActivity);
        tv.setText("detail photo");
        return tv;
    }
}
