package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaungying.common.bean.ConfigBean;
import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/7/27
 */
public class DownPopWindowItemView extends RelativeLayout {

    private TextView tv;
    private ConfigBean.ItemsBean itemsBean;
    //为了筛选时使用
    private int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ConfigBean.ItemsBean getItemsBean() {
        return itemsBean;
    }

    public void setItemsBean(ConfigBean.ItemsBean itemsBean) {
        this.itemsBean = itemsBean;
    }

    public DownPopWindowItemView(Context context) {
        this(context,null);
    }

    public DownPopWindowItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DownPopWindowItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.down_pop_window_item_view,this);
        tv = (TextView) findViewById(R.id.tv_down_pop);
    }
    public void setText(String title){
        tv.setText(title);
    }

    public String getTitle(){
        return tv.getText().toString();
    }
}
