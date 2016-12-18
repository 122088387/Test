package com.chaungying.common.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaungying.common.utils.date.DateUtil;
import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class HourView extends FrameLayout implements View.OnClickListener{
    Context mContext;

    //第几个布局的标志,为了表示是否发送setTime监听
    private int TAG;

    public void setTAG(int TAG) {
        this.TAG = TAG;
    }

    LinearLayout ll_hour;
    List<String> list;

    public void setList( List<String> list) {
        this.list = list;
        initView();
    }

    public HourView(Context context) {
        this(context,null);

    }


    public HourView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HourView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }
    List<TextView> listTextView = new ArrayList<TextView>();
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hour_layout,this);
        ll_hour = (LinearLayout) view.findViewById(R.id.ll_hour);
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(mContext);
            tv.setPadding(0,2,2,0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setText(list.get(i));
            tv.setId(i);
            tv.setOnClickListener(this);
            listTextView.add(tv);
            ll_hour.addView(tv);
        }
    }

    OneTextListener listener;
    public void setOnListener(OneTextListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(v instanceof  TextView){
            String str = ((TextView) v).getText().toString();
//            Log.e("str","=="+str+"==");
            if(str != null){
                int hour = DateUtil.getHour(str);
                int minute = DateUtil.getMinute(str);
                int setTime =  hour * 60 + minute;
                //第二列数据点击时点击的数据通过回调发送过去
                if(TAG == 1){
                    listener.sendStr(setTime);
                }
                if(TAG == 2){
                    listener.sendStr(setTime);
                }
            }
        }
        for (int i = 0; i < listTextView.size(); i++) {
            if(((TextView) v).getId() == i){
                listTextView.get(i).setTextColor(Color.RED);
            }else{
                listTextView.get(i).setTextColor(0xff7f7f7f);
            }
        }

    }
    //点击第一列时间的监听接口
    public interface OneTextListener{
        void sendStr(int date);
    }
}
