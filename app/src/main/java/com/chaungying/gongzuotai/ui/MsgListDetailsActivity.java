package com.chaungying.gongzuotai.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Base;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.chaungying.zixunjieda.bean.ShowDetailsBean;
import com.chaungying.zixunjieda.view.ZiXunBuildView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 王晓赛 or 2016/7/14
 *         订餐消息详情、报修消息详情界面
 */
@ContentView(R.layout.activity_msg_list_details)
public class MsgListDetailsActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_back)
    private ImageView title_back;

    //添加下拉的线性布局
    @ViewInject(R.id.ll_add_view)
    private LinearLayout llAddView;

    @ViewInject(R.id.msg_list_details_btn)
    private AppCompatButton msg_list_details_btn;
    public static int logicId = -1;
    private int type = -1;
    private int appId = -1;

    private String userId = "-1";
    private int states = -1;

    private ShowDetailsBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        setActionBar("详情", R.drawable.nav_return, 0);
        msg_list_details_btn.setOnClickListener(this);

        ProgressUtil.show(this, "加载中...");
        appId = getIntent().getExtras().getInt("appId");
        logicId = getIntent().getExtras().getInt("logicId");
        type = getIntent().getExtras().getInt("type");


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llAddView.removeAllViews();
        msg_list_details_btn.setVisibility(View.GONE);
        getDatas();
    }

    private void getDatas() {
        RequestParams params = new RequestParams(Const.WuYe.URL_ZI_XUN_DETAILS);
        params.setConnectTimeout(30 * 1000);
        params.addParameter("appId", appId);
        params.addParameter("logicId", logicId);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ZiXunBuildView buildView1 = new ZiXunBuildView(MsgListDetailsActivity.this, llAddView);
                buildView1.buildView(result, false);
                ProgressUtil.close();
                Gson gson = new Gson();
                data = gson.fromJson(result, ShowDetailsBean.class);
                LogUtil.e(data.toString());
                userId = data.getUserId();
                states = data.getStates();
                if (type == 2) {//抢单中心
                    msg_list_details_btn.setText("我要抢单");
                    msg_list_details_btn.setVisibility(View.VISIBLE);
                } else if (type == 1) {//订餐提醒
                    if (SPUtils.get(MsgListDetailsActivity.this, Const.SPDate.ID, -1).equals(userId)
                            && states == 1) {
                        msg_list_details_btn.setText("完成订单");
                        msg_list_details_btn.setVisibility(View.VISIBLE);
                    }
                } else if (type == 3) {//我的报修
                    if (states == 0) {//不显示按钮
                        msg_list_details_btn.setVisibility(View.GONE);
                    } else if (states == 1) {
                        int userId = (int) SPUtils.get(x.app(), Const.SPDate.ID, 0);
                        if (data.getRepairUserId() != -1 && data.getRepairUserId() == userId) {
                            msg_list_details_btn.setText("报修处理");
                            msg_list_details_btn.setVisibility(View.VISIBLE);
                        }
                    } else if (states == 2) {
                        msg_list_details_btn.setText("报修处理");
                        msg_list_details_btn.setVisibility(View.VISIBLE);
                    }
                }
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

    /**
     * 返回到九宫格主界面
     */
    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msg_list_details_btn:
                ProgressUtil.show(this, "");
                if (type == 2) {
//                userId=抢单人id&logicId=逻辑表数据id
                    RequestParams params = new RequestParams(Const.WuYe.URL_WORK_ORDER_DETAILS_UPLOAD);
                    params.addParameter("userId", SPUtils.get(this, Const.SPDate.ID, -1));
                    params.addParameter("logicId", logicId);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            Base base = gson.fromJson(result, Base.class);
                            if (base.respCode == 1001) {
                                finish();
                                T.showShort(MsgListDetailsActivity.this, "抢单成功！");
                            } else {
                                T.showShort(MsgListDetailsActivity.this, "抢单失败！");
                            }
                            ProgressUtil.close();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            ProgressUtil.close();
                            T.showShort(MsgListDetailsActivity.this, "抢单失败！");
                            finish();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } else if (type == 1) {
                    RequestParams params = new RequestParams(Const.WuYe.URL_FINISH_OREDER);
                    params.addParameter("appId", appId);
                    params.addParameter("logicId", logicId);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            Base base = gson.fromJson(result, Base.class);
                            if (base.respCode == 1001) {
                                T.showShort(MsgListDetailsActivity.this, "确认成功！");
                                finish();
                            } else {
                                T.showShort(MsgListDetailsActivity.this, "确认失败！");
                            }
                            ProgressUtil.close();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            ProgressUtil.close();
                            T.showShort(MsgListDetailsActivity.this, "确认失败！");
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } else if (type == 3) {
                    int userId = (int) SPUtils.get(x.app(), Const.SPDate.ID, 0);
                    if (data.getRepairUserId() != -1 && data.getRepairUserId() == userId) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("states", states);
                        bundle.putInt("logicId", logicId);
                        openActivty(this, DealWithOrderActivity.class, bundle, null);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("states", states);
                        bundle.putInt("grabberId", userId);
                        bundle.putInt("logicId", logicId);
                        openActivty(this, DealWithOrderActivity.class, bundle, null);
                    }
                }
                break;
        }
    }
}
