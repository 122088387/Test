package com.chaungying.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 新闻碎片适配器
 *
 * @author 种耀淮 在2016年07月26日19:19创建
 */
public class NewsFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> datas;

    public NewsFragmentAdapter(FragmentManager fm, ArrayList<Fragment> datas) {
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
