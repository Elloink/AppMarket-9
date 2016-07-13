package com.appmarket.mapleaf.applicationmarket.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appmarket.mapleaf.applicationmarket.R;
import com.appmarket.mapleaf.applicationmarket.bean.CategoryInfo;
import com.appmarket.mapleaf.applicationmarket.http.Protocol6;
import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseAdapter;
import com.appmarket.mapleaf.applicationmarket.view.MyBaseHolder;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class Fragment6 extends BaseFragment{
    private ArrayList<CategoryInfo> infoList;
    @Override
    public View onCreateSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDivider(null);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(new ColorDrawable());
        listView.setAdapter(new MyCategoryAdapter(infoList) {

            @Override
            public MyBaseHolder getHolder(int position) {


                if(infoList.get(position).isTitle){
                    //返回标题的Holder
                    return new MyBaseHolder<CategoryInfo>() {
                        private TextView textView;
                        @Override
                        public View initView() {
                            View view = UIUtils.inflate(R.layout.category_title_);
                            textView = (TextView) view.findViewById(R.id.tv_category_title);
                            return view;
                        }

                        @Override
                        public void refreshView(CategoryInfo data) {
                            textView.setText(data.title);
                        }
                    };
                }else{
                    //返回普通的Holder
                    return new MyBaseHolder<CategoryInfo>() {
                        private TextView textView1;
                        private TextView textView2;
                        private TextView textView3;
                        private ImageView imageView1;
                        private ImageView imageView2;
                        private ImageView imageView3;
                        private LinearLayout linearLayout1;
                        private LinearLayout linearLayout2;
                        private LinearLayout linearLayout3;


                        @Override
                        public View initView() {
                            View view = UIUtils.inflate(R.layout.category_item);
                            textView1 = (TextView) view.findViewById(R.id.tv_category_1);
                            textView2 = (TextView) view.findViewById(R.id.tv_category_2);
                            textView3 = (TextView) view.findViewById(R.id.tv_category_3);
                            imageView1 = (ImageView) view.findViewById(R.id.iv_category_1);
                            imageView2 = (ImageView) view.findViewById(R.id.iv_category_2);
                            imageView3 = (ImageView) view.findViewById(R.id.iv_category_3);
                            linearLayout1 = (LinearLayout) view.findViewById(R.id.ll_category_1);
                            linearLayout2 = (LinearLayout) view.findViewById(R.id.ll_category_2);
                            linearLayout3 = (LinearLayout) view.findViewById(R.id.ll_category_3);
                            linearLayout1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            linearLayout2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            linearLayout3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });


                            return view;
                        }

                        @Override
                        public void refreshView(CategoryInfo data) {
                            textView1.setText(data.name1);
                            textView2.setText(data.name2);
                            textView3.setText(data.name3);
                            x.image().bind(imageView1,"http://127.0.0.1:8090/image?name="+data.url1,options);
                            x.image().bind(imageView2,"http://127.0.0.1:8090/image?name="+data.url2,options);
                            x.image().bind(imageView3,"http://127.0.0.1:8090/image?name="+data.url3,options);

                        }
                    };
                }
            }

            @Override
            public ArrayList<CategoryInfo> loadMore() {
                return null;
            }

            @Override
            public boolean hasMore() {
                return false;
            }

            @Override
            public int getViewTypeCount() {
                return 3;
            }

            @Override
            public int getInnerType(int position) {
                if(infoList.get(position).isTitle){
                    return 2;
                }else{
                    return 1;
                }
            }
        });
        return listView;
    }

    @Override
    public int onLoad() {
        Protocol6 protocol6= new Protocol6();
        infoList =  protocol6.getData(0);
        return check(infoList);
    }
    private class MyCategoryAdapter extends MyBaseAdapter {
        public MyCategoryAdapter(ArrayList list) {
            super(list);
            options = new ImageOptions.Builder().build();
        }

        @Override
        public MyBaseHolder getHolder(int position) {
            return null;
        }

        @Override
        public ArrayList<CategoryInfo> loadMore() {
            return null;
        }
    }
}
