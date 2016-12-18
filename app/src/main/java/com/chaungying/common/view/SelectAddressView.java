package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;

/**
 * 选择地址控件 ，点击后进入如同商城中的选择地址
 */
public class SelectAddressView extends FrameLayout {

    TextView title;
    TextView show;

    String url;

    private String fieldname;
    private int isRequired;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

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

    public SelectAddressView(Context context) {
        this(context, null);
    }

    public SelectAddressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectAddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.select_address_view, this);
        title = (TextView) findViewById(R.id.tv_address_title);
        show = (TextView) findViewById(R.id.tv_address_show);
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setPlaceholder(String str) {
        show.setHint(str);
    }

    public String getShow() {
        return show.getText().toString();
    }
    public void setShow(String showStr){
        show.setText(showStr);
    }
}
