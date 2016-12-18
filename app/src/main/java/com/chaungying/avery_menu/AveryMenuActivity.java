package com.chaungying.avery_menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.chaungying.BaseActivity;
import com.chaungying.avery_menu.bean.MenuBean;
import com.chaungying.avery_menu.view.AveryMenuFragment;
import com.chaungying.avery_menu.view.MenuWeekView;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/16
 */
@ContentView(R.layout.activity_avery_menu)
public class AveryMenuActivity extends BaseActivity implements View.OnTouchListener /*implements AdapterView.OnItemClickListener*/ {

    @ViewInject(R.id.menu_week_view)
    MenuWeekView menuWeekView;

//    //星期布局
//    @ViewInject(R.id.avery_menu_gv)
//    private GridView appGridView;


//    WeekAdapter weekAdapter;

//    @ViewInject(R.id.avery_menu_calendar)
//    private CollapseCalendarView calendarView;

//    @ViewInject(R.id.zao)
//    LinearLayout zao;
//    @ViewInject(R.id.wu)
//    LinearLayout wu;
//    @ViewInject(R.id.wan)
//    LinearLayout wan;

    List<MenuBean.DataBean> list = new ArrayList<>();

    //滑动菜单
    @ViewInject(R.id.avery_menu_tablayout)
    SlidingTabLayout avery_menu_tablayout;

    @ViewInject(R.id.avery_menu_vp)
    ViewPager avery_menu_vp;

    private final String[] mTitles = {"早餐", "午餐", "晚餐"};

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("每日菜谱", R.drawable.nav_return, 0);
        final Calendar calendar = Calendar.getInstance();
        //菜谱中点击日期的回调
        menuWeekView.setListener(new MenuWeekView.WeekViewItemOnClickListener() {
            @Override
            public void weekViewListener(String str) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(str);
                    calendar.setTime(date);
                    initData(calendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
//        weekAdapter = new WeekAdapter(this);
//        appGridView.setAdapter(weekAdapter);
//        appGridView.setOnItemClickListener(this);

        initData(calendar);


        AveryMenuFragment fm1 = AveryMenuFragment.getInstance();
        AveryMenuFragment fm2 = AveryMenuFragment.getInstance();
        AveryMenuFragment fm3 = AveryMenuFragment.getInstance();
        //添加全部联系人
        mFragments.add(fm1);
        mFragments.add(fm2);
        mFragments.add(fm3);
        avery_menu_vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        avery_menu_tablayout.setViewPager(avery_menu_vp);

//        initViewVp();
        //获取日历的管理者
//        CalendarManager manager = new CalendarManager(LocalDate.now(), CalendarManager.State.WEEK, LocalDate.now(), LocalDate.now().plusYears(1));
//        calendarView.init(manager);
//        calendarView.setOnTouchListener(this);
//        //点击日期的监听
//        calendarView.setListener(new CollapseCalendarView.OnDateSelect() {
//            @Override
//            public void onDateSelected(LocalDate localDate) {
//                int year = localDate.getYear();
//                int month = localDate.getMonthOfYear();
//                int day = localDate.getDayOfMonth();
//                //获取的当前点击的日期
//                L.e(year + "-" + month + "-" + day);
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, month - 1, day);
//                String title = new SimpleDateFormat("yyyy 年 MM 月").format(calendar.getTime());
//                calendarView.setMyTitle(title);
//                //请求数据
//                initData(calendar);
//            }
//        });

    }


    private void initViewVpDatas() {
//        avery_menu_tablayout.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                if(list.size() > 0){
//                    List<MenuBean.DataBean.CookbookInfosBean> cookList = list.get(0).getCookbookInfos();
//                    List<MenuBean.DataBean.CookbookInfosBean> cookList1 = new ArrayList<MenuBean.DataBean.CookbookInfosBean>();
//                    for (int i = 0; i < cookList.size(); i++) {
//                        if (cookList.get(i).getType() == position + 1) {
//                            cookList1.add(cookList.get(i));
//                        }
//                    }
//                    ((AveryMenuFragment) mFragments.get(position)).setDataBeanList(cookList1);
//                }
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });
        if (list.size() > 0) {
            List<MenuBean.DataBean.CookbookInfosBean> cookList = list.get(0).getCookbookInfos();
            List<MenuBean.DataBean.CookbookInfosBean> cookList1 = new ArrayList<MenuBean.DataBean.CookbookInfosBean>();
            List<MenuBean.DataBean.CookbookInfosBean> cookList2 = new ArrayList<MenuBean.DataBean.CookbookInfosBean>();
            List<MenuBean.DataBean.CookbookInfosBean> cookList3 = new ArrayList<MenuBean.DataBean.CookbookInfosBean>();
            for (int i = 0; i < cookList.size(); i++) {
                if (cookList.get(i).getType() == 1) {
                    cookList1.add(cookList.get(i));
                    ((AveryMenuFragment) mFragments.get(0)).setDataBeanList(cookList1);
                } else if (cookList.get(i).getType() == 2) {
                    cookList2.add(cookList.get(i));
                    ((AveryMenuFragment) mFragments.get(1)).setDataBeanList(cookList2);
                } else if (cookList.get(i).getType() == 3) {
                    cookList3.add(cookList.get(i));
                    ((AveryMenuFragment) mFragments.get(2)).setDataBeanList(cookList3);
                }
            }
        } else if (list.size() == 0) {
            List<MenuBean.DataBean.CookbookInfosBean> cookList = new ArrayList<MenuBean.DataBean.CookbookInfosBean>();
            for (int i = 0; i < 3; i++) {
                ((AveryMenuFragment) mFragments.get(i)).setDataBeanList(cookList);
            }
        }
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


    private void initData(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(calendar.getTime());
        ProgressUtil.show(this, "加载中...");
//        RequestParams params = new RequestParams(Const.WuYe.URL_AVERY_MENU);
        RequestParams params = new RequestParams(Const.WuYe.URL_AVERY_MENU);
//        ?userId=4513&date=2016-08-17
        params.addParameter("userId", SPUtils.get(this, Const.SPDate.ID, 4512));
        params.addParameter("date", date);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MenuBean menuBean = gson.fromJson(result, MenuBean.class);
                list = menuBean.getData();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String weekDate = sdf.format(new Date());
//                String returnWeek = GetWeekUtils.getWeek(weekDate);
//                int position = GetWeekUtils.getWeekInt(returnWeek);
//                initView(0);
                initViewVpDatas();
                ProgressUtil.close();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ProgressUtil.close();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        initView(position);
//        for (int i = 0; i < 7; i++) {
//            if(i == position){
//                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                TextView tv = (TextView) view.findViewById(R.id.menu_item);
//                tv.setTextColor(getResources().getColor(R.color.White));
//            }else{
//                parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.White));
//                TextView tv = (TextView)  parent.getChildAt(i).findViewById(R.id.menu_item);
//                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
//            }
//        }
//    }
}
