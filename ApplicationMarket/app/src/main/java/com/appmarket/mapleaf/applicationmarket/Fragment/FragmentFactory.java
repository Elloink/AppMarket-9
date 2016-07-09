package com.appmarket.mapleaf.applicationmarket.Fragment;

import java.util.HashMap;

/**
 * Created by Mapleaf on 2016/7/8.
 */
public class FragmentFactory {
    private static HashMap<Integer,BaseFragment> map = new HashMap<>();
    public static BaseFragment createFragment(int position){
        BaseFragment fragment = map.get(position);
        if(fragment==null){
            switch (position){
                case 0:
                    fragment = new Fragment1();
                    break;
                case 1:
                    fragment = new Fragment2();
                    break;
                case 2:
                    fragment = new Fragment3();
                    break;
                case 3:
                    fragment = new Fragment4();
                    break;
                case 4:
                    fragment = new Fragment5();
                    break;
                case 5:
                    fragment = new Fragment6();
                    break;
                case 6:
                    fragment = new Fragment7();
                    break;
            }
            map.put(position,fragment);
        }
        return fragment;
    }
}
