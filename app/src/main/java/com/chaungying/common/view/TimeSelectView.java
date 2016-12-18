package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.common.bean.ConfigBean;
import com.chaungying.common.bean.SubtitlesItem;
import com.chaungying.wuye3.R;

import java.util.ArrayList;

/**
 * @author 王晓赛 or 2016/7/19
 */
public class TimeSelectView extends FrameLayout{

    TextView timeSelect,timeShow;
    private ConfigBean.ItemsBean itemsBean;

    public TimeSelectView(Context context) {
        this(context,null);
    }

    public TimeSelectView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.time_select_view,this);
        timeSelect = (TextView) findViewById(R.id.tv_time_select);
        timeShow = (TextView) findViewById(R.id.tv_show_result);
    }

    public void setItemsBean(ConfigBean.ItemsBean itemsBean) {
        this.itemsBean = itemsBean;
        initView();
    }

    /**
     * 设置选择完毕之后返回的时间
     * @param time
     */
    public void setTimeShow(String time){
        timeShow.setText(time);
    }
    ArrayList<SubtitlesItem> list = new ArrayList<SubtitlesItem>();
    /**
     * 初始化数据
     */
    private void initView() {
        timeSelect.setText(itemsBean.getTitle());
        list= (ArrayList<SubtitlesItem>) itemsBean.getSubtitles();
    }

    public ArrayList<SubtitlesItem> getList() {
        return list;
    }
}
