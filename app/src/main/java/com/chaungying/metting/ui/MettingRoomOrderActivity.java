package com.chaungying.metting.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.common.view.DateDialogPopWindow;
import com.chaungying.common.view.TimeDialogPopWindow;
import com.chaungying.metting.bean.MettingRoomBean;
import com.chaungying.metting.view.ProgressUtil;
import com.chaungying.modues.login.bean.HttpRequestBase;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author 王晓赛 or 2016/6/30
 */
@ContentView(R.layout.activity_metting_room_order)
public class MettingRoomOrderActivity extends BaseActivity {

    //返回按钮
    @ViewInject(R.id.title_back)
    private ImageView ivBack;
    //预定按钮
    @ViewInject(R.id.tv_order)
    private TextView tvOrder;
    //会议室名称
    @ViewInject(R.id.metting_room_name)
    private TextView order_name;
    //预约日期
    @ViewInject(R.id.order_data)
    private TextView order_data;

    @ViewInject(R.id.mettingRoomOrder_beginTime)
    private TextView tvBeginTime;

    @ViewInject(R.id.mettingRoomOrder_endTime)
    private TextView tvEndTime;

    //预定单位
    @ViewInject(R.id.order_group)
    private EditText order_group;
    //联系人
    @ViewInject(R.id.lian_xi_ren)
    private EditText et_person;
    //手机号
    @ViewInject(R.id.phone_num)
    private EditText et_phone_num;
    //备注
//    @ViewInject(R.id.bei_zhu)
//    private EditText et_bei_zhu;

    private String openTime, closeTime;
    //会议室id
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
    }

    /**
     * 初始化界面的数据
     */
    private void initData() {
        setActionBar(R.string.metting_room_order, R.drawable.nav_return, 0);
        MettingRoomBean bean = (MettingRoomBean) getIntent().getExtras().getSerializable("bean");
        order_name.setText(bean.getName());
        order_data.setText(MettingRoomSelectActivity.data);
        openTime = bean.getBeginTime();
        closeTime = bean.getEndTime();
        id = bean.getId();
        if (!SPUtils.get(this, Const.SPDate.USER_COMPANY, "").equals("")) {
            order_group.setText((String) SPUtils.get(this, Const.SPDate.USER_COMPANY, ""));
        }
        et_person.setText((String) SPUtils.get(this, Const.SPDate.USER_NAME, ""));
        et_phone_num.setText((String) SPUtils.get(this, Const.SPDate.LONGING_NAME, ""));
    }


    /**
     * 预约按钮
     */
    @Event(value = R.id.tv_order)
    private void order(View view) {
        String startTime = tvBeginTime.getText().toString();
        String endTime = tvEndTime.getText().toString();
        //判断结束时间是否大于开始时间
        if (startTime.equals("开始时间") || endTime.equals("结束时间") || StringToMinute(startTime) >= StringToMinute(endTime)) {
            T.showShort(this, "请选择正确的时间");
            return;
        }
//        //判断是否在开放时间内
//        if(StringToMinute(startTime) < StringToMinute(openTime) || StringToMinute(endTime)>StringToMinute(closeTime)){
//            T.showShort(this,"没有在开放时间内");
//            return;
//        }
        //手机号和联系人不能为空
        if (StringUtils.isEmpty(et_person.getText().toString()) || StringUtils.isEmpty(et_phone_num.getText().toString())) {
            T.showShort(this, "手机号、联系人不能为空");
            return;
        }

        ProgressUtil.show(this, "正在提交...", false);
        //本地判断如果没有问题，提交服务器进行检查
        RequestParams params = new RequestParams(Const.WuYe.URL_METTING_ROOM_ORDER);
        params.addParameter("appointmentDate", MettingRoomSelectActivity.data);
        params.addParameter("groupName", order_group.getText().toString());//预定的单位
        params.addParameter("meetingId", id);//会议室的id
        params.addParameter("appointmentBeginTime", startTime);
        params.addParameter("appointmentEndTime", endTime);
        params.addParameter("userName", et_person.getText().toString());
        params.addParameter("phone", et_phone_num.getText().toString());
        int userId = (int) SPUtils.get(this, Const.SPDate.ID, 0);
        params.addParameter("operaterId", userId);
        String userName = (String) SPUtils.get(this, Const.SPDate.USER_NAME, "");
        params.addParameter("operaterName", userName);
//        params.addParameter("remarks", et_bei_zhu.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                HttpRequestBase base = gson.fromJson(result, HttpRequestBase.class);
                if (base.respCode == 1001) {
                    T.showShort(MettingRoomOrderActivity.this, "预定成功");
//                    Intent intent = new Intent();
//                    intent.setClass(MettingRoomOrderActivity.this, MettingRoomActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
                    finish();
                } else {
                    T.showShort(MettingRoomOrderActivity.this, base.respMsg);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showShort(MettingRoomOrderActivity.this, "提交失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                ProgressUtil.close();
            }
        });
    }

    /**
     * 将字符串转变为分钟
     *
     * @param timeStr
     * @return
     */
    private int StringToMinute(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        int time = 0;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(timeStr));
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            time = hour * 60 + minute;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 点击日期弹出选择器
     *
     * @param view
     */
    @Event(value = R.id.order_data)
    private void dateSelectedClick(View view) {
        // TODO: 2016/9/9 0009 可以选择日期
        DateDialogPopWindow timePop = new DateDialogPopWindow(this);
        timePop.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        timePop.sendStrListner = new DateDialogPopWindow.SendStrListner() {
            @Override
            public void sendToActivityStr(String string) {
                order_data.setText(string);
            }
        };

    }

    /**
     * 点击开始时间
     *
     * @param view
     */
    @Event(value = R.id.mettingRoomOrder_beginTime)
    private void timeSelectBegin(View view) {
        set(tvBeginTime);
    }

    private void set(final TextView textView) {
        final TimeDialogPopWindow timePop = new TimeDialogPopWindow(this);
        timePop.setMinuts_interval(10);
        timePop.initTimePicker();
        timePop.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        timePop.sendStrListner = new TimeDialogPopWindow.SendStrListner() {
            @Override
            public void sendToActivityStr(String string) {
                textView.setText(string);
                textView.setTextColor(getResources().getColor(R.color.include_title));
                timePop.dismiss();
            }
        };
    }

    /**
     * 点击结束时间
     *
     * @param view
     */
    @Event(value = R.id.mettingRoomOrder_endTime)
    private void timeSelectEnd(View view) {
        set(tvEndTime);
    }

    /**
     * 返回按钮
     *
     * @param view
     */
    @Event(value = R.id.title_back)
    private void back(View view) {
        finish();
    }
}
