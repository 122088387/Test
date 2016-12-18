package com.chaungying.modues.main.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.chaungying.BaseFragment;
import com.chaungying.avery_menu.AveryMenuActivity;
import com.chaungying.common.utils.T;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.ji_xiao.ui.PerformanceActivity;
import com.chaungying.jinjitonggao.ui.TongGaoActivity;
import com.chaungying.metting.ui.MettingRoomActivity;
import com.chaungying.modues.main.adapter.ApplyAdapter;
import com.chaungying.modues.main.bean.RoleAppsBean;
import com.chaungying.news.ui.NewsActivity;
import com.chaungying.park_navigation.ui.ActivityParkList;
import com.chaungying.patrols.ui.PatrolsActivity;
import com.chaungying.qiandao.ui.SignInActivity;
import com.chaungying.round_malls1.ui.MyShopActivity;
import com.chaungying.round_malls1.ui.ShoopingStoreActivity;
import com.chaungying.use_car.ui.UseCarActivity;
import com.chaungying.work_list.ui.WorkListActivity;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.ui.ZiXunJieDaListActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 应用界面的Fragment
 *
 * @anthor 王晓赛 or 2016/6/22
 */
public class ApplyFragment extends BaseFragment implements AdapterView.OnItemClickListener, EasyPermissions.PermissionCallbacks {
    //九宫格布局
    private GridView appGridView;
    //适配器
    private ApplyAdapter adapter;
    private ArrayList<RoleAppsBean> data_list = new ArrayList<>();
    //上下文对象
    private Context mContext;
    private int index = 0;

    public ApplyFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ApplyFragment(ArrayList<RoleAppsBean> data_list) {
        super();
        this.data_list = data_list;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            data_list = (ArrayList<RoleAppsBean>) savedInstanceState.getSerializable("datas");
            savedInstanceState.remove("datas");
        }
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_apply, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("应用");
        appGridView = (GridView) view.findViewById(R.id.apply_grid_view);
        adapter = new ApplyAdapter(getActivity(), data_list);
        appGridView.setAdapter(adapter);
        appGridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        index = position;
        int appId = data_list.get(position).getApplicationId();
        if (appId == 114) {//新闻通知
            openActivty(mContext, NewsActivity.class, null, null);
        } else if (appId == 102) { //进入会议室预定界面
            openActivty(mContext, MettingRoomActivity.class, null, null);
        } else if (appId == 73) {//巡查任务
            T.showLong(mContext, position + "");
            Bundle bundle = new Bundle();
            bundle.putInt("applicationId", data_list.get(position).getApplicationId());//73
            //跳入到适配界面
            openActivty(mContext, PatrolsActivity.class, bundle, null);
        } else if (appId == 86) {//紧急通告
            Bundle bundle = new Bundle();
            bundle.putInt("applicationId", data_list.get(position).getApplicationId());//86
            openActivty(mContext, TongGaoActivity.class, bundle, null);
        } else if (appId == 101) {//签到
            methodRequiresTwoPermission();
        } else if (appId == 105) {//工作单
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", data_list.get(position));//105
            openActivty(mContext, WorkListActivity.class, bundle, null);
        }
//        else if (appId == 106) {//派工管理
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("bean", data_list.get(position));
//            openActivty(mContext, ZiXunJieDaListActivity.class, bundle, null);
//        }
        else if (appId == 111 || appId == 113 || appId == 103   //  咨询解答  投诉管理   订餐
                || appId == 104 || appId == 108 || appId == 106  // 现场报修  能源管理  派工管理
                ) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", data_list.get(position));
            openActivty(mContext, ZiXunJieDaListActivity.class, bundle, null);
        } else if (appId == 131) {//用车管理
            openActivty(mContext, UseCarActivity.class, null, null);
        } else if (appId == 109) {//园区导航
            openActivty(mContext, ActivityParkList.class, null, null);
        } else if (appId == 130) {//每日菜谱
            openActivty(mContext, AveryMenuActivity.class, null, null);
        } else if (appId == 133 || appId == 132) {//我要投诉  我要咨询
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", data_list.get(position));
            openActivty(mContext, ZiXunJieDaListActivity.class, bundle, null);
        } else if (appId == 110) {//工作绩效
            openActivty(mContext, PerformanceActivity.class, null, null);
        } else if (appId == 156) {//周边商城
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", data_list.get(position));
            openActivty(mContext, ShoopingStoreActivity.class, bundle, null);
//            openActivty(mContext, RoundMallsActivity.class, bundle, null);
        } else if (appId == 157) {
            openActivty(mContext, MyShopActivity.class, null, null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("datas", data_list);
    }

    private static final int RC_LOCATION = 0X0001;

    @AfterPermissionGranted(RC_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // Already have permission, do the thing
            // ...
            Bundle bundle = new Bundle();
            bundle.putInt("applicationId", data_list.get(index).getApplicationId());//85
            openActivty(mContext, SignInActivity.class, bundle, null);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "请开启定位权限，否则无法使用签到功能",
                    RC_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Bundle bundle = new Bundle();
        bundle.putInt("applicationId", data_list.get(index).getApplicationId());//85
        openActivty(mContext, SignInActivity.class, bundle, null);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        T.showLong(getContext(),"请开启定位权限，否则无法使用签到功能");
        final CustomDialog.Builder dialog = new CustomDialog.Builder(mContext);
        dialog.setTitle("提示");
        dialog.setMessage("请手动开启定位权限，否则无法使用签到功能");
        dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
//                intent.setClassName("com.android.settings", "com.chaungying.wuye3");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.create().show();


    }


}
