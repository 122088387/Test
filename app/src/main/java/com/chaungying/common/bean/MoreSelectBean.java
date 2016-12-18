package com.chaungying.common.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MoreSelectBean implements Serializable{
    private String info;
    private boolean isChecked;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
