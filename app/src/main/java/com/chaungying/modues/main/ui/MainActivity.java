package com.chaungying.modues.main.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chaungying.common.constant.Const;
import com.chaungying.common.constant.ExtraTag;
import com.chaungying.gongzuotai.WorkFragment;
import com.chaungying.modues.main.adapter.MainAdapter;
import com.chaungying.modues.main.bean.RoleAppsBean;
import com.chaungying.modues.main.bean.TabEntity;
import com.chaungying.modues.main.utils.FileUtils;
import com.chaungying.modues.main.view.ApplyFragment;
import com.chaungying.modues.main.view.SetFragment;
import com.chaungying.wuye3.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

/**
 * @anthor 王晓赛 or 2016/6/22
 */

@ContentView(R.layout.activity_main1)
public class MainActivity extends FragmentActivity implements WorkFragment.sendMsgNumberListener, SetFragment.SetSystemUpdateListener {

    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"工作台", "应用",
//            "通讯录",//注释时注意
            "设置"};
    //没有按下时
    private int[] mIconUnselectIds = {R.drawable.icon_workbench, R.drawable.icon_apply,
//            R.drawable.icon_address_list,//注释时注意
            R.drawable.icon_set};
    //按下时的图片
    private int[] mIconSelectIds = {R.drawable.icon_workbench_b, R.drawable.icon_apply_b,
//            R.drawable.icon_address_list_b,//注释时注意
            R.drawable.icon_set_b};
    //创建下部Tab的集合
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;

    private ViewPager viewPager;

    //存储json中的每个元素的信息
    private ArrayList<RoleAppsBean> data_list;
    private CommonTabLayout mTabLayout_1;

    private WorkFragment workFragment;
    private ApplyFragment applyFragment;
    private SetFragment setFragment;

    private MainAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            //沉浸式状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        x.view().inject(this);
        initView();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        //从intent中获取item的信息
        data_list = (ArrayList<RoleAppsBean>) getIntent().getBundleExtra(ExtraTag.APP_JSON).getSerializable("bundle");
        //将碎片添加到集合中
        workFragment = new WorkFragment();
        //实现发送消息红色气泡的接口
        workFragment.setSendMsgNumberListener(this);

        applyFragment = new ApplyFragment(data_list);

        //设置中系统提示的回调
        setFragment = new SetFragment();
        setFragment.setSetSystemUpdateListener(this);

        mFragments.add(workFragment);
        mFragments.add(applyFragment);
        mFragments.add(setFragment);


        //Tab按钮的内容设置
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mTabLayout_1 = (CommonTabLayout) findViewById(R.id.tl_1);
        mTabLayout_1.setTabData(mTabEntities);
        viewPager = (ViewPager) findViewById(R.id.fl_change);
        adapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(adapter);

        for (int i = 0; i < mTitles.length; i++) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTabLayout_1.getIconView(i).getLayoutParams();
            params.setMargins(0, 12, 0, 0);
            mTabLayout_1.getIconView(i).setLayoutParams(params);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_1.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public void setWorkListener() {
        if (workFragment != null) {
            workFragment.setSendMsgNumberListener(this);
        }
    }

    @Override
    protected void onResume() {
//        if (workFragment.getUnReadMsgNum() > 0) {
//            mTabLayout_1.showMsg(0, workFragment.getUnReadMsgNum());
//            mTabLayout_1.setMsgMargin(0,-10,0);
//            MsgView msgView = mTabLayout_1.getMsgView(0);
//            msgView.setStrokeWidth(0);
//        } else {
//            mTabLayout_1.hideMsg(0);
//        }
        if (workFragment != null) {
            workFragment.setSendMsgNumberListener(this);
        }
        if (setFragment != null) {
            setFragment.setSetSystemUpdateListener(this);
        }
        super.onResume();
    }

    @Override
    public void sendMsgNumber(int num) {
        if (num > 0) {
            mTabLayout_1.showMsg(0, num);
            mTabLayout_1.setMsgMargin(0, -10, 2);
            MsgView msgView = mTabLayout_1.getMsgView(0);
            msgView.setStrokeWidth(0);
        } else {
            mTabLayout_1.hideMsg(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void systemUpdate(boolean isShow) {
        if (isShow) {
            mTabLayout_1.showMsg(2, 1);
            mTabLayout_1.setMsgMargin(2, -8, 1);
            MsgView msgView = mTabLayout_1.getMsgView(2);
            msgView.setStrokeWidth(0);
        } else {
            mTabLayout_1.hideMsg(2);
        }
    }

    @Override
    protected void onDestroy() {
        //删除声音文件夹
        File voicePathDirFile = new File(Const.SAVE_MEDAR.VOICE_PATH);
        FileUtils.deleteDir(voicePathDirFile);

        //删除签字文件夹
        File signPathDirFile = new File(Const.SAVE_MEDAR.SIGN_PATH);
        FileUtils.deleteDir(signPathDirFile);

        //删除拍照文件夹
        File picPathDirFile = new File(Const.SAVE_MEDAR.PIC_PATH);
        FileUtils.deleteDir(picPathDirFile);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                break;
        }
        return true;
    }
}
