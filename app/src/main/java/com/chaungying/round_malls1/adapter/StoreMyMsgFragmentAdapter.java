package com.chaungying.round_malls1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 商城我的信息的适配器
 *
 */
public class StoreMyMsgFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> datas;

    public StoreMyMsgFragmentAdapter(FragmentManager fm, ArrayList<Fragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
