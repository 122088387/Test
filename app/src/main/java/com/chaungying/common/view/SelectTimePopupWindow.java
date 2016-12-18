package com.chaungying.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chaungying.common.utils.T;
import com.chaungying.common.utils.date.DateUtil;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class SelectTimePopupWindow extends PopupWindow implements View.OnClickListener {


    private Button btn_ok;
    private View mTimeView;
    private LinearLayout ll_time;
    Activity mContext;
    //显示到列表上的时间字符串
    StringBuffer strBuf = new StringBuffer();
    //点击确定后
    SendStrListner listnerSenStr;

    public void setListnerSenStr(SendStrListner listnerSenStr) {
        this.listnerSenStr = listnerSenStr;
    }

    public SelectTimePopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTimeView = inflater.inflate(R.layout.time_layout, null);
        ll_time = (LinearLayout) mTimeView.findViewById(R.id.ll_time);
        btn_ok = (Button) mTimeView.findViewById(R.id.btn_ok);
        initView(context);


        //确定按钮
        btn_ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //对最后提交的时间进行处理
                if("".equals(riqi) || "".equals(h) || "".equals(m) || "".equals(h1) || "".equals(m1)){
                    dismiss();
                    return;
                }
                strBuf.append(riqi + "  ").append(h).append(":").append(m).append("  ").append(Integer.parseInt(h1)).append(":").append(m1);
                T.showShort(mContext,strBuf.toString());
                listnerSenStr.sendToActivityStr(strBuf.toString());
                //销毁弹出框
                dismiss();
            }
        });


        //设置按钮监听，Activity中传过来点击该按钮之后要做的事情
//        btn_ok.setOnClickListener(itemsOnClick);
//        btn_take_photo.setOnClickListener(itemsOnClick);

        setPupopStyle(context);
    }

    public interface SendStrListner{
        void sendToActivityStr(String string);
    }

    //设置弹出框的样式
    private void setPupopStyle(Activity context) {

        //设置SelectPicPopupWindow的View
        this.setContentView(mTimeView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setHeight(mContext.getWindowManager().getDefaultDisplay().getHeight() / 15 * 5);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xffffff);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.home_entry_item_pressed));
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mTimeView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
//                int height = mTimeView.findViewById(R.id.pop_layout).getTop();
//                int y=(int) event.getY();
//                if(event.getAction()== MotionEvent.ACTION_UP){
//                    if(y<height){
//                        dismiss();
//                    }
//                }
                return true;
            }
        });
    }

    List<TextView> list_tv = new ArrayList<TextView>();
    List<String> list = new ArrayList<String>();

    List<LinearLayout> list_ll = new ArrayList<LinearLayout>();

    private void initView(Activity context) {
        for (int i = 0; i < 3; i++) {
            LinearLayout ll_time1 = new LinearLayout(context);
            LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ll_time1.setLayoutParams(params);
            ll_time1.setOrientation(LinearLayout.VERTICAL);
            ll_time1.setGravity(Gravity.CENTER_HORIZONTAL);
            //添加到布局集合中
//            list_ll.add(ll_time1);
            Date date = new Date();
            String dateStr = DateUtil.getDate(date);

            if (i == 0) {
                for (int j = 0; j < 7; j++) {
                    TextView tv = new TextView(context);
                    tv.setPadding(0, 2, 0, 2);
                    if (j == 0) {
                        tv.setText("今天 " + dateStr);
                    } else if (j == 1) {
                        tv.setText("明天 " + dateStr);
                    } else if (j == 2) {
                        tv.setText("后天 " + dateStr);
                    } else {
                        tv.setText(DateUtil.getWeek(date) + " " + dateStr);
                    }
                    tv.setId(j);
                    list_tv.add(tv);
                    date = DateUtil.addDay(date, 1);
                    dateStr = DateUtil.getDate(date);
                    ll_time1.addView(tv);
                }
                //将第一个布局添加到最大布局中  并存放到集合当中
                ll_time.addView(ll_time1);
                list_ll.add(ll_time1);
                //添加一条灰色的线
                addLine(context);
                continue;
            }

            //将剩下的两个添加到布局中，并存放到布局集合当中
            ll_time.addView(ll_time1);
            list_ll.add(ll_time1);
            if (i == 1) {
                addLine(context);
                ll_time1.addView(tipTextView(context, "请选择日期"));
            } else if (i == 2) {
                ll_time1.addView(tipTextView(context, "请选择开始时间"));
            }
        }
        //给第一列设置监听
        for (int i = 0; i < list_tv.size(); i++) {
            list_tv.get(i).setOnClickListener(this);
        }
    }

    private TextView tipTextView(Activity context, String str) {
        TextView riqiTip = new TextView(context);
        riqiTip.setText(str);
        return riqiTip;
    }

    private void addLine(Activity context) {
        TextView textView = new TextView(context);
        LayoutParams paramsText = new LayoutParams(1, LayoutParams.MATCH_PARENT);
        textView.setBackgroundResource(R.color.huise2);
        textView.setLayoutParams(paramsText);
        ll_time.addView(textView);
    }

    ArrayList<String> two_time_list = new ArrayList<String>();

    //设置第二列数据的监听回调
    HourView.OneTextListener listener = new HourView.OneTextListener() {
        @Override
        public void sendStr(int time) {

            two_time_list.clear();
//            ToastShow.toastShow(mContext, time + "");//08:30
            //添加第三列布局的数据
            initTwoTime(time);
            list_ll.get(2).removeAllViews();
            LinearLayout l2 = list_ll.get(2);
            HourView twoHourView = new HourView(mContext);
            twoHourView.setTAG(2);
            twoHourView.setOnListener(threeLieListener);
            twoHourView.setList(two_time_list);
            l2.addView(twoHourView);
            ll_time.postInvalidate();
        }
    };
    //设置第三列数据的监听回调 ，为了得到第三列的时间
    HourView.OneTextListener threeLieListener = new HourView.OneTextListener() {
        @Override
        public void sendStr(int date) {
            h1 = String.valueOf(date / 60);
            m1 = String.valueOf(date % 60);
            if(m1.length() == 1){
                m1+="0";
            }
        }
    };
    private String h = "";
    private String m = "";
    private String h1 = "";
    private String m1 = "";

    private void initTwoTime(int time) {//570  780
        h = String.valueOf(time / 60);
        m = String.valueOf(time % 60);
        if(m.length() == 1){
            m+="0";
        }
        time = time + 3 * 60;
        Calendar c = Calendar.getInstance();
        do {
            int hour = time / 60;
            int minute = time % 60;
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            String str = DateUtil.getTimeHH_MM(c.getTime());
            two_time_list.add(str);
            time += 30;
        } while (time <= 21 * 60);
    }

    private int totalShowDayNum = 7;
    private String riqi = "";

    //对第一列布局的监听事件
    @Override
    public void onClick(View v) {
        list.clear();
        //清空第三列布局中的View
        list_ll.get(2).removeAllViews();
        list_ll.get(2).addView(tipTextView(mContext, "请选择开始时间"));
        //第二列布局
        LinearLayout l = null;
        switch (v.getId()) {
            case 0:
                updateOneTimeView(0);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                updateOneTimeView(1);
                break;
        }
        //设置字体颜色
        for (int i = 0; i < totalShowDayNum; i++) {
            if (v.getId() == i) {
                list_tv.get(i).setTextColor(Color.RED);
                riqi = list_tv.get(i).getText().toString();
            } else {
                list_tv.get(i).setTextColor(0xff7f7f7f);
            }
        }
    }

    // 根据选的是今天还是之后的数据加载不同的数据  hourIndex为0  代表今天    hourIndex为1 其他代表其他天数
    private void updateOneTimeView(int hourIndex) {
        LinearLayout l;
        boolean isSet =  initHour(hourIndex);
        list_ll.get(1).removeAllViews();
        if(isSet == false){
            T.showLong(mContext,"报修时间段为8:00-21:00");
            list_ll.get(1).addView(tipTextView(mContext,"请选择日期"));
            return;
        }
        l = list_ll.get(1);
        HourView view = new HourView(mContext);
        view.setTAG(1);
        view.setList(list);
        l.addView(view);
        //给第二列布局设置监听
        view.setOnListener(listener);
        ll_time.postInvalidate();
    }

    public boolean initHour(int day) {//8  -  18
        if (day == 0) {
            Date date = new Date();
            Calendar c = Calendar.getInstance();
            long timeNow = c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE);
            long timeBa = 8 * 60;
            long timeShiBa = 18 * 60;
            long setTime = 0;
            String str = "";
            int hour = 0;
            int minute = 0;
            if (timeNow > timeBa && timeNow < timeShiBa) {
                setTime = timeNow + 30 - timeNow % 30;
                hour = (int) (setTime / 60); //150
                minute = (int) (setTime % 60);
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                str = DateUtil.getTimeHH_MM(c.getTime());//9:00
                list.add(str);
                while (setTime < 18 * 60) {
                    setTime += 30;
                    hour = (int) (setTime / 60); //150
                    minute = (int) (setTime % 60);
                    c.set(Calendar.HOUR_OF_DAY, hour);
                    c.set(Calendar.MINUTE, minute);
                    str = DateUtil.getTimeHH_MM(c.getTime());//9:00
                    list.add(str);
                }
            } else if(timeNow <= timeBa){
                setTime = 8 * 60;
                while (setTime <= 18 * 60) {
                    hour = (int) (setTime / 60); //150
                    Log.e("time1", hour + "");
                    minute = (int) (setTime % 60);
                    setTime += 30;
                    c.set(Calendar.HOUR_OF_DAY, hour);
                    c.set(Calendar.MINUTE, minute);
                    str = DateUtil.getTimeHH_MM(c.getTime());//9:00
                    list.add(str);
                }
            }else{
                return false;
            }
        } else {
            int setTime1 = 8 * 60;
            Calendar c = Calendar.getInstance();
            String str = "";
            int hour1 = 0;
            int minute1 = 0;
            int setTime = 0;
            while (setTime1 <= 18 * 60) {
                hour1 = (int) (setTime1 / 60); //150
                Log.e("time", hour1 + "");
                minute1 = (int) (setTime1 % 60);
                setTime1 += 30;
                c.set(Calendar.HOUR_OF_DAY, hour1);
                c.set(Calendar.MINUTE, minute1);
                str = DateUtil.getTimeHH_MM(c.getTime());//9:00
                list.add(str);
            }
        }
        return true;
    }


}
