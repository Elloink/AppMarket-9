package com.mapleaf.mobilesafe;

import android.test.AndroidTestCase;
import android.util.Log;

import com.mapleaf.engine.ContackEngine;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mapleaf on 2016/6/2.
 */

public class TextMo extends AndroidTestCase{
    public void textg(){


        List<HashMap<String, String>> contact = ContackEngine.getContact(getContext());
        for (HashMap<String,String> map:
                contact) {
            Log.e("---------------","姓名"+map.get("name")+"、电话"+map.get("phone"));
        }
    }
}
