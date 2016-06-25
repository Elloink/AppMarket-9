package com.mapleaf.mobilesafe;

import android.provider.Settings;
import android.test.AndroidTestCase;

import com.mapleaf.engine.AddressDao;

/**
 * Created by Mapleaf on 2016/6/12.
 */
public class TextAddress extends AndroidTestCase{

    public void text123(){
        String s = AddressDao.AddressParse(getContext(), "18888888888");
        System.out.print("------------------------------------"+s+"----------------------");
    }
}
