package com.chaungying.ji_xiao.bean;

import java.util.List;

/**
 * @author 王晓赛 or 2016/9/20
 *
 * 绩效中投诉咨询bean
 *
 */
public class PerComplaintsConsultBean {

    /**
     * respCode : 1001
     * listComplaint : [{"total":2,"finish":2,"orders":1,"day":"2016-09-14"},{"total":0,"finish":0,"orders":2,"day":"2016-09-15"},{"total":0,"finish":0,"orders":3,"day":"2016-09-16"},{"total":0,"finish":0,"orders":4,"day":"2016-09-17"},{"total":0,"finish":0,"orders":5,"day":"2016-09-18"},{"total":0,"finish":0,"orders":6,"day":"2016-09-19"},{"total":0,"finish":0,"orders":7,"day":"2016-09-20"}]
     * respMsg : 成功！
     * listAnswer : [{"total":29,"finish":27,"orders":1,"day":"2016-09-14"},{"total":0,"finish":0,"orders":2,"day":"2016-09-15"},{"total":0,"finish":0,"orders":3,"day":"2016-09-16"},{"total":0,"finish":0,"orders":4,"day":"2016-09-17"},{"total":0,"finish":0,"orders":5,"day":"2016-09-18"},{"total":0,"finish":0,"orders":6,"day":"2016-09-19"},{"total":0,"finish":0,"orders":7,"day":"2016-09-20"}]
     */

    private int respCode;
    private String respMsg;
    /**
     * total : 2
     * finish : 2
     * orders : 1
     * day : 2016-09-14
     */

    private List<ListComplaintBean> listComplaint;
    /**
     * total : 29
     * finish : 27
     * orders : 1
     * day : 2016-09-14
     */

    private List<ListAnswerBean> listAnswer;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public List<ListComplaintBean> getListComplaint() {
        return listComplaint;
    }

    public void setListComplaint(List<ListComplaintBean> listComplaint) {
        this.listComplaint = listComplaint;
    }

    public List<ListAnswerBean> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(List<ListAnswerBean> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public static class ListComplaintBean {
        private int total;
        private int finish;
        private int orders;
        private String day;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }

    public static class ListAnswerBean {
        private int total;
        private int finish;
        private int orders;
        private String day;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
