package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/8/26
 */
public class GroupLineView extends FrameLayout {
    public GroupLineView(Context context) {
        this(context, null);
    }

    public GroupLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.group_line_view, this);
    }
}
