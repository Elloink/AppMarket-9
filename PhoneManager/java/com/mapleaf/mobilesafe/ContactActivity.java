package com.mapleaf.mobilesafe;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mapleaf.myutils.MyAsycnTask;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {

    private ProgressBar pb;
    private ListView lv;
    private ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        lv = (ListView) findViewById(R.id.lv_contact);
        pb = (ProgressBar) findViewById(R.id.pb_contact);
        list = new ArrayList<>();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = list.get(position).get("phone");
                Intent intent = new Intent();
                intent.putExtra("phone",phone);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    new MyAsycnTask(){
        @Override
        public void preTask() {

        }

        @Override
        public void doInBack() {
            ContentResolver cr = getContentResolver();
            Cursor cursor = cr.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"_id"}, null, null, null);
            while (cursor.moveToNext()) {
                String cid = cursor.getString(0);
                Cursor c = cr.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1", "mimetype"},
                        "raw_contact_id=?", new String[]{cid}, null);
                HashMap<String, String> map = new HashMap<>();

                while (c.moveToNext()) {

                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                        map.put("name", data1);
                    }

                }
                list.add(map);

                c.close();
            }
            cursor.close();
        }

        @Override
        public void afterTask() {
            lv.setAdapter(new MyAdapter());
            pb.setVisibility(View.INVISIBLE);
        }
    }.excute();


    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(ContactActivity.this, R.layout.contact_item, null);
            TextView name = (TextView) view.findViewById(R.id.tv_contact_name);
            TextView phone = (TextView) view.findViewById(R.id.tv_contact_phone);
            name.setText(list.get(position).get("name"));
            phone.setText(list.get(position).get("phone"));
            return view;
        }
    }

}
