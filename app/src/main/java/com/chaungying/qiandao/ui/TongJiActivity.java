package com.chaungying.qiandao.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.qiandao.adapter.TongJiAdapter;
import com.chaungying.qiandao.adapter.TongJiListener;
import com.chaungying.qiandao.bean.TongJiBean;
import com.chaungying.site_repairs.view.PullToRefreshLayout;
import com.chaungying.site_repairs.view.PullableListView1;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王晓赛 or 2016/7/28
 */

@ContentView(R.layout.activity_qian_dao_tong_ji)
public class TongJiActivity extends BaseActivity {


    //刷新的页数
    public int refreshPage = 1;
    //刷新每次的个数
    public static int refreshPageNum = 10;


    @ViewInject(R.id.title_back)
    private ImageView title_back;
    //支持刷新的布局
    @ViewInject(R.id.refresh_view)
    private PullToRefreshLayout layout;
    //支持刷新的listView
    @ViewInject(R.id.lv_tong_ji)
    private PullableListView1 lvTongJi;

    private final int UPDATA = 0;

    //获取到的签到数据
    ArrayList<TongJiBean.DataBean> list = new ArrayList<TongJiBean.DataBean>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA:
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    TongJiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setActionBar(R.string.signin_statistical, R.drawable.nav_return, 0);
        ProgressUtil.show(this, "加载中...");
        getSigInData();
        adapter = new TongJiAdapter(this);
        lvTongJi.setAdapter(adapter);
        layout.setOnRefreshListener(new TongJiListener(this));
//        layout.setCanPullDown(false);
        //注册加载数据之后的广播接收器
        registerBoradcastReceiver();
    }

    private void getSigInData() {
        RequestParams pamas = new RequestParams(Const.WuYe.URL_SIGN_STATISTICAL);
        pamas.setConnectTimeout(5 * 1000);
//        page=页号（默认1）&row=行数（默认50）&memberId=用户id
        pamas.addParameter("page", refreshPage);
        pamas.addParameter("row", refreshPageNum);
        pamas.addParameter("memberId", SPUtils.get(this, Const.SPDate.ID, 4512));
        x.http().post(pamas, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                TongJiBean bean = gson.fromJson(result, TongJiBean.class);
                adapter.setIsAdmin(bean.getIsAdmin());
                list = (ArrayList<TongJiBean.DataBean>) bean.getData();
                if (list != null) {
                    setBeanTag();
                    handler.sendEmptyMessage(UPDATA);
                } else {
                    T.showShort(TongJiActivity.this, "暂无数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                ProgressUtil.hide();
            }
        });

    }

    /**
     * 给数据设置标志
     */
    private void setBeanTag() {
        for (int position = 0; position < list.size(); position++) {
            if (position == 0) {
                list.get(position).setShow(true);
            }
            if (position > 0) {
                TongJiBean.DataBean bean1 = list.get(position);
                TongJiBean.DataBean upBean = list.get(position - 1);
                if (!(bean1.getSignInDate() == null ? "" : bean1.getSignInDate()).equals(upBean.getSignInDate() == null ? "" : upBean.getSignInDate())) {
                    bean1.setShow(true);
                } else {
                    bean1.setShow(false);
                }
            }
        }
    }

    @Event(R.id.title_back)
    private void back(View view) {
        finish();
    }


    /**
     * 广播接收器
     */
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播
        registerReceiver(receiverRefresh, myIntentFilter);
    }

    /**
     * 接收刷新数据的标志
     */
    private final String ACTION_NAME = "刷新数据";


    /**
     * 将result json串变为对象 添加到集合中，更新适配器
     *
     * @param result
     */
    public void jsonToBean(String result) {
        Gson gson = new Gson();
        TongJiBean bean = gson.fromJson(result, TongJiBean.class);
        List<TongJiBean.DataBean> dataBeanList = (ArrayList<TongJiBean.DataBean>) bean.getData();
        //将刷新出来的数据集合添加进去
        if (list != null) {
            if (dataBeanList != null) {
                list.addAll(dataBeanList);
                setBeanTag();
            } else {
                T.showShort(TongJiActivity.this, "没有更多数据");
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 定义接收 刷新数据的广播接收器
     */
    private BroadcastReceiver receiverRefresh = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                jsonToBean(intent.getStringExtra("json"));
            }
        }
    };

    /**
     * 取消广播
     */
    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(receiverRefresh);
        refreshPage = 0;
        if (list != null) {
            list.clear();
        }
    }
}
