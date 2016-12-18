package com.chaungying.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 创建者：  王晓赛
 * 时  间： 2016/11/23
 *
 * 隐藏控件
 */

public class HiddenView extends FrameLayout{

    String fieldName;
    String hiddenKey;

    public String getHiddenKey() {
        return hiddenKey;
    }

    public void setHiddenKey(String hiddenKey) {
        this.hiddenKey = hiddenKey;
    }

    int ids;

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public HiddenView(Context context) {
        super(context);
    }

    public HiddenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HiddenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
