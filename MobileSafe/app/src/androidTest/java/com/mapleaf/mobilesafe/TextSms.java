package com.mapleaf.mobilesafe;

import android.test.AndroidTestCase;
import android.util.Log;

import com.mapleaf.engine.ContackEngine;
import com.mapleaf.engine.SmsEngine;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mapleaf on 2016/6/2.
 */

public class TextSms extends AndroidTestCase {
    public void textsms() {
        SmsEngine.getAllSms(getContext());
    }
}
