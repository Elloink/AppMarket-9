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

public class SettingItem extends RelativeLayout{
    private View view;
    private TextView tv_title;
    private TextView tv_content;
    private CheckBox cb;
    private String content_on;
    private String content_off;
    public SettingItem(Context context) {
        super(context);
        init();
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        String title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "titlee");
         content_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "content_on");
        content_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "content_off");
        init();
        tv_title.setText(title);
        if(cb.isChecked()){
            tv_content.setText(content_on);

        }else{
            tv_content.setText(content_off);
        }

    }
    private void init(){
        view = View.inflate(getContext(), R.layout.item_setting,this);
        tv_title = (TextView) view.findViewById(R.id.tv_setting_update_title);
        tv_content = (TextView) view.findViewById(R.id.tv_setting_update_content);
        cb = (CheckBox) view.findViewById(R.id.cb_setting_update_checkbox);

    }
    public boolean isChecked(){
        return cb.isChecked();
    }
    public void setTitle(String title){
        tv_title.setText(title);
    }
    public void setContent(String content){
        tv_content.setText(content);
    }
    public void setCheck(boolean bool){
        cb.setChecked(bool);
        if(cb.isChecked()){
            tv_content.setText(content_on);

        }else{
            tv_content.setText(content_off);
        }
    }
}
