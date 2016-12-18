package com.chaungying.use_car.bean;

import java.io.Serializable;

/**
 * @author 王晓赛 or 2016/8/2
 */
public class AllCarBean implements Serializable{


    /**
     * id : 1
     * isEnable : 0
     * createTime :
     * carMoney : 100
     * operatorName :
     * operatorId :
     * accommodate : 21
     * description : 有导航
     * name : 别克君越
     * state : 0
     * beginTime : 8:30
     * endTime : 18:00
     * carLicense : 冀A00000
     */

    private int id;
    private int isEnable;
    private String createTime;
    private String carMoney;
    private String operatorName;
    private String operatorId;
    private int accommodate;
    private String description;
    private String name;
    private int state;
    private String beginTime;
    private String endTime;
    private String carLicense;

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

    public String getCarMoney() {
        return carMoney;
    }

    public void setCarMoney(String carMoney) {
        this.carMoney = carMoney;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public int getAccommodate() {
        return accommodate;
    }

    public void setAccommodate(int accommodate) {
        this.accommodate = accommodate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }
}
