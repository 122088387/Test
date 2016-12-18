package com.chaungying.modues.main.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaungying.BaseFragment;
import com.chaungying.address.view.AllContactFragment;
import com.chaungying.address.view.OftenContactFragment;
import com.chaungying.wuye3.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/7/25
 */

public class AddressFragment extends BaseFragment implements OnTabSelectListener {

    private Context mContext;
    private SlidingTabLayout tabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"全部", "常用联系人"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_address, null);
        setActionBar(R.string.menu_address, R.drawable.nav_return, 0, view);
        tabLayout = (SlidingTabLayout) view.findViewById(R.id.address_group_tablayout);
        //添加全部联系人
        mFragments.add(AllContactFragment.getInstance());
        //添加常用联系人
        mFragments.add(OftenContactFragment.getInstance());

        ViewPager vp = (ViewPager) view.findViewById(R.id.vp);
        MyPagerAdapter adapter = new MyPagerAdapter(((FragmentActivity) mContext).getSupportFragmentManager());
        vp.setAdapter(adapter);
        tabLayout.setViewPager(vp);
        return view;
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    /**
     * 设置每个页面动作当行条的内容和图片
     *
     * @param text           标题内容
     * @param left_drawable  左边图标
     * @param right_drawable 右边图标
     */


    public void setActionBar(
            int text,
            int left_drawable,
            int right_drawable, View view) {
        TextView tv = (TextView) view.findViewById(R.id.title);
        ImageView iv_left = (ImageView) view.findViewById(R.id.title_back);
        ImageView iv_right = (ImageView) view.findViewById(R.id.title_menu);
        tv.setText(text);
        iv_left.setImageResource(left_drawable);
        if (right_drawable != 0) {
            iv_right.setImageResource(right_drawable);
        }
    }
}
