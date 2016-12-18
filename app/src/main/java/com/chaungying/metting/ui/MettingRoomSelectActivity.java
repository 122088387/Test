package com.chaungying.metting.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chaungying.BaseActivity;
import com.chaungying.common.constant.Const;
import com.chaungying.common.utils.SPUtils;
import com.chaungying.common.utils.T;
import com.chaungying.metting.adapter.MettingRoomSelectAdapter;
import com.chaungying.metting.bean.MettingRoomBean;
import com.chaungying.metting.bean.MettingRoomSelectBean;
import com.chaungying.metting.bean.Point;
import com.chaungying.wuye3.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 会议室预订列表界面
 *
 * @author 王晓赛 or 2016/6/29
 */
@ContentView(R.layout.activity_metting_room_select)
public class MettingRoomSelectActivity extends BaseActivity {

    @ViewInject(R.id.lv_metting_select)
    private ListView lv_metting_select;

    @ViewInject(R.id.title_back)
    private ImageView title_back;

    @ViewInject(R.id.title_menu)
    private TextView title_order;

    @ViewInject(R.id.title)
    private TextView title;


    //选择时间的框
    @ViewInject(R.id.layout_select_time)
    private LinearLayout layout_select_time;
    @ViewInject(R.id.tp_time)
    private TimePicker tp_time;
    @ViewInject(R.id.ll)
    private LinearLayout ll;
    // 分钟
    String[] minuts = new String[]{"00", "10", "20", "30", "40", "50"};
    //开始时间
    @ViewInject(R.id.start_time)
    private TextView start_time;
    //结束时间
    @ViewInject(R.id.end_time)
    private TextView end_time;
    @ViewInject(R.id.center_title)
    private TextView center_title;

    @ViewInject(R.id.tv_easy)
    private TextView tv_easy;

    //会议室的适配器
    private MettingRoomSelectAdapter adapter;

    //会议室包含的属性bean
    private List<MettingRoomSelectBean> list = new ArrayList<>();
    //控制选择时间框的显隐
    private boolean isShowSelect;
    //会议室的开放时间和结束时间
    private String startTime, endTime;
    //某个会议室的实体
    private MettingRoomBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        tp_time.setIs24HourView(true);
        // 注这里的1不是一分钟 而是minuts数组下标
//        tp_time.setCurrentMinute(0);
        //对分钟进行格式设置，设置到TimePicker中
        setNumberPickerTextSize(tp_time);
        bean = (MettingRoomBean) getIntent().getExtras().getSerializable("bean");
        title.setText(bean.getName());
        data = getIntent().getExtras().getString("data");
        getData();
        adapter = new MettingRoomSelectAdapter(this);
        lv_metting_select.setAdapter(adapter);
    }

    private MettingRoomSelectBean selectBean;

    //查询的日期
    static String data;

    private void getData() {
        ////////////////////////////联网获取数据/////////////////////
        RequestParams params = new RequestParams(Const.WuYe.URL_METTING_ROOM_SELECT);
        params.setConnectTimeout(30 * 1000);
        params.addParameter("appointmentDate", data);
        params.addParameter("meetingId", bean.getId());
        params.addParameter("userId", SPUtils.get(this, Const.SPDate.ID, 4512));
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result != null && !result.equals("")) {
                    Gson gson = new Gson();
                    selectBean = gson.fromJson(result, MettingRoomSelectBean.class);
                    //按照时间从小到大排序
                    List<MettingRoomSelectBean.DatasBean> list = selectBean.getDatas();
                    if (list.size() == 0) {
                        tv_easy.setVisibility(View.VISIBLE);
                    } else {
                        tv_easy.setVisibility(View.GONE);
                    }
                    Collections.sort(list);
                    adapter.setList(list);
                } else {
                    T.showShort(MettingRoomSelectActivity.this, "没有数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.showShort(MettingRoomSelectActivity.this, "获取数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //刷新适配器
                adapter.notifyDataSetChanged();
            }
        });
    }
    //选择时间后判断时间


    @Override
    protected void onRestart() {
        super.onRestart();
//        layout_select_time.setVisibility(View.GONE);
        getData();
    }

    /**
     * 判断时间是否重复
     */
    public boolean checkTime(long start, long end) {
        List<MettingRoomSelectBean.DatasBean> list = selectBean.getDatas();
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Point p = new Point();
            int startTime = getLongTime(list.get(i).getAppointmentBeginTime());
            int endTime = getLongTime(list.get(i).getAppointmentEndTime());
            p.x = startTime;
            p.y = endTime;
            pointList.add(p);
        }
        Collections.sort(pointList);
        for (int i = 0; i < pointList.size(); i++) {
            if (start <= pointList.get(i).x) {
                if (i == 0) {//判断比第一个时间段就小
                    if (end <= pointList.get(i).x) {
                        return true;
                    } else {
                        return false;
                    }
                } else {//有两个时间段及以上
                    if (start >= pointList.get(i - 1).y) {//与前一个时间段比较的结束时间比较
                        if (end <= pointList.get(i).x) {//结束时间小于该段时间的开始时间
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            //如果前边都不成立与最后一段时间比较
            if (i == pointList.size() - 1) {
                if (start < pointList.get(i).y) {//开始时间小于结束最后一段的结束时间，说明重复
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取时间的秒数
     *
     * @param timeStr
     * @return
     */
    public int getLongTime(String timeStr) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date date = format.parse(timeStr);
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int startTime = hour * 60 * 60 + minute * 60;
            return startTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 点击预定按钮之后
     *
     * @param view
     */
    @Event(value = R.id.title_menu)
    private void showSelectTime(View view) {
//        if (isShowSelect) {
//            int start = getLongTime(start_time.getText().toString());
//            int end = getLongTime(end_time.getText().toString());
//            if (!"开始时间".equals(start_time.getText().toString()) && !"结束时间".equals(end_time.getText().toString())) {
//                if(start < getLongTime(startTime) || end > getLongTime(endTime)){//检查是否超过开放时间
//                    T.showShort(this,"没有在开放时间内");
//                    return;
//                }
//                //检查与已选定的时间是否冲突
//                boolean isOk = checkTime(start, end);
//                if (isOk) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean",bean);
        openActivty(MettingRoomSelectActivity.this, MettingRoomOrderActivity.class, bundle, null);
//                } else {
//                    T.showShort(MettingRoomSelectActivity.this, "该时间已被预定");
//                }
//            } else {
//                T.showShort(MettingRoomSelectActivity.this, "请选择时间");
//            }
//        } else {
//            layout_select_time.setVisibility(View.VISIBLE);
//            title_order.setText("确定");
//            isShowSelect = !isShowSelect;
//        }
    }

    /**
     * 点击返回图片
     *
     * @param view
     */
    @Event(value = R.id.title_back)
    private void titleBack(View view) {
        finish();
    }

    private Calendar calendar;
    private int hourOfDay1;
    private int minute1;
    private int hourOfDay2;
    private int minute2;


    @ViewInject(R.id.tv_cancel)
    private TextView btn_cancle;
    @ViewInject(R.id.tv_ok)
    private TextView btn_ok;

    @Event(value = R.id.tv_cancel)
    private void cancle(View view) {
        ll.setVisibility(View.GONE);
    }

    private int index = 0;

    //时间框上的确定按钮
    @Event(value = R.id.tv_ok)
    private void ok(View view) {
        if (index == 1) {//选择设置开始时间的时候
            if (end_time.getText().toString().equals("结束时间")) {//结束时间显示结束时间时
                ll.setVisibility(View.GONE);
            } else {
                if (isCheck(hourOfDay1, hourOfDay2)) {//小时大于
                    ll.setVisibility(View.GONE);
                    end_time.setText("结束时间");
                } else if (hourOfDay2 == hourOfDay1) {//等于
                    if (getTime(start_time.getText().toString()).get(Calendar.MINUTE) >= getTime(end_time.getText().toString()).get(Calendar.MINUTE)) {
                        ll.setVisibility(View.GONE);
                        end_time.setText("结束时间");
                    } else {
                        ll.setVisibility(View.GONE);
                    }
                } else {//小于
                    ll.setVisibility(View.GONE);
                }
            }
            setTime(start_time, hourOfDay1, minute1);
        } else if (index == 2) {//设置结束时间的时候
            if (isCheck(hourOfDay1, hourOfDay2)) {//小时大于
                T.showLong(MettingRoomSelectActivity.this, "结束时间不能小于开始时间");
                end_time.setText("结束时间");
            } else if (hourOfDay2 == hourOfDay1) {//等于
                if (getTime(start_time.getText().toString()).get(Calendar.MINUTE) >= minute2) {
                    T.showLong(MettingRoomSelectActivity.this, "结束时间不能小于或等于时间");
                    end_time.setText("结束时间");
                } else {
                    setTime(end_time, hourOfDay2, minute2);
                    ll.setVisibility(View.GONE);
                }
            } else {
                setTime(end_time, hourOfDay2, minute2);
                ll.setVisibility(View.GONE);
            }
        }
    }

    private void setTime(TextView tvShow, int hour, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hour, minutes);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        tvShow.setText(format.format(calendar.getTimeInMillis()));
    }

    /**
     * 将时间字符串，变日对象
     *
     * @param time 时间字符串
     * @return 日历对象
     */
    private Calendar getTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 开始时间
     */
    @Event(value = R.id.start_time)
    private void startTime(View view) {
        index = 1;
        calendar = Calendar.getInstance();
        //让时间对话框所在的布局显示出来
        ll.setVisibility(View.VISIBLE);
        center_title.setText("开始时间");
        if ("开始时间".equals(start_time.getText().toString())) {
            tp_time.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            tp_time.setCurrentMinute(calendar.get(Calendar.MINUTE) / 10);
        } else {
            tp_time.setCurrentHour(getTime(start_time.getText().toString()).get(Calendar.HOUR_OF_DAY));
            tp_time.setCurrentMinute(getTime(start_time.getText().toString()).get(Calendar.MINUTE) / 10);
        }
        hourOfDay1 = tp_time.getCurrentHour();
        minute1 = setMinuteType(tp_time.getCurrentMinute());
        tp_time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (index == 1) {
                    hourOfDay1 = hourOfDay;
                    minute1 = setMinuteType(minute);
                }
            }
        });
    }

    /**
     * 结束时间
     */
    @Event(value = R.id.end_time)
    private void endTime(View view) {
        index = 2;
        calendar = Calendar.getInstance();
        center_title.setText("结束时间");
        if (start_time.getText().toString().equals("开始时间")) {
            T.showShort(this, "请选择开始时间");
            return;
        }
        ll.setVisibility(View.VISIBLE);
        tp_time.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        tp_time.setCurrentMinute(calendar.get(Calendar.MINUTE) / 10);
        hourOfDay2 = tp_time.getCurrentHour();
        minute2 = setMinuteType(tp_time.getCurrentMinute());
        tp_time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (index == 2) {
                    hourOfDay2 = hourOfDay;
                    minute2 = setMinuteType(minute);
                }
            }
        });
    }

    private int setMinuteType(int minute) {
        int minutes = 0;
        switch (minute) {
            case 0:
                minutes = 0;
                break;
            case 1:
                minutes = 10;
                break;
            case 2:
                minutes = 20;
                break;
            case 3:
                minutes = 30;
                break;
            case 4:
                minutes = 40;
                break;

            case 5:
                minutes = 50;
                break;
        }
        return minutes;
    }


    /**
     * num1是否大于num2
     *
     * @param num1
     * @param num2
     * @return 大于返回true，否则false
     */

    public boolean isCheck(int num1, int num2) {
        return num1 > num2 ? true : false;
    }


    /**
     * 得到timePicker里面的android.widget.NumberPicker组件
     * （有两个android.widget.NumberPicker组件--hour，minute）
     *
     * @param viewGroup
     * @return
     */

    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;

        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    /**
     * 查找timePicker里面的android.widget.NumberPicker组件 ，并对其进行时间间隔设置
     *
     * @param viewGroup TimePicker timePicker
     */
    private void setNumberPickerTextSize(ViewGroup viewGroup) {
        List<NumberPicker> npList = findNumberPicker(viewGroup);
        if (null != npList) {
            for (NumberPicker mMinuteSpinner : npList) {
                // System.out.println("mMinuteSpinner.toString()="+mMinuteSpinner.toString());
                if (mMinuteSpinner.toString().contains("id/minute")) {// 对分钟进行间隔设置
                    // android.widget.NumberPicker{42af7a60 VFED.... ......I.
                    // 0,0-0,0 #1020499 android:id/minute}
                    mMinuteSpinner.setMinValue(0);
                    mMinuteSpinner.setMaxValue(minuts.length - 1);//改变分钟的最小和最大显示
                    mMinuteSpinner.setDisplayedValues(minuts); // 分钟显示数组
                }
                // 对小时进行间隔设置 使用
                // if(mMinuteSpinner.toString().contains("id/hour")){}即可
                // android.widget.NumberPicker{42af7a60 VFED.... ......I.
                // 0,0-0,0 #1020499 android:id/hour}
            }
        }
    }
}
