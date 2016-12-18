package com.chaungying.common.view;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chaungying.wuye3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/12.
 */
public class SingleSelectView extends FrameLayout {
    private TextView tv_sex;
    private TextView single_tv;
    private RadioGroup radioGroup;
    Context mContext;
    ArrayList<String> str;
    //是否为必填项
    private int isRequired;

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    private List<Map<String, String>> data_list = new ArrayList<Map<String, String>>();

    public void setData(ArrayList<String> str) {
        this.str = str;
    }

    public SingleSelectView(Context context) {
        this(context, null);

    }

    public SingleSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

    }

    public void init() {
        //设置list数据
//        for (int i = 0; i < str.size(); i++) {
//            Map<String,String> map = new HashMap<String,String>();
//            map.put("text",str.get(i));
//            data_list.add(map);
//        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.single_select_layout, this);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        single_tv = (TextView) view.findViewById(R.id.single_tv);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        for (int i = 0; i < str.size(); i++) {
            AppCompatRadioButton radio = new AppCompatRadioButton(mContext);
            radio.setBackgroundColor(getResources().getColor(R.color.White));
            radio.setText(str.get(i));
            if (i == 0) {
                radio.setChecked(true);
            }
            radio.setId(i);
            radioGroup.addView(radio);
        }
    }

    public void setSexTv(String tag) {
        tv_sex.setText(tag);
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public String getSingle_tv() {
        return single_tv.getText().toString();
    }

    public void setSingle_tv(String single_tv) {
        this.single_tv.setText(single_tv);
    }

}
