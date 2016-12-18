package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chaungying.wuye3.R;

/**
 * @author 王晓赛 or 2016/7/28
 */
public class PullSelectView extends FrameLayout {


    public PullSelectView(Context context) {
        this(context, null);
    }

    public PullSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.pull_select_view, this);
    }
}
