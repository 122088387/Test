package com.chaungying.news.bean;


import com.chaungying.common.utils.date.DateStyle;
import com.chaungying.common.utils.date.DateUtil;

import java.util.Date;

/**
 * 新闻列表子项实体
 *
 * @author 种耀淮 在2016年07月27日10:52创建
 */
public class NewsItem {

    private boolean isDisPlayDate;// 是否显示时间条

    // Id号
    private long id;
    // 排序方式
    private int sort;
    // 来源
    private String source;
    // 状态
    private int state;
    // 评论
    private String remarks;
    // 图片地址
    private String imageAddr;
    // 创建时间
    private String creatTime;// yyyy-MM-dd hh-mm-ss

    private int activityType;
    // 标题
    private String activityName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImageAddr() {
        return imageAddr;
    }

    public void setImageAddr(String imageAddr) {
        this.imageAddr = imageAddr;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public boolean isDisPlayDate() {
        return isDisPlayDate;
    }

    // 根据上一个日期判断是否显示日期条
    public void setDisPlayDate(String onADate) {
        if (creatTime.equals(onADate)) {
            isDisPlayDate = false;
        } else {
            isDisPlayDate = true;
        }
    }

    public void setDate(String date) {
        creatTime = DateUtil.getInterValDaysToString(
                DateUtil.DateToString(new Date(System.currentTimeMillis()), DateStyle.YYYY_MM_DD),
                DateUtil.StringToString(date, DateStyle.YYYY_MM_DD));
    }
}
