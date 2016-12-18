package com.chaungying.common.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author 王晓赛 or 2016/8/23
 */
public class TimePeriod extends FrameLayout implements View.OnClickListener {
    TextView startTv, endTv;
    private Context mContext;

    public TimePeriod(Context context) {
        this(context, null);
    }

    public TimePeriod(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePeriod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.time_period, this);
        startTv = (TextView) view.findViewById(R.id.mettingRoomOrder_beginTime);
        endTv = (TextView) view.findViewById(R.id.mettingRoomOrder_endTime);
        startTv.setOnClickListener(this);
        endTv.setOnClickListener(this);
    }

    public String getTime(){
        return startTv.getText().toString()+"-"+endTv.getText().toString();
    }

    public boolean checkTime() {
        String startTime = startTv.getText().toString();
        String endTime = endTv.getText().toString();
        if (!startTime.equals("开始时间") && !endTime.equals("结束时间")) {
            if (StringToMinute(startTime) >= StringToMinute(endTime)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mettingRoomOrder_beginTime:
                set(startTv);
                break;
            case R.id.mettingRoomOrder_endTime:
                set(endTv);
                break;

        }
    }

    private void set(final TextView textView) {
        final TimeDialogPopWindow timePop = new TimeDialogPopWindow((Activity) mContext);
        timePop.setMinuts_interval(10);
        timePop.initTimePicker();
        timePop.showAtLocation(((Activity) mContext).findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        timePop.sendStrListner = new TimeDialogPopWindow.SendStrListner() {
            @Override
            public void sendToActivityStr(String string) {
                textView.setText(string);
                textView.setTextColor(getResources().getColor(R.color.include_title));
                timePop.dismiss();
            }
        };
    }
}
