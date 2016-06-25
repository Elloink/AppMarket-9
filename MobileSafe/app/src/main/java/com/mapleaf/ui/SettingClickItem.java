package com.mapleaf.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mapleaf.mobilesafe.R;


/**
 * Created by Mapleaf on 2016/5/21.
 */

public class SettingClickItem extends RelativeLayout{
    private View view;
    private TextView tv_title;
    private TextView tv_content;

    private String content_on;
    private String content_off;
    public SettingClickItem(Context context) {
        super(context);
        init();
    }

    public SettingClickItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public SettingClickItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();



    }
    private void init(){
        view = View.inflate(getContext(), R.layout.item_click_setting,this);
        tv_title = (TextView) view.findViewById(R.id.tv_setting_update_title);
        tv_content = (TextView) view.findViewById(R.id.tv_setting_update_content);


    }

    public void setTitle(String title){
        tv_title.setText(title);
    }
    public void setContent(String content){
        tv_content.setText(content);
    }

}
