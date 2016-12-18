package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/6/25
 */
public class JiLianView extends FrameLayout{

    TextView tv,tv_show,tv_show_id,jilian_select_id;
    //级联中的排序
    private int orders;

    private Integer dependency;
    private String fieldname;
    private int isRequired;

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public Integer getDependency() {
        return dependency;
    }

    public void setDependency(Integer dependency) {
        this.dependency = dependency;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public JiLianView(Context context) {
        this(context, null);
    }
    //级联的URL
    private String jiLianUrl;

    public String getjiLianUrl() {
        return jiLianUrl;
    }

    public void setjiLianUrl(String moreSelectUrl) {
        this.jiLianUrl = moreSelectUrl;
    }

    public JiLianView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JiLianView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.ji_lian_show,this);
        tv = (TextView) findViewById(R.id.tv_paigong);
        tv_show = (TextView) findViewById(R.id.tv_show_result);
        tv_show_id = (TextView) findViewById(R.id.tv_show_id);
        jilian_select_id = (TextView) findViewById(R.id.jilian_select_id);
    }

    public void setTv_show_id(String string){
        tv_show_id.setText(string);
    }
    public String getTv_show_id(){
        return tv_show_id.getText().toString();
    }
    public void setTvText(String str){
        tv.setText(str);
    }
    public String getTvText(){
        return tv.getText().toString();
    }
    public void setTv_show(String string){
        tv_show.setText(string);
    }

    public TextView getTv_show() {
        return tv_show;
    }

    public TextView getJilian_select_id() {
        return jilian_select_id;
    }

    public void setJilian_select_id(String jilian_select_id) {
        this.jilian_select_id.setText(jilian_select_id);
    }
    public void setTv_show_hint(String string){
        tv_show.setHint(string);
    }
}
