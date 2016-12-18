package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.chaungying.wuye3.R;

/**
 * Created by Administrator on 2016/6/12.
 */
public class CheckItemView extends FrameLayout{

    private CheckBox check_box;

    public CheckItemView(Context context) {
        this(context, null);
    }

    public CheckItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.check_item_layout, this);
        check_box = (CheckBox) findViewById(R.id.checkbox);
    }
    public void setCheckTag(String tag){
        check_box.setText(tag);
    }
}
