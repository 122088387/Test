package com.chaungying.modues.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自适应ListView
 *
 * @author 种耀淮
 */
public class FillListView extends ListView {

    public FillListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
