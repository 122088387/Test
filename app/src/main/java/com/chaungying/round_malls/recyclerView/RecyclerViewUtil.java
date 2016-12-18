package com.chaungying.round_malls.recyclerView;

import android.support.v7.widget.RecyclerView;

/**
 * @author 种耀淮 在2016年09月06日15:14创建
 */
public class RecyclerViewUtil {

    /**
     * 判断RecyclerView是否滚动到底部
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
