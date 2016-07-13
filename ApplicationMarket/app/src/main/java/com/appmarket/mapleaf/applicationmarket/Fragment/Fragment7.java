package com.appmarket.mapleaf.applicationmarket.Fragment;

import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appmarket.mapleaf.applicationmarket.http.Protocol7;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;
import com.appmarket.mapleaf.applicationmarket.view.MyOrderView;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class Fragment7 extends BaseFragment{
    private ArrayList<String> data;
    @Override
    public View onCreateSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        MyOrderView myOrderView = new MyOrderView(UIUtils.getContext());
        for(int i=0;i<data.size();i++){
            String s = data.get(i);
            TextView textView = new TextView(UIUtils.getContext());
            textView.setTextSize(UIUtils.dp2px(50));
            textView.setText(s);
            myOrderView.addView(textView);
        }
        scrollView.addView(myOrderView);
        return scrollView;
    }

    @Override
    public int onLoad() {
        Protocol7 protocol = new Protocol7();
        data = protocol.getData(0);
        return check(data);
    }
}
