package com.appmarket.mapleaf.applicationmarket.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.bean.SubjectInfo;
import com.appmarket.mapleaf.applicationmarket.http.Protocol4;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;
import com.appmarket.mapleaf.applicationmarket.view.LoadingPage;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseAdapter;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseHolder;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class Fragment4 extends BaseFragment{
    private ArrayList<SubjectInfo> infoList;
    @Override
    public View onCreateSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDivider(null);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(new ColorDrawable());
        listView.setAdapter(new MyBaseAdapter<SubjectInfo>(infoList) {
            @Override
            public MyBaseHolder getHolder(int position) {
                return new MyBaseHolder<SubjectInfo>(){
                    private TextView tv_sub;
                    private ImageView iv_sub;
                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.subject_item);
                        tv_sub = (TextView) view.findViewById(R.id.tv_sub);
                        iv_sub = (ImageView) view.findViewById(R.id.iv_sub);
                        return view;
                    }

                    @Override
                    public void refreshView(SubjectInfo data) {
                        tv_sub.setText(data.des);

                        x.image().bind(iv_sub,"http://127.0.0.1:8090/image?name="+data.iconUrl,options);
                    }
                };
            }

            @Override
            public ArrayList<SubjectInfo> loadMore() {
                Protocol4 protocol4 = new Protocol4();
                return protocol4.getData(getListsize());
            }
        });
        return listView;
    }

    @Override
    public int onLoad() {
        Protocol4 protocol4 = new Protocol4();
        infoList=protocol4.getData(0);
        return check(infoList);
    }
}
