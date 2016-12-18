package com.chaungying.ji_xiao.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/9/19
 */
public class PerSignBean {

    /**
     * totalPeople : 5
     * absenteeismPeople : 5
     * respCode : 1001
     * signInPeople : 0
     * data : [{"absenteeismPeople2":5,"signInPeople2":0,"day":"2016-09-19"},{"absenteeismPeople2":5,"signInPeople2":1,"day":"2016-09-18"},{"absenteeismPeople2":5,"signInPeople2":0,"day":"2016-09-17"},{"absenteeismPeople2":5,"signInPeople2":0,"day":"2016-09-16"},{"absenteeismPeople2":5,"signInPeople2":0,"day":"2016-09-15"},{"absenteeismPeople2":5,"signInPeople2":0,"day":"2016-09-14"},{"absenteeismPeople2":4,"signInPeople2":1,"day":"2016-09-13"}]
     * respMsg : 成功！
     * tardinessPeople : 0
     */

    private int totalPeople;//总人员
    private int absenteeismPeople;//缺勤人员；
    private int respCode;
    private int signInPeople;//签到人员
    private String respMsg;
    private int tardinessPeople;//迟到早退人员
    /**
     * absenteeismPeople2 : 5
     * signInPeople2 : 0
     * day : 2016-09-19
     */

    private List<DataBean> data;

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public int getAbsenteeismPeople() {
        return absenteeismPeople;
    }

    public void setAbsenteeismPeople(int absenteeismPeople) {
        this.absenteeismPeople = absenteeismPeople;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public int getSignInPeople() {
        return signInPeople;
    }

    public void setSignInPeople(int signInPeople) {
        this.signInPeople = signInPeople;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getTardinessPeople() {
        return tardinessPeople;
    }

    public void setTardinessPeople(int tardinessPeople) {
        this.tardinessPeople = tardinessPeople;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int absenteeismPeople2; //意外情况人员
        private int signInPeople2;//签到人员
        private int orders;
        private String day;

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public int getAbsenteeismPeople2() {
            return absenteeismPeople2;
        }

        public void setAbsenteeismPeople2(int absenteeismPeople2) {
            this.absenteeismPeople2 = absenteeismPeople2;
        }

        public int getSignInPeople2() {
            return signInPeople2;
        }

        public void setSignInPeople2(int signInPeople2) {
            this.signInPeople2 = signInPeople2;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
