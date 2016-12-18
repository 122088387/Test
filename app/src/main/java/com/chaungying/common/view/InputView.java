package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chaungying.wuye3.R;


public class InputView extends FrameLayout{

    TextView tv;
    EditText et;
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

    public InputView(Context context) {
        this(context, null);
    }

    public InputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.et_layout, this);
        tv = (TextView) findViewById(R.id.tv_title);
        et = (EditText) findViewById(R.id.et_input);
    }
    public void setTvText(String str){
        tv.setText(str);
    }
    public String getTvText(){
        return tv.getText().toString();
    }
    public void setEtPlaceholder(String str){
        et.setHint(str);
    }
    public String  getInput(){
        return et.getText().toString();
    }
}
