package com.chaungying.site_repairs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableListView1 extends ListView implements Pullable {

    public PullableListView1(Context context) {
        super(context);
    }

    public PullableListView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableListView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
//        if (getCount() == 0) {
//            // 没有item的时候也可以下拉刷新
//            return true;
//        } else if (getFirstVisiblePosition() == 0
//                && getChildAt(0).getTop() >= 0) {
//            // 滑到ListView的顶部了
//            return true;
//        } else
//            return false;
        //不支持下拉刷新操作，上边的代码放开去掉本行，就会不限制下拉刷新
        return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() -

                    getFirstVisiblePosition()) != null
                    && getChildAt(


                    getLastVisiblePosition()


                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }
}
