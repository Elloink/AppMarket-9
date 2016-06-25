package com.mapleaf.mobilesafe;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.mapleaf.db.BlackNumDao;
import com.mapleaf.db.BlackNumOpenHelper;

/**
 * Created by Mapleaf on 2016/6/18.
 */
public class TestDB extends AndroidTestCase{
    public void test(){

        BlackNumDao dao = new BlackNumDao(getContext());
        dao.add("18888888888888","1");
        dao.add("18888834568","2");
        dao.add("188884578","0");
        dao.add("188888888","1");
        dao.add("1888888888","1");
        dao.add("18888884574588","1");
        dao.add("188888854788","1");
        dao.add("18888884578","1");
        dao.add("188888854774578","1");
        dao.add("18888888888","1");
        dao.add("18888888888888","1");
        dao.add("188888845788","1");
        dao.add("188888888548","1");
        dao.add("1888885478888","1");
        dao.add("1888888888","1");
        dao.add("18888845788","1");

    }
}
