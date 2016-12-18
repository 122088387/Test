package com.chaungying.metting.bean;

import java.io.Serializable;

/**
 * @author 王晓赛 or 2016/6/29
 */
public class MettingRoomBean implements Serializable{

    /**
     * id : 1
     * isEnable : 0
     * createTime :
     * desc : 有音响
     * operatorName :
     * operatorId : 0
     * accommodate : 20
     * name : 一号会议室
     * state : 0
     * beginTime : 8:30
     * queryDate :
     * endTime : 18:00
     */

    private int id;
    private int isEnable;
    private String createTime;
    private String description;
    private String operatorName;
    private int operatorId;
    private int accommodate;
    private String name;
    private int state;
    private String beginTime;
    private String queryDate;
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getAccommodate() {
        return accommodate;
    }

    public void setAccommodate(int accommodate) {
        this.accommodate = accommodate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
