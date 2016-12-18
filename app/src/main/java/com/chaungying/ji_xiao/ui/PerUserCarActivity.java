package com.chaungying.ji_xiao.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.view.DownPopWindowPerView;
import com.chaungying.ji_xiao.adapter.UserCarAdapter;
import com.chaungying.ji_xiao.bean.JobHeader;
import com.chaungying.ji_xiao.bean.PerCarBean;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/9/20
 */

@ContentView(R.layout.activity_per_user_car)
public class PerUserCarActivity extends BaseActivity {

    JobHeader bean;
    UserCarAdapter userCarAdapter;

    @ViewInject(R.id.down_popwindow_view)
    private DownPopWindowPerView down_popwindow_view;

    @ViewInject(R.id.lv_per_user_car)
    private ListView lv_per_user_car;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    PerCarBean bean = (PerCarBean) msg.getData().getSerializable("bean");
                    if (bean != null) {
                        userCarAdapter.setList(bean.getData());
                        userCarAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar("用车情况", R.drawable.nav_return, 0);
        userCarAdapter = new UserCarAdapter(this);
        lv_per_user_car.setAdapter(userCarAdapter);
        initData();
    }

    private void initData() {
        ProgressUtil.show(this, "加载中...");
        //下拉时间  先加载出来
        RequestParams params = new RequestParams(Const.WuYe.URL_SIGNIN_WORK_CAR_WORK_PERFORMANCE_FILETRATE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                bean = new Gson().fromJson(result, JobHeader.class);
                if (bean != null) {
                    initView();
                }
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


        //加载列表中的数据
        RequestParams params1 = new RequestParams(Const.WuYe.URL_SIGNIN_WORK_CAR_WORK_PERFORMANCE_LIST);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PerCarBean bean = new Gson().fromJson(result, PerCarBean.class);
                if (bean != null) {
                    userCarAdapter.setList(bean.getData());
                    userCarAdapter.notifyDataSetChanged();
                }
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

    private void initView() {
        down_popwindow_view.setItemsBeanList(bean.getItems());
        down_popwindow_view.setHandler(handler);
    }
}
