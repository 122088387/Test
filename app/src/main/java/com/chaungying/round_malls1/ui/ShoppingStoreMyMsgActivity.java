package com.chaungying.round_malls1.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chaungying.BaseActivity;
import com.chaungying.modues.main.bean.TabEntity;
import com.chaungying.round_malls1.adapter.StoreMyMsgFragmentAdapter;
import com.chaungying.wuye3.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/10/11
 * <p>
 * 我的订单 我的收藏  我的地址界面
 */

@ContentView(R.layout.activity_shopping_store_my_msg)
public class ShoppingStoreMyMsgActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @ViewInject(R.id.shopping_store_my_msg_tabLayout)
    private CommonTabLayout tabLayout;

    @ViewInject(R.id.shopping_store_my_msg_fill)
    private View shopping_store_my_msg_fill;

    @ViewInject(R.id.shopping_store_my_msg_viewPager)
    private ViewPager viewPager;

    private StoreMyMsgFragmentAdapter adapter;

    private ArrayList<Fragment> datas = new ArrayList<>();
    private OrderListFragment orderListFragment;
    private CollectListFragment collectListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        createView();
    }

    private void createView() {
        setActionBar("我的", R.drawable.nav_return, 0);
        // 如果手机版本大于4.4 则隐藏分割线
//        if (Build.VERSION.SDK_INT >= 19) {
//            shopping_store_my_msg_fill.setVisibility(View.GONE);
//        }
        // 配置TabLayout
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new TabEntity("订单", 0, 0));
        tabEntities.add(new TabEntity("收藏", 0, 0));
//        tabEntities.add(new TabEntity("地址", 0, 0));
        tabLayout.setTabData(tabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position, true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        // 添加数据
        orderListFragment = new OrderListFragment();
        collectListFragment = new CollectListFragment();
        datas.add(orderListFragment);
        datas.add(collectListFragment);

        // 配置ViewPager
        adapter = new StoreMyMsgFragmentAdapter(getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
