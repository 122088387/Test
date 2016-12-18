package com.chaungying.common.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chaungying.common.pickerview.view.TimePickerView;
import com.chaungying.wuye3.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 王晓赛 or 2016/7/22
 *
 * 日期选择底部弹出框
 */
public class DateDialogPopWindow extends PopupWindow implements View.OnClickListener{
    private Activity mContext;
    private View mTimeView;
    private TextView tvCancle,tvOk;
    private DatePicker datePicker;
    private Calendar calendar;
    public SendStrListner sendStrListner;

    private TimePickerView pvTime;

    public DateDialogPopWindow(Activity context) {
        super(context);
        mContext = context;
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mTimeView = inflater.inflate(R.layout.date_dialog_pop_window, null);
//        tvCancle = (TextView) mTimeView.findViewById(R.id.tv_cancel);
//        tvOk = (TextView) mTimeView.findViewById(R.id.tv_ok);
//        datePicker = (DatePicker) mTimeView.findViewById(R.id.date_picker);
//        tvOk.setOnClickListener(this);
//        tvCancle.setOnClickListener(this);
//        setPupopStyle(context);
        //初始化日期选择器 的日期
//        calendar = Calendar.getInstance();
//        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year,
//                                      int monthOfYear, int dayOfMonth) {
//                // 获取一个日历对象，并初始化为当前选中的时间
//                calendar.set(year, monthOfYear, dayOfMonth);
//
//            }
//        });
        pvTime = new TimePickerView(mContext, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.show();
        initTimePicker();
    }

    public void initTimePicker() {
//        timePicker.setIs24HourView(true);
//        timePicker.setCurrentHour(Calendar.HOUR_OF_DAY);
//        timePicker.setCurrentMinute(Calendar.MINUTE);
//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                calendar.set(Calendar.MINUTE,minute);
//            }
//        });
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        // 时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
//                tvTime.setText(getTime(date));
                sendStrListner.sendToActivityStr(getTime(date));
            }
        });
    }

    private void setPupopStyle(Context context) {
        //设置SelectPicPopupWindow的View
        this.setContentView(mTimeView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        this.setHeight(mContext.getWindowManager().getDefaultDisplay().getHeight() / 15 * 5);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.home_entry_item_pressed));
        this.setAnimationStyle(R.style.AnimBottom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
            break;
            case R.id.tv_ok:
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy年MM月dd日");
                String date = sdf.format(calendar.getTime());
                Toast.makeText(mContext,date, Toast.LENGTH_SHORT).show();
                sendStrListner.sendToActivityStr(date);
                dismiss();
            break;
        }
    }
    public interface SendStrListner{
        void sendToActivityStr(String string);
    }
    public static String getTime(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
