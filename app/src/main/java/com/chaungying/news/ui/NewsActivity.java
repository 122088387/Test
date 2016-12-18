package com.chaungying.news.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chaungying.BaseActivity;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.news.adapter.NewsFragmentAdapter;
import com.chaungying.wuye3.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 新闻公告主页
 *
 * @author 种耀淮 在2016年07月26日18:28创建
 */
@ContentView(R.layout.activity_news)
public class NewsActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener, OnTabSelectListener, NewsListFragment.ReturnUnreadNumListener {


    // Tab布局
    @ViewInject(R.id.newsList_tabLayout1)
    private SlidingTabLayout tabLayout;

    // 分割线
    @ViewInject(R.id.newsList_fill)
    private View fill;

    // ViewPager
    @ViewInject(R.id.newsList_viewPager)
    private ViewPager viewPager;

    // 碎片适配器
    private NewsFragmentAdapter adapter;

    private ArrayList<Fragment> datas = new ArrayList<>();

    private NewsListFragment newsListFragment;
    private NoticeListFragment noticeListFragment;

    // 配置TabLayout
//    ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ProgressUtil.show(this, "加载中...");
        createView();
    }

    /**
     * 初始化
     */
    private void createView() {
        // 初始化Title
        setActionBar("新闻公告", R.drawable.nav_return, 0);
        // 初始化Content
        // 如果手机版本大于4.4 则隐藏分割线
//        if (Build.VERSION.SDK_INT >= 19) {
//            fill.setVisibility(View.GONE);
//        }

        tabLayout.setOnTabSelectListener(this);

        // 添加数据
        newsListFragment = new NewsListFragment();
        noticeListFragment = new NoticeListFragment();
        datas.add(newsListFragment);
        datas.add(noticeListFragment);

        newsListFragment.setUnreadNumListener(this);

        noticeListFragment.setUnreadNumListener(new NewsListFragment.ReturnUnreadNumListener() {
            @Override
            public void returnNum(int unReadNum) {
                if (unReadNum > 0) {
                    tabLayout.showMsg(1, unReadNum);
                    tabLayout.setMsgMargin(1, 70, 4);
                    MsgView msgView = tabLayout.getMsgView(1);
                    msgView.setStrokeWidth(0);
                } else {
                    tabLayout.hideMsg(1);
                }
            }
        });

        // 配置ViewPager
        adapter = new NewsFragmentAdapter(getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

        tabLayout.setViewPager(viewPager, new String[]{"新闻", "公告"});
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

    @Override
    public void onTabSelect(int position) {
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void returnNum(int unReadNum) {
        if (unReadNum > 0) {
            tabLayout.showMsg(0, unReadNum);
            tabLayout.setMsgMargin(0, 70, 4);
            MsgView msgView = tabLayout.getMsgView(0);
            msgView.setStrokeWidth(0);
        } else {
            tabLayout.hideMsg(0);
        }
    }
}
