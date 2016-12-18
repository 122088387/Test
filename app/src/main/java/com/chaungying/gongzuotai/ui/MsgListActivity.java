package com.chaungying.gongzuotai.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.common.view.CustomDialog;
import com.chaungying.gongzuotai.adapter.MealMsgListAdapter;
import com.chaungying.gongzuotai.adapter.RepairMsgListAdapter;
import com.chaungying.gongzuotai.bean.AllMsgBean;
import com.chaungying.gongzuotai.dbbean.OrderMealBean;
import com.chaungying.gongzuotai.dbbean.RepairBean;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author 王晓赛 or 2016/8/15
 */
@ContentView(R.layout.activity_msg_list)
public class MsgListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    MealMsgListAdapter msgListadapter;
    RepairMsgListAdapter repairMsgListAdapter;


    @ViewInject(R.id.lv_msg_list)
    ListView lv_msg_list;

    List<OrderMealBean> beanList = new ArrayList<>();
    List<RepairBean> repairBeanList = new ArrayList<>();
    int type = -100;

    String orderUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        String title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", -100);
        orderUrl = getIntent().getStringExtra("url");
        if (title != null && !title.equals("")) {
            setActionBar(title, R.drawable.nav_return, 0);
        }
        if (type == 1) {//订餐提醒
            msgListadapter = new MealMsgListAdapter(this);
            lv_msg_list.setAdapter(msgListadapter);
        } else if (type == 3) {//我的报修
            repairMsgListAdapter = new RepairMsgListAdapter(this);
            repairMsgListAdapter.setType("派工");
            lv_msg_list.setAdapter(repairMsgListAdapter);

        }


        lv_msg_list.setOnItemClickListener(this);
    }

    private void initData() {
        String userId = (int) SPUtils.get(this, Const.SPDate.ID, 4512) + "";
        if (type == 1) {
            beanList = DataSupport.where("userId=" + userId).order("logicId").find(OrderMealBean.class);
            msgListadapter.setList(beanList);
            msgListadapter.notifyDataSetChanged();
        } else if (type == 3) {
            repairBeanList = DataSupport.where("userId=" + userId)/*.order("logicId")*/.find(RepairBean.class);
            Collections.reverse(repairBeanList);
            repairMsgListAdapter.setList(repairBeanList);
            repairMsgListAdapter.notifyDataSetChanged();
        }

    }

    List<AllMsgBean.DataBean> datasBeanList = new ArrayList<>();

    /**
     * 获取抢单中心的数据
     */
    private void getRobDatas() {
        ProgressUtil.show(this, "加载中...");
        RequestParams params = new RequestParams(orderUrl.substring(0, orderUrl.lastIndexOf("=") + 1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                AllMsgBean bean = gson.fromJson(result, AllMsgBean.class);
                List<AllMsgBean.DataBean> list = bean.getData();
                datasBeanList = list;
                initListView(list);
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

    //抢单中心的列表数据
    List<RepairBean> qiang_dan_List = new ArrayList<RepairBean>();

    private void initListView(List<AllMsgBean.DataBean> list) {
        for (int i = 0; i < list.size(); i++) {
            AllMsgBean.DataBean datasBean = list.get(i);
            int type = list.get(i).getType();
            if (type == 2) {
                List<List<AllMsgBean.DataBean.DatasBean>> databeanList = datasBean.getDatas();
                qiang_dan_List = new ArrayList<RepairBean>();
                for (int j = 0; j < databeanList.size(); j++) {
                    List<AllMsgBean.DataBean.DatasBean> databeanList1 = databeanList.get(j);
                    RepairBean repairBean = new RepairBean();
                    repairBean.setLayoutid(56);
                    repairBean.setApplicationId(datasBean.getApplicationId());
                    repairBean.setType(datasBean.getType());
                    repairBean.setTitle(databeanList1.get(0).getTitle() + databeanList1.get(0).getValue());
                    repairBean.setTitle1(databeanList1.get(1).getTitle() + ":" + databeanList1.get(1).getValue());
                    repairBean.setTitle2(databeanList1.get(2).getTitle() + ":" + databeanList1.get(2).getValue());
                    repairBean.setLogicId(databeanList1.get(0).getLogicId());
                    repairBean.setRead(false);
                    repairBean.setUserId((int) SPUtils.get(this, Const.SPDate.ID, 4512));
                    repairBean.setItemtype(databeanList1.get(0).getItemtype());
                    qiang_dan_List.add(repairBean);
                }
                repairMsgListAdapter.setList(qiang_dan_List);
                repairMsgListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        if (type == 2) {//抢单中心
            repairMsgListAdapter = new RepairMsgListAdapter(this);
            repairMsgListAdapter.setList(qiang_dan_List);
            lv_msg_list.setAdapter(repairMsgListAdapter);
            getRobDatas();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (type == 3) {
            String userId = (int) SPUtils.get(this, Const.SPDate.ID, 4512) + "";
            repairBeanList = DataSupport.where("userId=" + userId)/*.order("logicId")*/.find(RepairBean.class);
            Collections.reverse(repairBeanList);
            if (repairMsgListAdapter != null) {
                repairMsgListAdapter.notifyDataSetChanged();
            }
        }
    }

    private boolean isSuccess;

    /**
     * 点击每个提醒列表中的 子项
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (type == 1) {//订餐提醒
            if (beanList != null && beanList.size() > 0) {
                beanList.get(position).setRead(true);
                int appId = beanList.get(position).getApplicationId();
                int logicId = beanList.get(position).getLogicId();
                Bundle bundle = new Bundle();
                bundle.putInt("appId", appId);
                bundle.putInt("logicId", logicId);
                bundle.putInt("type", type);
                openActivty(this, MsgListDetailsActivity.class, bundle, null);
                //更改数据库中的标志
                OrderMealBean bean = new OrderMealBean();
                bean.setRead(true);
                bean.updateAll("logicId=" + logicId);
            }
        } else if (type == 3) {//我的报修

            ////////////判断该单子是否已经被接///////////////////
            if (repairBeanList != null && repairBeanList.size() > 0) {

                final int appId = repairBeanList.get(position).getApplicationId();
                final int logicId = repairBeanList.get(position).getLogicId();

                //派工管理才能抢单(此if语句是区分派工管理的还是抢单的)
                if (repairBeanList.get(position).getCountersignTime().equals("") && repairBeanList.get(position).getDataType() == 2) {
                    final CustomDialog.Builder dialog = new CustomDialog.Builder(this);
                    dialog.setTitle("提示");
                    dialog.setMessage("是否确认接单?");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            RequestParams params = new RequestParams(Const.WuYe.URL_WORK_TASK_CORNTER_SIGN);
                            params.addParameter("logicId", logicId);
                            x.http().post(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    Log.e("接单", "接单" + result);
                                    isSuccess = true;
                                    //更改数据库中的标志
                                    RepairBean bean1 = new RepairBean();
                                    bean1.setCountersignTime("已接单");
                                    bean1.updateAll("logicId=" + logicId);

                                    //记录接单的时间，半小时后提醒该工作人员去完成该项任务
                                    remind();

                                    if (isSuccess) {
                                        isSuccess = !isSuccess;
                                        repairBeanList.get(position).setRead(true);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("appId", appId);
                                        bundle.putInt("logicId", logicId);
                                        bundle.putInt("type", type);
                                        openActivty(MsgListActivity.this, MsgListDetailsActivity.class, bundle, null);

                                        //更改数据库中的标志
                                        RepairBean bean = new RepairBean();
                                        bean.setRead(true);
                                        bean.updateAll("logicId=" + logicId);
                                    }
                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    T.showShort(MsgListActivity.this, "服务器错误");
                                }

                                @Override
                                public void onCancelled(CancelledException cex) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });
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


                } else {
                    isSuccess = true;
                }

                if (isSuccess) {
                    isSuccess = !isSuccess;
                    repairBeanList.get(position).setRead(true);
                    Bundle bundle = new Bundle();
                    bundle.putInt("appId", appId);
                    bundle.putInt("logicId", logicId);
                    bundle.putInt("type", type);
                    openActivty(this, MsgListDetailsActivity.class, bundle, null);
                    //更改数据库中的标志
                    RepairBean bean = new RepairBean();
                    bean.setRead(true);
                    bean.updateAll("logicId=" + logicId);
                }
            }
        } else if (type == 2) {
            if (qiang_dan_List.size() > 0) {
                int appId = qiang_dan_List.get(position).getApplicationId();
                int logicId = qiang_dan_List.get(position).getLogicId();
                Bundle bundle = new Bundle();
                bundle.putInt("appId", appId);
                bundle.putInt("logicId", logicId);
                bundle.putInt("type", type);
                openActivty(this, MsgListDetailsActivity.class, bundle, null);
            }
        }

    }


    /**
     * 提醒
     */
    public void remind() {
        //不进行任何特殊处理,在原生系统中可以实现
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        //通过设置Notification来使进程保持，但是一旦通知去了之后，service还是会被关闭
        Intent i = new Intent(this, AlarmService.class);
        i.setAction("com.xiaoqi.alarmmanagerdemo.MainActivity.START");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(i);
    }
}
