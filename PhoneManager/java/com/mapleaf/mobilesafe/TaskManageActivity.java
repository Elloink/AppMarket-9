package com.mapleaf.mobilesafe;

import android.app.Activity;
import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mapleaf.bean.TaskInfo;
import com.mapleaf.engine.TaskEngine;
import com.mapleaf.myutils.MyAsycnTask;
import com.mapleaf.myutils.TaskUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskManageActivity extends Activity {

    private ListView lv_taskmanage;
    private TextView tv_taskmanage_storage;
    private ProgressBar pb_taskmanage;
    private List<TaskInfo> list;
    private MyAdapter adapter;
    private TaskInfo taskInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);
        lv_taskmanage = (ListView) findViewById(R.id.lv_taskmanage);
        tv_taskmanage_storage = (TextView) findViewById(R.id.tv_taskmanage_storage);
        pb_taskmanage = (ProgressBar) findViewById(R.id.pb_taskmanage);
        loadData();
        listviewclick();
        int process = TaskUtils.getProcess(getApplicationContext());
        long availMem = TaskUtils.getAvailMem(getApplicationContext());
        long totalMem = TaskUtils.getTotalMem(getApplicationContext());
        String avail = Formatter.formatFileSize(getApplicationContext(), availMem);
        String total = Formatter.formatFileSize(getApplicationContext(), totalMem);
        tv_taskmanage_storage.setText("运行中的进程："+process+"内存"+avail+"/"+total);

    }

    private void listviewclick() {
        lv_taskmanage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                taskInfo = list.get(position);
                if(taskInfo.isCheck()){
                    taskInfo.setCheck(false);
                }else{
                    taskInfo.setCheck(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void loadData() {
        new MyAsycnTask(){

            @Override
            public void preTask() {
                pb_taskmanage.setVisibility(View.VISIBLE);
            }

            @Override
            public void doInBack() {
                list = TaskEngine.getTaskInfo(getApplicationContext());
            }

            @Override
            public void afterTask() {
                pb_taskmanage.setVisibility(View.INVISIBLE);
                adapter = new MyAdapter();
                lv_taskmanage.setAdapter(adapter);

            }
        }.excute();
    }
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            ViewHolder viewHolder=null;
            if(convertView!=null){
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }else{
                view = View.inflate(getApplicationContext(),R.layout.item_taskmanage,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_name = (TextView) view.findViewById(R.id.item_taskmanage_appname);
                viewHolder.iv_icon = (ImageView) view.findViewById(R.id.item_taskmanage_icon);
                viewHolder.tv_progress = (TextView) view.findViewById(R.id.tv_taskmanage_storage);
                viewHolder.cb = (CheckBox) view.findViewById(R.id.cb_taskmanage);
                view.setTag(viewHolder);
            }
            taskInfo = list.get(position);
            viewHolder.tv_name.setText(taskInfo.getName());
            viewHolder.iv_icon.setImageDrawable(taskInfo.getIcon());
            if(taskInfo.isCheck()){
                viewHolder.cb.setChecked(true);
            }else{
                viewHolder.cb.setChecked(false);
            }
//            long ramSize = taskInfo.getRamSize();
//            String s = Formatter.formatFileSize(getApplicationContext(), ramSize);
//            viewHolder.tv_progress.setText("内存占用："+s);
            return view;
        }
    }
    static class ViewHolder{
        TextView tv_name;
        ImageView iv_icon;
        TextView tv_progress;
        CheckBox cb;
    }
    public void all(View view){
        for(int i =0 ;i<list.size();i++){
            list.get(i).setCheck(true);
            adapter.notifyDataSetChanged();
        }
    }
    public void cancle(View view){
        for(int i =0 ;i<list.size();i++){
            list.get(i).setCheck(false);
            adapter.notifyDataSetChanged();
        }
    }
    public void clear(View view){
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<TaskInfo> deletelist = new ArrayList<>();
        for(int i = 0 ;i<list.size();i++){
            if(list.get(i).isCheck()){
                activityManager.killBackgroundProcesses(list.get(i).getPackageName());
                deletelist.add(list.get(i));
            }
        }
        for(TaskInfo info : deletelist){
            list.remove(info);
        }
        deletelist.clear();
        deletelist=null;
        adapter.notifyDataSetChanged();
    }
    public void setting(View view){

    }
}
