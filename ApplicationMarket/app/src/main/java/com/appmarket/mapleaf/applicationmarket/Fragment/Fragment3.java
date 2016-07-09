package com.appmarket.mapleaf.applicationmarket.Fragment;

import android.view.View;

import com.appmarket.mapleaf.applicationmarket.view.LoadingPage;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class Fragment3 extends BaseFragment{
    @Override
    public View onCreateSuccessView() {
        return null;
    }

    @Override
    public int onLoad() {
        return LoadingPage.STATER_ERROR;
    }
}
