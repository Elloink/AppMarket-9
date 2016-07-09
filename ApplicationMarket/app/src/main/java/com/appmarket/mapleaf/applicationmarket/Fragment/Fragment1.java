package com.appmarket.mapleaf.applicationmarket.Fragment;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;
import com.appmarket.mapleaf.applicationmarket.view.LoadingPage;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseAdapter;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseHolder;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class Fragment1 extends BaseFragment{
    private ArrayList<String> list;
    @Override
    public View onCreateSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setAdapter(new MyBaseAdapter<String>(list){
            @Override
            public MyBaseHolder getHolder() {

                return new MyBaseHolder<String>() {
                    private TextView textView;
                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.fragment1_layout);
                        textView = (TextView) view.findViewById(R.id.tv_fragment1);
                        return view;
                    }

                    @Override
                    public void refreshView(String data) {
                        textView.setText(data);
                    }
                };
            }

            @Override
            public ArrayList<String> loadMore() {
                ArrayList<String> list = new ArrayList<String>();
                for(int i =0 ;i<10;i++){
                    list.add("extra:"+i);
                }
                SystemClock.sleep(2000);
                return list;
            }
        });

        return listView;
    }

    @Override
    public int onLoad() {
        list= new ArrayList<>();
        for(int i =0;i<20;i++){
            list.add("text:"+i);
        }
        return LoadingPage.STATER_SUCCESS;
    }
}
