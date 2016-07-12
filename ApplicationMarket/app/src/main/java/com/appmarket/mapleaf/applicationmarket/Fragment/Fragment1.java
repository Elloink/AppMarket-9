package com.appmarket.mapleaf.applicationmarket.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;
import com.appmarket.mapleaf.applicationmarket.http.Protocol1;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseAdapter;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseHolder;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class Fragment1 extends BaseFragment{
    private ArrayList<AppInfo> appInfoArrayList;

    @Override
    public View onCreateSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDivider(null);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(new ColorDrawable());
        listView.setAdapter(new MyBaseAdapter<AppInfo>(appInfoArrayList){

            @Override
            public MyBaseHolder getHolder() {

                return new MyBaseHolder<AppInfo>() {
                    private TextView name;
                    private TextView des;
                    private ImageView icon;
                    private LinearLayout download;
                    private TextView size;
                    private RatingBar rb_star;
                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.fragment1_layout);
                        name = (TextView) view.findViewById(R.id.tv_appname);
                        size = (TextView) view.findViewById(R.id.tv_appsize);
                        icon = (ImageView) view.findViewById(R.id.iv_apppic);
                        des = (TextView) view.findViewById(R.id.tv_des);
                        rb_star = (RatingBar) view.findViewById(R.id.rb_stars);
                        return view;
                    }

                    @Override
                    public void refreshView(AppInfo data) {
                        name.setText(data.name);
                        des.setText(data.des);
                        size.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
                        x.image().bind(icon,"http://127.0.0.1:8090/image?name="+data.iconUrl,options);
                        rb_star.setRating(data.stars);
                    }
                };
            }

            @Override
            public ArrayList<AppInfo> loadMore() {
                Protocol1 protocol1 = new Protocol1();

                return protocol1.getData(getListsize());
            }
        });

        return listView;
    }

    @Override
    public int onLoad() {
        Protocol1 protocol1 = new Protocol1();
        appInfoArrayList = protocol1.getData(0);

        return check(appInfoArrayList);
    }
}
