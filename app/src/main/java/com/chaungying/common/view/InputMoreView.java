package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;


public class InputMoreView extends FrameLayout{

    TextView tv;
    EditText et;
    int length;
    private int isRequired;

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public InputMoreView(Context context) {
        this(context, null);
    }

    public InputMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.et_more_layout, this);
        tv = (TextView) findViewById(R.id.tv_title);
        et = (EditText) findViewById(R.id.et_input);
    }
//    public void setTvText(String str){
//        tv.setText(str);
//    }
    public void setEtPlaceholder(String str){
        et.setHint(str);
    }
    public String  getInput(){
        return et.getText().toString();
    }
}
