package com.chaungying.common.view;

import android.app.Activity;
import android.widget.PopupWindow;

import com.chaungying.common.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王晓赛 or 2016/7/22
 *         <p/>
 *         时间选择底部弹出框
 */
public class TimeDialogPopWindow extends PopupWindow {
    private Activity mContext;
    private TimePickerView pvTime;
    public SendStrListner sendStrListner;

    /**
     * 对分钟的间隔设置，不设置的话，默认是1
     *
     * @param minuts_interval
     */
    public void setMinuts_interval(int minuts_interval) {
        pvTime = new TimePickerView(mContext, TimePickerView.Type.HOURS_MINS);
        pvTime.setMinutsInterval(minuts_interval);
        pvTime.show();
    }

    public TimeDialogPopWindow(Activity context) {
        super(context);
        mContext = context;
    }

    public void initTimePicker() {
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                sendStrListner.sendToActivityStr(getTime(date));
            }
        });

    }

    public interface SendStrListner {
        void sendToActivityStr(String string);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
}
