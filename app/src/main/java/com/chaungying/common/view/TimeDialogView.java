package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.common.bean.ConfigBean;
import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/7/22
 */
public class TimeDialogView extends FrameLayout {

    TextView timeSelect,inputShow;
    private ConfigBean.ItemsBean itemsBean;

    private int isRequired;

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public TimeDialogView(Context context) {
        this(context,null);
    }

    public TimeDialogView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.time_dialog_view,this);
        timeSelect = (TextView) findViewById(R.id.tv_time_select);
        inputShow = (TextView) findViewById(R.id.tv_show);
    }

    /**
     * 设置选择完毕之后返回的时间
     * @param time
     */
    public void setTimeShow(String time){
        inputShow.setText(time);
    }

    public String getTimeShow(){
        return  inputShow.getText().toString();
    }
    /**
     * 初始化数据
     */
    public void initView(String title) {
        timeSelect.setText(title);
    }

    public void setHint(String strHint){
        inputShow.setHint(strHint);
    }
}
