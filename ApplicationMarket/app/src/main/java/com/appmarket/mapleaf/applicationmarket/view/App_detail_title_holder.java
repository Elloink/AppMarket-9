package com.appmarket.mapleaf.applicationmarket.view;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.bean.AppInfo;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Mapleaf on 2016/7/13.
 */
public class App_detail_title_holder extends MyBaseHolder<AppInfo>{
    private TextView name;
    private TextView des;
    private ImageView icon;
    private TextView size;
    private RatingBar rb_star;
    protected ImageOptions options;

    public App_detail_title_holder() {
        super();
        options = new ImageOptions.Builder().setFailureDrawableId(R.drawable.ic_default)
                .setLoadingDrawableId(R.drawable.ic_default).build();
    }

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
}
