package com.chaungying.ji_xiao.bean;

import java.io.Serializable;

/**
 * @author 王晓赛 or 2016/9/20
 */
public class PerRepairDispatchBean implements Serializable{

    /**
     * respCode : 1001
     * moreOneDaySum : 1
     * finishSum : 3
     * respMsg : 成功！
     * unfinishedSum : 74
     * oneHourSum : 1
     * oneDaySum : 2
     * thirtyMinutesSum : 1
     */

    private int respCode;
    private int moreOneDaySum;
    private int finishSum;
    private String respMsg;
    private int unfinishedSum;
    private int oneHourSum;
    private int oneDaySum;
    private int thirtyMinutesSum;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public int getMoreOneDaySum() {
        return moreOneDaySum;
    }

    public void setMoreOneDaySum(int moreOneDaySum) {
        this.moreOneDaySum = moreOneDaySum;
    }

    public int getFinishSum() {
        return finishSum;
    }

    public void setFinishSum(int finishSum) {
        this.finishSum = finishSum;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getUnfinishedSum() {
        return unfinishedSum;
    }

    public void setUnfinishedSum(int unfinishedSum) {
        this.unfinishedSum = unfinishedSum;
    }

    public int getOneHourSum() {
        return oneHourSum;
    }

    public void setOneHourSum(int oneHourSum) {
        this.oneHourSum = oneHourSum;
    }

    public int getOneDaySum() {
        return oneDaySum;
    }

    public void setOneDaySum(int oneDaySum) {
        this.oneDaySum = oneDaySum;
    }

    public int getThirtyMinutesSum() {
        return thirtyMinutesSum;
    }

    public void setThirtyMinutesSum(int thirtyMinutesSum) {
        this.thirtyMinutesSum = thirtyMinutesSum;
    }
}
