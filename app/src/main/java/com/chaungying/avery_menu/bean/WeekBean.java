package com.chaungying.avery_menu.bean;

/**
 * @author 王晓赛 or 2016/8/30
 *
 * 星期的实体
 */
public class WeekBean {

    private String value;//几号 比如：22
    private int color;//字体的颜色
    private boolean isSelect;//是否选中
    private boolean isNow;//是否是今天
    private int index;//在它所在周的第几天
    private String allValue;
    private String yearMonthDay;

    public String getYearMonthDay() {
        return yearMonthDay;
    }

    public void setYearMonthDay(String yearMonthDay) {
        this.yearMonthDay = yearMonthDay;
    }

    public String getAllValue() {
        return allValue;
    }

    public void setAllValue(String allValue) {
        this.allValue = allValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isNow() {
        return isNow;
    }

    public void setNow(boolean now) {
        isNow = now;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
